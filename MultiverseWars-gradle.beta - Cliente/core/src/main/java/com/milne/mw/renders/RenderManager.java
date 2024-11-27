package com.milne.mw.renders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.globals.Global;
import com.milne.mw.menu.PauseMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class RenderManager {
    private static RenderManager instance;
    private ShapeRenderer shapeRenderer;
    private Stage stage;

    private float walkAnimationTime;
   // private ArrayList<AttackAnimation> attackAnimations;
    private HashMap<String,Image> attackImages;
    private Skin skin;
    private Label roundLabel;
    private Label livesLabel;
    private Label energyLabel;

    public RenderManager(Stage stage) {
        this.stage = stage;
        this.shapeRenderer = new ShapeRenderer();
        //this.attackAnimations = new ArrayList<>();
        this.attackImages = new HashMap<>();
    }


    public void render(boolean isPaused, EntityManager entityManager, float delta, PauseMenu pauseMenu) {
       /* if (!isPaused) {
            stage.act(delta);
            entityManager.update(delta);
            updateWalkAnimation(delta, entityManager);
            updateLabels(entityManager.getRound());
        } */
        pauseMenu.checkForEscapeKey();
        stage.draw();

       /* if (!isPaused && Global.debugMode) {
            debugRender.drawHitboxes(entityManager);
            debugRender.drawPlacementZones(entityManager, pauseMenu);
            debugRender.drawBombRanges(entityManager);
        } */

        //updateAttackAnimations(delta);
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
        //attackAnimations.add(new AttackAnimation(character, cooldown));
    }

    private void updateAttackAnimations(float delta) {
        // Usa un iterador para actualizar y eliminar animaciones finalizadas
       /* Iterator<AttackAnimation> iterator = attackAnimations.iterator();
        while (iterator.hasNext()) {
            AttackAnimation animation = iterator.next();
            if (animation.update(delta)) {
                iterator.remove(); // Elimina la animación si ha terminado
            }
        } */
    }

    public void updateRoundLabels(int currentRound, int maxRound) {
        this.roundLabel.setText(currentRound + "/" + maxRound);
    }

    public void initializeRoundLabel() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        roundLabel = new Label("", skin);
        roundLabel.setFontScale(2f);
        roundLabel.setColor(Color.WHITE);

        Table rounds = new Table();
        rounds.top().right();
        rounds.setFillParent(true);
        rounds.add(roundLabel).padTop(20).padRight(20);
        stage.addActor(rounds);
    }

    public void updatePlayerLabels(int lives, int energy) {
        if (this.livesLabel == null || this.energyLabel == null) {
            initializePlayerLabels(lives, energy);
        } else {
            this.livesLabel.setText("Vidas: " + lives);
            this.energyLabel.setText("Energía: " + energy);
        }
    }


    public void initializePlayerLabels(int lives, int energy) {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        this.livesLabel = new Label("Vidas: " + lives, skin);
        this.livesLabel.setFontScale(1.5f);
        this.livesLabel.setColor(Color.RED);
        stage.addActor(this.livesLabel);

        this.energyLabel = new Label("Energía: " + energy, skin);
        this.energyLabel.setFontScale(1.5f);
        this.energyLabel.setColor(Color.BLUE);
        stage.addActor(this.energyLabel);

        // Posición
        Table energyAndLives = new Table();
        energyAndLives.top().left();
        energyAndLives.setFillParent(true);
        energyAndLives.add(this.livesLabel).padLeft(20).padTop(20).row();
        energyAndLives.add(this.energyLabel).padLeft(20);
        stage.addActor(energyAndLives);
    }

    public void drawBossAttack(String idAttack, String texture, float x, float y, float width, float height) {
        Image attack = new Image(Global.loadTexture(texture));
        attack.setSize(width,height);
        attack.setPosition(x,y);
        attack.setZIndex(1);
        stage.addActor(attack);

        attackImages.put(idAttack, attack);
    }

    public void updateBossAttack(String idAttack, float x, float y) {
        attackImages.get(idAttack).setPosition(x,y);
    }

    public void removeBossAttack(String idAttack) {
        Image attack = attackImages.get(idAttack);
        attack.remove();
        stage.getActors().removeValue(attack, true);
        attackImages.remove(idAttack);
    }

    public void dispose() {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
        if (skin != null) {
            skin.dispose();
            skin = null;
        }
    }
}
