package com.milne.mw.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.network.ServerThread;

import static com.milne.mw.globals.Global.loadTexture;

public class TwoPlayerModeScreen implements Screen {

    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;

    public TwoPlayerModeScreen() {
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = loadTexture("difficulty/DIFICULTAD DOS JUGADORES.jpg");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.addActor(backgroundImage);

    }

    @Override
    public void show() {
        Global.multiplayer = true;
        GameData.finishedGame = false;

        NetworkData.serverThread = new ServerThread();
        NetworkData.serverThread.start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            GameData.game.setScreen(new MainMenuScreen());
        }
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
        backgroundTexture.dispose();
    }
}
