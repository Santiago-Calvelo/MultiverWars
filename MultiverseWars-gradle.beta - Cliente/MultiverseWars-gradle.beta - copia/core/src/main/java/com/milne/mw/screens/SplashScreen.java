package com.milne.mw.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.milne.mw.Global.loadTexture;

public class SplashScreen implements Screen {

    private Game game;
    private Stage stage;
    private Texture splashImage;

    public SplashScreen(Game game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(800, 600));
    }

    @Override
    public void show() {
        splashImage = loadTexture("multiverse-wars/splash.png");
        Image splash = new Image(splashImage);
        splash.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.addActor(splash);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new MainMenuScreen(game));
            }
        }, 3);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
        splashImage.dispose();
    }
}
