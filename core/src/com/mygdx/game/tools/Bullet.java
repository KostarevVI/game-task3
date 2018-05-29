package com.mygdx.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.view.PlayScreen;

import static com.mygdx.game.MyGame.PPM;
import static com.mygdx.game.view.PlayScreen.mapHeight;
import static com.mygdx.game.view.PlayScreen.mapWidth;

public class Bullet {

    public static final int BULLETSPEED = 500;
    private static Texture bulletTexture;

    private float x,y;

    public boolean remove = false;

    public Bullet(float x, float y){
        this.x = x;
        this.y = y;

        Gdx.app.log(this.x+ " " + this.y + "|" + x + " " + y, "");

        if (bulletTexture == null)
            bulletTexture = new Texture("bullet.png");
    }

    public void update(float dt, Player player){
        x += BULLETSPEED * dt;
        Gdx.app.log("Bullet " + mapWidth + " " + x/PPM + " " + y/PPM + " " + player.b2body.getPosition().x + " " + bulletTexture, "");
        if(x/PPM > mapWidth|| x/PPM < 0)
            remove = true;
    }

    public void render (SpriteBatch batch){
        batch.draw(bulletTexture, x, y, bulletTexture.getWidth()/PPM, bulletTexture.getHeight()/PPM);
    }

}
