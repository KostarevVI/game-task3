package com.mygdx.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MyGame;

public class WorldContactListener implements ContactListener {

    public int isPlayerOnGround() {
        return playerOnGround;
    }

    private int playerOnGround = 0;

    private boolean isItPlayer(Fixture fixA, Fixture fixB) {
        return (fixA.getUserData() == "footSensor" || fixB.getUserData() == "footSensor")
                && fixA.getFilterData().categoryBits == MyGame.PLAYER_BIT;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (isItPlayer(fixA, fixB))
            playerOnGround++;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (isItPlayer(fixA, fixB))
            playerOnGround--;
        //Gdx.app.log("Begin cont " + String.valueOf(isOnGround), "");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
