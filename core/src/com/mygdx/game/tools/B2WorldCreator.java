package com.mygdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;

import static com.mygdx.game.MyGame.PPM;

public class B2WorldCreator {

    protected Fixture fixture;

    public B2WorldCreator(World world, TiledMap map){
        //создание твёрдых тел
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;


        //создание карты тел уровня
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bDef);
            //деление на два, ибо риг начитанется с центра коробки в обе стороны
            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
            fDef.shape = shape;
            fixture = body.createFixture(fDef);
            fixture.setUserData("Ground");
            setCategoryFilter(MyGame.GROUND_BIT);
        }
    }
    public void setCategoryFilter(short filterBits){
        Filter filter = new Filter();
        filter.categoryBits = filterBits;
        fixture.setFilterData(filter);
    }
}
