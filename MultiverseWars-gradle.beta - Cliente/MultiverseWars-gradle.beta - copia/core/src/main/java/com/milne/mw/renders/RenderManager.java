package com.milne.mw.renders;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.milne.mw.entities.flycharacter.Bomb;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.Global;
import com.milne.mw.menu.GameOverMenu;
import com.milne.mw.menu.PauseMenu;
import com.milne.mw.player.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class RenderManager {
    private static RenderManager instance;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Image backgroundImage;
    private float walkAnimationTime;
    private ArrayList<AttackAnimation> attackAnimations;
    private int maxRound, currentRound;
    private Skin skin;
    private Label roundLabel;
    private Label livesLabel;
    private Label energyLabel;
    private Player player;
    private DebugRender debugRender;

    private RenderManager(Texture mapTexture, Stage stage) {
        this.stage = stage;
        this.shapeRenderer = new ShapeRenderer();
        this.backgroundImage = new Image(mapTexture);
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(backgroundImage);
        this.attackAnimations = new ArrayList<>();
        this.debugRender = new DebugRender(stage);
    }

    public static RenderManager getInstance(Texture mapTexture, Stage stage) {
        if (instance == null) {
            instance = new RenderManager(mapTexture, stage);
        }
        return instance;
    }

    public static void resetInstance() {
        if (instance != null) {
            instance.dispose();
            instance = null;
        }
    }

    public static RenderManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("RenderManager no ha sido inicializado. Llama a getInstance() con parámetros primero.");
        }
        return instance;
    }

    public void render(boolean isPaused, EntityManager entityManager, float delta, PauseMenu pauseMenu) {
        if (!isPaused) {
            stage.act(delta);
            entityManager.update(delta);
            updateWalkAnimation(delta, entityManager);
            updateLabels(entityManager.getRound());
        }
        pauseMenu.checkForEscapeKey();
        stage.draw();

        if (!isPaused && Global.debugMode) {
            debugRender.drawHitboxes(entityManager);
            debugRender.drawPlacementZones(entityManager, pauseMenu);
            debugRender.drawBombRanges(entityManager);
        }

        updateAttackAnimations(delta);
    }

    private void updateWalkAnimation(float delta, EntityManager entityManager) {
        walkAnimationTime += delta;

        // Alterna la textura de caminata cada 0.5 segundos
        if (walkAnimationTime >= 0.5f) {
            for (Character character : entityManager.getCharacters()) {
                if (character.getLives() > 0) {
                    TextureRegionDrawable currentDrawable = (TextureRegionDrawable) character.getImage().getDrawable();
                    TextureRegionDrawable nextDrawable = (currentDrawable.getRegion().getTexture() == character.getWalk1Texture())
                        ? new TextureRegionDrawable(character.getWalk2Texture())
                        : new TextureRegionDrawable(character.getWalk1Texture());

                    character.getImage().setDrawable(nextDrawable);
                }
            }
            walkAnimationTime = 0;
        }
    }

    // Método para iniciar la animación del ataque
    public void animateCharacterAttack(Character character, float cooldown) {
        // Crea una nueva animación de ataque y la añade a la lista
        attackAnimations.add(new AttackAnimation(character, cooldown));
    }

    private void updateAttackAnimations(float delta) {
        // Usa un iterador para actualizar y eliminar animaciones finalizadas
        Iterator<AttackAnimation> iterator = attackAnimations.iterator();
        while (iterator.hasNext()) {
            AttackAnimation animation = iterator.next();
            if (animation.update(delta)) {
                iterator.remove(); // Elimina la animación si ha terminado
            }
        }
    }

    private void updateLabels(int currentRound) {
        if (this.currentRound != currentRound) {
            this.currentRound = currentRound;
        }
        roundLabel.setText(currentRound + "/" + maxRound);
        livesLabel.setText("Vidas: " + player.getLives());
        energyLabel.setText("Energía: " + player.getEnergy());
    }

    public void initializeLabels(int currentRound) {

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        roundLabel = new Label("", skin);
        roundLabel.setFontScale(2f);
        roundLabel.setColor(Color.WHITE);

        Table rounds = new Table();
        rounds.top().right();
        rounds.setFillParent(true);
        rounds.add(roundLabel).padTop(20).padRight(20);
        stage.addActor(rounds);

        livesLabel = new Label("Vidas: " + player.getLives(), skin);
        livesLabel.setFontScale(1.5f);
        livesLabel.setColor(Color.RED);
        stage.addActor(livesLabel);

        energyLabel = new Label("Energía: " + player.getEnergy(), skin);
        energyLabel.setFontScale(1.5f);
        energyLabel.setColor(Color.BLUE);
        stage.addActor(energyLabel);

        // Posición
        Table energyAndLives = new Table();
        energyAndLives.top().left();
        energyAndLives.setFillParent(true);
        energyAndLives.add(livesLabel).padLeft(20).padTop(20).row();
        energyAndLives.add(energyLabel).padLeft(20);
        stage.addActor(energyAndLives);

        updateLabels(currentRound);
    }

    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
        if (backgroundImage != null) {
            backgroundImage.remove();
            backgroundImage = null;
        }
        if (skin != null) {
            skin.dispose();
            skin = null;
        }
    }
}
