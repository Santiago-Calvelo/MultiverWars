package com.milne.mw.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.entities.SellTowerListener;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.menu.GameOverMenu;
import com.milne.mw.menu.PauseMenu;
import com.milne.mw.menu.VictoryMenu;
import com.milne.mw.player.Player;
import com.milne.mw.renders.RenderManager;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.MusicManager;

public class MapScreen implements Screen {

    private Stage stage;
    private RenderManager renderManager;
    private EntityManager entityManager;
    private Player player;
    private PauseMenu pauseMenu;
    private VictoryMenu victoryMenu;
    private GameOverMenu gameOverMenu;

    public MapScreen(String map, Difficulty difficultyLevel) {
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);
        renderManager = RenderManager.getInstance(map, stage);
        player = new Player(difficultyLevel.getInitalLives(), difficultyLevel.getInitialEnergy(), difficultyLevel.getEnergyGenerationRate());
        entityManager = new EntityManager(stage, difficultyLevel, player);
        pauseMenu = new PauseMenu(stage, entityManager);
        victoryMenu = new VictoryMenu(stage, pauseMenu);
        gameOverMenu = new GameOverMenu(stage, pauseMenu, entityManager);

        entityManager.startEnemySpawner();
        entityManager.setVictoryMenu(victoryMenu);
        entityManager.setGameOverMenu(gameOverMenu);
        stage.addListener(new SellTowerListener(entityManager));

        renderManager.setPlayer(player);
        renderManager.setMaxRound(difficultyLevel.getMaxRound());
        renderManager.initializeLabels(entityManager.getRound());

        readyForGame();
    }

    public void readyForGame() {
        NetworkData.clientThread.sendMessage("readyforgame!");
    }

    public void spawnEntity(int id, float x, float y, String entityImage, float hitboxWidth, float hitboxHeight) {
        System.out.println("Spawneando entidad en " + x + " " + y);
        Image entity = new Image(Global.loadTexture(entityImage));
        entity.setSize(hitboxWidth, hitboxHeight);
        entity.setPosition(x,y);
        stage.addActor(entity);
    }

    public void addCardsToPanel(String cardImage, float x, float y, int width, int height, String entityType) {
        System.out.println("Agregando carta");
        Image card = new Image(Global.loadTexture(cardImage));

        card.setSize(width,height);
        card.setPosition(x,y);

        CardDragListener cardDragListener = new CardDragListener(entityType,card);
        card.addListener(cardDragListener);
        stage.addActor(card);

    }


    @Override
    public void show() {
        MusicManager.playMusic("tema d battala.mp3");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            Vector2 worldTouch = stage.getViewport().unproject(new Vector2(touchX, touchY));
            pauseMenu.handleInput(worldTouch.x, worldTouch.y);
        }

        if (Gdx.input.justTouched() && !player.isAlive()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            Vector2 worldTouch = stage.getViewport().unproject(new Vector2(touchX, touchY));
            gameOverMenu.handleInput(worldTouch.x, worldTouch.y);
        }
        renderManager.render(pauseMenu.getIsPaused(), entityManager, delta, pauseMenu);
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
        MusicManager.stopMusic();
        dispose();
    }

    @Override
    public void dispose() {
        if (entityManager != null) {
            entityManager.dispose();
        }
        if (stage != null) {
            stage.clear(); // Limpia los actores
            stage.dispose(); // Libera recursos gráficos asociados
            stage = null; // Evita llamar a dispose() nuevamente
        }
        if (renderManager != null) {
            RenderManager.resetInstance(); // Libera recursos del RenderManager
        }
    }

}
