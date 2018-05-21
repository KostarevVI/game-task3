package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.sprites.Player;

import static com.mygdx.game.view.playScreen.deltaGen;

public class playerController extends  worldContactListener{

    private Player player;

    public playerController(Player player) {
        this.player = player;
    }

    float speedVelocity = 0.8f, jumpVelocity = 4.5f, maxSpeed = 2f;

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && worldContactListener.isOnGround>0)
            player.b2body.applyLinearImpulse(new Vector2(0, jumpVelocity), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= maxSpeed)
            player.b2body.applyLinearImpulse(new Vector2(speedVelocity, 0), player.b2body.getWorldCenter(), true);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -maxSpeed)
            player.b2body.applyLinearImpulse(new Vector2(-speedVelocity, 0), player.b2body.getWorldCenter(), true);
        else downSpeed();
    }

    private void downSpeed() {
        if (player.b2body.getLinearVelocity().x >= speedVelocity * deltaGen)
            player.b2body.applyLinearImpulse(new Vector2(-maxSpeed*6 * deltaGen, 0), player.b2body.getWorldCenter(), true);
        else if (player.b2body.getLinearVelocity().x <= -speedVelocity*6 * deltaGen)
            player.b2body.applyLinearImpulse(new Vector2(maxSpeed*6 * deltaGen, 0), player.b2body.getWorldCenter(), true);
    }
}
