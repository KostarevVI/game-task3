package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.view.PlayScreen;

import static com.mygdx.game.view.PlayScreen.deltaGen;
import static java.lang.Math.abs;

public class PlayerController {

    private Player player;
    public boolean isShooting;
    private WorldContactListener contactListener;

    public PlayerController(Player player, WorldContactListener contactListener) {
        this.player = player;
        this.contactListener = contactListener;
    }

    float speedVelocity = 0.8f, jumpVelocity = 4.5f, maxSpeed = 2f;

    public void handleInput(float dt) {
        isShooting = Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && contactListener.isPlayerOnGround() > 0)
            player.b2body.applyLinearImpulse(new Vector2(0, jumpVelocity), player.b2body.getWorldCenter(), true);
        if (abs(player.b2body.getLinearVelocity().x) <= maxSpeed) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                player.b2body.applyLinearImpulse(new Vector2(speedVelocity, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
                player.b2body.applyLinearImpulse(new Vector2(-speedVelocity, 0), player.b2body.getWorldCenter(), true);
        }
            downSpeed();
    }

    private void downSpeed() {
        if (player.b2body.getLinearVelocity().x >= speedVelocity * deltaGen)
            player.b2body.applyLinearImpulse(new Vector2(-player.b2body.getLinearVelocity().x * 6 * deltaGen, 0), player.b2body.getWorldCenter(), true);
        else if (player.b2body.getLinearVelocity().x <= -speedVelocity * deltaGen)
            player.b2body.applyLinearImpulse(new Vector2(-player.b2body.getLinearVelocity().x * 6 * deltaGen, 0), player.b2body.getWorldCenter(), true);
    }
}
