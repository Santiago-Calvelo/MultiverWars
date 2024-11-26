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
import com.milne.mw.maps.MapSelectionScreen;

import static com.milne.mw.Global.loadTexture;

public class PlayOptionsScreen implements Screen {
    private Game game;
    private Stage stage;
    private Skin skin;
    private Texture menuImage;

    public PlayOptionsScreen(Game game) {
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
        TextButton singlePlayerButton = new TextButton("Un Jugador", skin);
        singlePlayerButton.setPosition(stage.getViewport().getWorldWidth() / 2f - singlePlayerButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 100);
        singlePlayerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MapSelectionScreen(game));
            }
        });

        TextButton twoPlayersButton = new TextButton("Dos Jugadores", skin);
        twoPlayersButton.setPosition(stage.getViewport().getWorldWidth() / 2f - twoPlayersButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 150);
        twoPlayersButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TwoPlayerModeScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Volver al Menu", skin);
        exitButton.setPosition(stage.getViewport().getWorldWidth() / 2f - exitButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 200);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(singlePlayerButton);
        stage.addActor(twoPlayersButton);
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
