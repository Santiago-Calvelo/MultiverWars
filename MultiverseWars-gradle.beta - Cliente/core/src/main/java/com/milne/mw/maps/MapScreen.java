package com.milne.mw.maps;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.milne.mw.entities.SellTowerListener;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.menu.GameOverMenu;
import com.milne.mw.menu.PauseMenu;
import com.milne.mw.menu.VictoryMenu;
import com.milne.mw.renders.RenderManager;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.MusicManager;

import java.util.HashMap;

public class MapScreen implements Screen {
    private Image background;
    private Stage stage;
    private RenderManager renderManager;
    private EntityManager entityManager;
    private PauseMenu pauseMenu;
    private VictoryMenu victoryMenu;
    private GameOverMenu gameOverMenu;
    private HashMap<Integer, Image> entities = new HashMap<>();
    private boolean gameover = false;

    public MapScreen(String map) {
        this.stage = new Stage(new FitViewport(800, 600));
        Gdx.input.setInputProcessor(stage);
        renderManager = new RenderManager(stage);
        this.background = new Image(Global.loadTexture(map));
        background.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(background);
        //player = new Player(difficultyLevel.getInitalLives(), difficultyLevel.getInitialEnergy(), difficultyLevel.getEnergyGenerationRate());
        //entityManager = new EntityManager(stage, difficultyLevel);
        pauseMenu = new PauseMenu(stage);
        victoryMenu = new VictoryMenu(stage);
        gameOverMenu = new GameOverMenu(stage);

       // entityManager.startEnemySpawner();
        //entityManager.setVictoryMenu(victoryMenu);
        //entityManager.setGameOverMenu(gameOverMenu);
        //stage.addListener(new SellTowerListener(entityManager));

       // renderManager.setPlayer(player);
        //renderManager.setMaxRound(difficultyLevel.getMaxRound());
       // renderManager.initializeLabels(entityManager.getRound());

        readyForGame();
    }

    public void createGameOverMenu() {
        gameover = true;
        gameOverMenu.createMenu();
    }

    public void createVictoryMenu() {
        victoryMenu.createMenu();
    }

    public void readyForGame() {
        NetworkData.clientThread.sendMessage("readyforgame!");
        renderManager.initializeRoundLabel();
    }

    public void addEntity(int id, float x, float y, String entityImage, float hitboxWidth, float hitboxHeight) {
        System.out.println(id);
        Image entity = new Image(Global.loadTexture(entityImage));
        entity.setSize(hitboxWidth, hitboxHeight);
        entity.setPosition(x,y);
        stage.addActor(entity);

        entities.put(id, entity);
    }

    public void addCardsToPanel(String cardImage, float x, float y, int width, int height, String entityType) {
        Image card = new Image(Global.loadTexture(cardImage));

        card.setSize(width,height);
        card.setPosition(x,y);

        CardDragListener cardDragListener = new CardDragListener(entityType,card);
        card.addListener(cardDragListener);
        stage.addActor(card);

    }

    public void removeEntity(int id) {
        Image entity = entities.get(id);
        if (entity != null) {
            entity.remove();
            stage.getActors().removeValue(entity, true);
            entities.remove(id);
        }
    }

    public void exploteBomb(int id, float x, float y, String explosionPath, float width, float height) {
        Image entity = entities.get(id);
        entity.setDrawable(new TextureRegionDrawable(Global.loadTexture(explosionPath)));
        entity.setSize(width,height);
        entity.setPosition(x,y);
    }

    public void moveEntity(int id, float currentX, float currentY) {
        Image entity = entities.get(id);
        if (entity != null) {
            entity.setPosition(currentX, currentY);
        } else {
            System.err.println("No se encontró la entidad con ID: " + id);
        }
    }


    public void animateTextureEntity(int id, String walkTexture) {
        Image entity = entities.get(id);
        if (entity != null) {
            entity.setDrawable(new TextureRegionDrawable(Global.loadTexture(walkTexture)));
        } else {
            System.err.println("No se encontró la entidad con ID: " + id);
        }
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
        if (Gdx.input.justTouched() && !gameover) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            Vector2 worldTouch = stage.getViewport().unproject(new Vector2(touchX, touchY));
            pauseMenu.handleInput(worldTouch.x, worldTouch.y);
        }

        if (Gdx.input.justTouched() && gameover) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.input.getY();
            Vector2 worldTouch = stage.getViewport().unproject(new Vector2(touchX, touchY));
            gameOverMenu.handleInput(worldTouch.x, worldTouch.y);
        }
        //renderManager.render(pauseMenu.getIsPaused(), entityManager, delta, pauseMenu);
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
        if (background != null) {
            background.remove();
            background = null;
        }
        if (stage != null) {
            stage.clear(); // Limpia los actores
            stage.dispose(); // Libera recursos gráficos asociados
            stage = null; // Evita llamar a dispose() nuevamente
        }
        if (renderManager != null) {
            renderManager.dispose(); // Libera recursos del RenderManager
        }
    }

    public RenderManager getRenderManager() {
        return this.renderManager;
    }
}
