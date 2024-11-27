package com.milne.mw.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.milne.mw.MusicManager;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.screens.MainMenuScreen;

import static com.milne.mw.globals.Global.loadTexture;

public class PauseMenu {
    private TextButton resumeButton;
    private TextButton mainMenuButton;
    private boolean isPaused = false;
    private Image pauseBackground;
    private Circle pauseButtonHitbox;
    private Stage stage;
    private boolean enable = true;

    public PauseMenu(Stage stage) {
        this.stage = stage;

        pauseButtonHitbox = new Circle(stage.getViewport().getWorldWidth() - 257, stage.getViewport().getWorldHeight() - 40, 30);
        createPauseMenuButtons();
    }

    private void createPauseMenuButtons() {
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.WHITE;

        pauseBackground = new Image(loadTexture("pause/escena-pausa.png"));
        pauseBackground.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        resumeButton = new TextButton("Reanudar", textButtonStyle);
        resumeButton.setPosition(stage.getViewport().getWorldWidth() / 2f - resumeButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f + 90);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause();
            }
        });

        mainMenuButton = new TextButton("Volver al Menu", textButtonStyle);
        mainMenuButton.setPosition(stage.getViewport().getWorldWidth() / 2f - mainMenuButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f - 55);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> {
                    NetworkData.clientThread.sendMessage("disconnect!" + GameData.clientNumber);
                    NetworkData.clientThread.end();
                    NetworkData.clientThread = null;
                    GameData.game.setScreen(new MainMenuScreen());
                });
                MusicManager.playMusic("bye bye.mp3");
            }
        });
    }

    public void handleInput(float touchX, float touchY) {
        if (enable && pauseButtonHitbox.contains(touchX, touchY)) {
            togglePause();
        }
    }

    public void checkForEscapeKey() {
        if (enable && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }
    }

    public void togglePause() {
        isPaused = !isPaused;

        if (isPaused) {
            addPauseMenuToStage();
        } else {
            removePauseMenuFromStage();
        }
    }

    private void addPauseMenuToStage() {
        stage.addActor(pauseBackground);
        stage.addActor(resumeButton);
        stage.addActor(mainMenuButton);
        pauseBackground.setZIndex(20);
        resumeButton.setZIndex(20);
        mainMenuButton.setZIndex(20);
    }

    private void removePauseMenuFromStage() {
        pauseBackground.remove();
        resumeButton.remove();
        mainMenuButton.remove();
    }

    public Circle getPauseButtonHitbox() {
        return pauseButtonHitbox;
    }

    public void dispose() {
        removePauseMenuFromStage();
        pauseBackground.remove();
        resumeButton.remove();
        mainMenuButton.remove();
    }
}
