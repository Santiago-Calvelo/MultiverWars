package com.milne.mw.difficulty;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.maps.MapScreen;
import com.milne.mw.screens.MainMenuScreen;


public class DifficultySelectionScreen implements Screen {

    private Stage stage;
    private Texture backgroundTexture;
    private Image backgroundImage;
    private Skin skin;
    private String map;

    public DifficultySelectionScreen(String map) {
        this.map = map;
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);
        this.skin = new Skin(Gdx.files.internal("uiskin.json"));

        backgroundTexture = Global.loadTexture("difficulty/DIFICULTAD UN JUGADOR.jpg");
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        stage.addActor(backgroundImage);

        createDifficultyButtons();
    }

    private void createDifficultyButtons() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default-font");
        textButtonStyle.font.getData().setScale(2.5f);
        textButtonStyle.fontColor = Color.YELLOW;
        textButtonStyle.up = null;
        textButtonStyle.down = null;

        TextButton easyButton = new TextButton("     ", textButtonStyle);
        easyButton.setPosition(stage.getViewport().getWorldWidth() / 6f - easyButton.getWidth() / 2 + 20, stage.getViewport().getWorldHeight() / 2f - 20);
        easyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGameWithDifficulty(Difficulty.EASY);
            }
        });

        TextButton mediumButton = new TextButton("     ", textButtonStyle);
        mediumButton.setPosition(stage.getViewport().getWorldWidth() / 2f - mediumButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 20);
        mediumButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGameWithDifficulty(Difficulty.MEDIUM);
            }
        });

        TextButton hardButton = new TextButton("       ", textButtonStyle);
        hardButton.setPosition(stage.getViewport().getWorldWidth() * 5 / 6f - hardButton.getWidth() / 2 - 30, stage.getViewport().getWorldHeight() / 2f - 20);
        hardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGameWithDifficulty(Difficulty.HARD);
            }
        });

        stage.addActor(easyButton);
        stage.addActor(mediumButton);
        stage.addActor(hardButton);
    }

    private void startGameWithDifficulty(Difficulty difficultyLevel) {
        if (Global.multiplayer) {
            NetworkData.clientThread.sendMessage("difficultyselected!" + difficultyLevel.name());
        } else {
            MapScreen mapScreen = new MapScreen(map, difficultyLevel);
            GameData.game.setScreen(mapScreen);
        }
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
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }

    @Override
    public void show() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}
}
