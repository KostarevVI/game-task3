package com.mygdx.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.myGame;


public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Integer perkTimer;
    private float perkTimerCount;
    private Integer enemiesLeft;

    Label countdownLabel;
    Label enemiesCounterLabel;
    Label perkTimerLabel;
    Label levelLabel;
    Label lifeLabel;
    Label enemiesLabel;

    public Hud(SpriteBatch sb) {
        perkTimer = 30;
        perkTimerCount = 0;
        enemiesLeft = 0;

        viewport = new FitViewport(myGame.V_WIDTH, myGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%02d", perkTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        perkTimerLabel = new Label("NEW RANDOM PERK IN:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        enemiesCounterLabel = new Label(String.format("%03d", enemiesLeft), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        enemiesLabel = new Label("ENEMIES LEFT:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("LEVEL:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeLabel = new Label("LIFE:", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(enemiesLabel).expandX().padTop(10);
        table.add(perkTimerLabel).expandX().padTop(10);
        table.add(lifeLabel).expandX().padTop(10);
        table.row();
        table.add(enemiesCounterLabel).expandX();
        table.add(countdownLabel).expandX();
        //table.add(levelLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
