package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.view.PlayScreen;

import static com.mygdx.game.MyGame.PPM;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, STANDING, RUNNING};
    public State currState;
    public State prevState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private TextureRegion playerFalls;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerJump;
    private float stateTimer;
    private boolean runningRight;
    private TextureRegion region;

    public boolean isRunningRight() {
        return runningRight;
    }

    public Player(World world, PlayScreen screen) {
        this.world = world;

        currState = State.STANDING;
        prevState = State.STANDING;
        stateTimer = 0f;
        runningRight = true;

        playerDef();

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 23; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), i * 512, 0, 512, 512));
        playerRun = new Animation<TextureRegion>(0.04f, frames);
        frames.clear();

        for (int i = 4; i < 11; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("player"), i * 512, 512, 512, 512));
        playerJump = new Animation<TextureRegion>(0.04f, frames);
        frames.clear();

        playerStand = new TextureRegion(screen.getAtlas().findRegion("player"), 0, 0, 512, 512);
        setBounds(0, 0, 512 / (PPM * 10.5f), 512 / (PPM * 10.5f));
        setRegion(playerStand);

        playerFalls = new TextureRegion(screen.getAtlas().findRegion("player"), 5120, 512, 512, 512);
        setBounds(0, 0 , 512 / (PPM * 10.5f), 512 / (PPM * 10.5f));
        setRegion(playerFalls);
    }

    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 5);
        setRegion(getFrame(dt));
        //Gdx.app.log("" + prevState, "");
    }

    public TextureRegion getFrame(float dt) {
        currState = getState();

        switch (currState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = playerFalls;
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currState == prevState ? stateTimer + dt : 0;

        prevState = currState;

        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && prevState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
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
        bodyShape.setAsBox(7 / PPM, 19 / PPM, new Vector2(0, 19 / PPM), 0);
        fDef.shape = bodyShape;
        b2body.createFixture(fDef).setUserData("body");

        fDef.filter.categoryBits = MyGame.PLAYER_BIT;
        fDef.filter.maskBits = MyGame.DEFAULT_BIT | MyGame.GROUND_BIT;

        EdgeShape footSensor = new EdgeShape();
        footSensor.set(new Vector2(-7 / PPM, -7 / PPM), new Vector2(7 / PPM, -7 / PPM));
        fDef.shape = footSensor;
        fDef.isSensor = true;

        b2body.createFixture(fDef).setUserData("footSensor");
    }
}
