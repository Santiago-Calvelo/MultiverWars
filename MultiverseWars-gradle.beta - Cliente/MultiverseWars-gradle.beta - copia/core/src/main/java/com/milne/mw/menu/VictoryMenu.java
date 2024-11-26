package com.milne.mw.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.milne.mw.MusicManager;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.screens.MainMenuScreen;

import static com.milne.mw.Global.loadTexture;

public class VictoryMenu {
    private TextButton mainMenuButton;
    private Game game;
    private Image victoryBackground;
    private Stage stage;
    private PauseMenu pauseMenu;

    public VictoryMenu(Stage stage, Game game, PauseMenu pauseMenu) {
        this.stage = stage;
        this.game = game;
        this.pauseMenu = pauseMenu;
    }

    public void createMenu() {
        pauseMenu.setEnable(false);
        pauseMenu.togglePause();
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.WHITE;

        victoryBackground = new Image(loadTexture("multiverse-wars/win.jpg"));
        victoryBackground.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());


        mainMenuButton = new TextButton("Volver al Menu", textButtonStyle);
        mainMenuButton.setPosition(stage.getViewport().getWorldWidth() / 2f - mainMenuButton.getWidth() / 2, stage.getViewport().getWorldHeight() / 2f + 90);
        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.postRunnable(() -> game.setScreen(new MainMenuScreen(game)));
                MusicManager.playMusic("bye bye.mp3");
            }
        });
        addVictoryMenuToStage();
    }

    private void addVictoryMenuToStage() {
        stage.addActor(victoryBackground);
        stage.addActor(mainMenuButton);
    }

    private void removeVictoryMenuFromStage() {
        victoryBackground.remove();
        mainMenuButton.remove();
    }

    public void dispose() {
        removeVictoryMenuFromStage();
        victoryBackground.remove();
        mainMenuButton.remove();
    }
}
