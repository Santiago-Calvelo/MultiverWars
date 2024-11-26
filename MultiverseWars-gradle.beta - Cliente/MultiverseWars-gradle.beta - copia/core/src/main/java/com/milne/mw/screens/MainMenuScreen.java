package com.milne.mw.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.milne.mw.Global.loadTexture;

public class MainMenuScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private Texture menuImage;

    public MainMenuScreen(Game game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    @Override
    public void show() {
        menuImage = loadTexture("multiverse-wars/menu.png");
        Image background = new Image(menuImage);
        background.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.addActor(background);

        createMenuButtons();
    }

    private void createMenuButtons() {
        TextButton playButton = new TextButton("Jugar", skin);
        playButton.setPosition(stage.getViewport().getWorldWidth() / 2f - playButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 100);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayOptionsScreen(game));
            }
        });

        TextButton introButton = new TextButton("Introduccion", skin);
        introButton.setPosition(stage.getViewport().getWorldWidth() / 2f - introButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 150);
        introButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new IntroductionScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Salir del Juego", skin);
        exitButton.setPosition(stage.getViewport().getWorldWidth() / 2f - exitButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 200);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ExitConfirmationScreen(game));
            }
        });

        stage.addActor(playButton);
        stage.addActor(introButton);
        stage.addActor(exitButton);
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
        skin.dispose();
        menuImage.dispose();
    }
}
