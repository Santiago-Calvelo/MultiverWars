package com.milne.mw.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.MusicManager;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.Global;
import com.milne.mw.player.Player;
import com.milne.mw.screens.MainMenuScreen;

import javax.swing.plaf.PanelUI;

import static com.milne.mw.globals.Global.loadTexture;

public class GameOverMenu {
    private Image gameOverBackground;
    private Stage stage;
    private PauseMenu pauseMenu;
    private EntityManager entityManager;
    private Rectangle retryButton, mainMenuButton;
    private Player player;
    private Group gameOverLayer;


    public GameOverMenu(Stage stage, PauseMenu pauseMenu, EntityManager entityManager) {
        this.stage = stage;
        this.pauseMenu = pauseMenu;
        this.entityManager = entityManager;
    }

    public void togglePause() {
        pauseMenu.togglePause();
    }

    public void createMenu(Player player) {
        pauseMenu.setEnable(false);
        pauseMenu.togglePause();
        entityManager.getPlacementHitboxes().clear();
        gameOverLayer = new Group();
        retryButton = new Rectangle(290f,205f,200,50);
        mainMenuButton = new Rectangle(270f, 100f, 500, 50);

        gameOverBackground = new Image(loadTexture("multiverse-wars/game-over.jpg"));
        gameOverBackground.setSize(stage.getViewport().getWorldWidth(), stage.getViewport().getWorldHeight());
        gameOverLayer.addActor(gameOverBackground);

        stage.addActor(gameOverLayer);

        this.player = player;
    }

    public void handleInput(float touchX, float touchY) {
        if (retryButton.contains(touchX, touchY)) {
            retry();
        } else if (mainMenuButton.contains(touchX, touchY)) {
            backToMainMenu();
        }
    }

    private void backToMainMenu() {
        Gdx.app.postRunnable(() -> GameData.game.setScreen(new MainMenuScreen()));
        MusicManager.playMusic("bye bye.mp3");
    }

    public void retry() {
        pauseMenu.setEnable(true);
        player.reset();
        entityManager.reset();
        pauseMenu.togglePause();
        dispose();
    }

    public void dispose() {
        gameOverLayer.clear(); // Limpia todos los actores del grupo
        stage.getActors().removeValue(gameOverLayer, true); // Elimina el grupo del Stage
        retryButton = null;
    }
}
