package com.milne.mw.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.MusicManager;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.screens.MainMenuScreen;

import static com.milne.mw.globals.Global.loadTexture;

public class GameOverMenu {
    private Image gameOverBackground;
    private Stage stage;
    private Rectangle mainMenuButton;

    public GameOverMenu(Stage stage) {
        this.stage = stage;
    }

    public void createMenu() {
        mainMenuButton = new Rectangle(270f, 100f, 500, 50);

        gameOverBackground = new Image(loadTexture("multiverse-wars/game-over.jpg"));
        gameOverBackground.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());

        stage.addActor(gameOverBackground);
    }

    public void handleInput(float touchX, float touchY) {
        if (mainMenuButton.contains(touchX, touchY)) {
            backToMainMenu();
        }
    }

    private void backToMainMenu() {
        Gdx.app.postRunnable(() -> {
            NetworkData.clientThread.sendMessage("disconnect!" + GameData.clientNumber);
            NetworkData.clientThread.end();
            NetworkData.clientThread = null;
            GameData.game.setScreen(new MainMenuScreen());
        });
        MusicManager.playMusic("bye bye.mp3");
    }


    public void dispose() {
        gameOverBackground.clear(); // Limpia todos los actores del grupo
        stage.getActors().removeValue(gameOverBackground, true); // Elimina el grupo del Stage
        mainMenuButton = null;
    }
}
