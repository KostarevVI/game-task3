package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.PlayScreen;

public class MyGame extends Game {
    public SpriteBatch batch;    //тайтл-сет

    public static final int V_WIDTH = 640;
    public static final int V_HEIGHT = 360;
    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short GROUND_BIT = 4;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
