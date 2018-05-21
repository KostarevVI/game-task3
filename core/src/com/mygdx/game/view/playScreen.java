package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.myGame;
import com.mygdx.game.sprites.Player;
import com.mygdx.game.tools.b2WorldCreator;
import com.mygdx.game.tools.playerController;
import com.mygdx.game.tools.worldContactListener;

import static com.mygdx.game.myGame.PPM;

public class playScreen implements Screen {

    private myGame game;

    //отрисовка игры и интерфейса
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //Для карты уровней
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d переменные
    private World world;
    private Box2DDebugRenderer b2dr;

    //создание игрока
    private Player player;

    private playerController controller;
    //общая дельта
    public static float deltaGen;

    //размеры карты (выведены вручную)
    public static final float mapWidth = 8.32f;
    public static final float mapHeight = 5.79f;

    public playScreen(myGame game) {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(myGame.V_WIDTH / PPM, myGame.V_HEIGHT / PPM, gameCam);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        player = new Player(world);

        new b2WorldCreator(world, map);

        controller = new playerController(player);

        world.setContactListener(new worldContactListener());
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        //обрабатывает входные данные пользователя
        controller.handleInput(dt);

        world.step(1 / 60f, 6, 2);

        //трекинг камеры чтобы она не выходила за границы уровня
        if (Math.abs(player.b2body.getPosition().x - mapWidth/2) < 1f)
            gameCam.position.x = player.b2body.getPosition().x;
        if (Math.abs(player.b2body.getPosition().y - mapHeight/2) <1.05f)
            gameCam.position.y = player.b2body.getPosition().y;

        //рендеринг только того, что происходит на экране
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float dt) {
        update(dt);

        //Очищает игровой экран чёрным цветом
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //рендер игровой карты
        renderer.render();

        //Объединяет нашу игровую и HUD камеры
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //дебаг рендерер Box2D
        b2dr.render(world, gameCam.combined);

        //запоминание дельта
        deltaGen = dt;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        world.dispose();
        b2dr.dispose();
        renderer.dispose();
        hud.dispose();
    }

   /* public void cameraUpdate(float dt) {
        Vector3 position = gameCam.position;
        position.x =
    }*/
}
