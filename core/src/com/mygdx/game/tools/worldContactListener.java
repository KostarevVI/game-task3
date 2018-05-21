package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.myGame;
import com.mygdx.game.sprites.Player;

public class worldContactListener implements ContactListener {

    public static int isOnGround = 0;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "footSensor" || fixB.getUserData() == "footSensor") {
            //Fixture footSensor = fixA.getUserData() == "footSensor" ? fixA : fixB;
            //Fixture object = footSensor == fixA ? fixB : fixA;

            if (fixA.getFilterData().categoryBits == myGame.PLAYER_BIT) {
                isOnGround++;
                //Gdx.app.log("Begin cont " + String.valueOf(isOnGround), "");
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "footSensor" || fixB.getUserData() == "footSensor") {
            //Fixture footSensor = fixA.getUserData() == "footSensor" ? fixA : fixB;
            //Fixture object = footSensor == fixA ? fixB : fixA;

            if (fixA.getFilterData().categoryBits == myGame.PLAYER_BIT) {
                isOnGround--;
                //Gdx.app.log("Begin cont " + String.valueOf(isOnGround), "");
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
