package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.EllipseShapeBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.myGame;

import static com.mygdx.game.myGame.PPM;

public class Player extends Sprite {
    public World world;
    public Body b2body;

    public Player(World world) {
        this.world = world;
        playerDef();
    }

    public void playerDef() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(32 / PPM, 32 / PPM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape footShape = new CircleShape();
        footShape.setRadius(7 / PPM);
        fDef.shape = footShape;
        b2body.createFixture(fDef).setUserData("foot");

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(7/PPM, 19/PPM, new Vector2(0, 19 /PPM), 0);
        fDef.shape = bodyShape;
        b2body.createFixture(fDef).setUserData("body");

        fDef.filter.categoryBits = myGame.PLAYER_BIT;
        fDef.filter.maskBits = myGame.DEFAULT_BIT | myGame.GROUND_BIT;

        EdgeShape footSensor = new EdgeShape();
        footSensor.set(new Vector2(-7/PPM,-7/PPM), new Vector2(7/PPM,-7/PPM));
        fDef.shape = footSensor;
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData("footSensor");
    }
}
