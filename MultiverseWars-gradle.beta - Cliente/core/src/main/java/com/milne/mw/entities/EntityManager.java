package com.milne.mw.entities;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.difficulty.Round;
import com.milne.mw.difficulty.RoundManager;
import com.milne.mw.entities.boss.BossCharacter;
import com.milne.mw.entities.flycharacter.Bomb;
import com.milne.mw.entities.rangedcharacter.Projectile;
import com.milne.mw.menu.GameOverMenu;
import com.milne.mw.menu.VictoryMenu;
import com.milne.mw.player.Player;
import com.milne.mw.renders.BossAnimator;

import java.util.*;

import static com.milne.mw.globals.Global.loadTexture;

public class EntityManager {
    private Stage stage;
    private Difficulty difficultyLevel;
    private Player player;
    private Array<Character> characters;
    private ArrayList<Rectangle> placementHitboxes;
    private HashMap<Integer, Character> positionMap;
    private final float INITIAL_X = 33;
    private final float INITIAL_Y = 42;
    private final float CELL_WIDTH = 62;
    private final float CELL_HEIGHT = 79;
    private final int COLS = 10;
    private final int ROWS = 5;
    private final float HITBOX_SIZE = 55;
    private float spawnInterval;
    private float spawnAccumulator;
    private boolean isPaused = false;
    private Array<Projectile> projectiles;
    private Array<Bomb> bombs;
    private RoundManager roundManager;
    private int enemiesInGame = 0;
    private int currentRoundIndex = 0;
    private VictoryMenu victoryMenu;
    private GameOverMenu gameOverMenu;
    private boolean bossIsAlive = false;
    private Character bossFinal;
    private EntityType[] enemyTypes = EntityType.values();
    private Array<EntityType> enemyList = new Array<>();
    private float enemiesSpawnAccumulator = 0;
    private float bossScalingFactor;

    public EntityManager(Stage stage, Difficulty difficultyLevel, Player player) {
        this.stage = stage;
        this.difficultyLevel = difficultyLevel;
        this.player = player;
        characters = new Array<>();
        projectiles = new Array<>();
        bombs = new Array<>();
        this.placementHitboxes = new ArrayList<>();
        initPlacementPoints();

        roundManager = new RoundManager();
    }

    public void initPlacementPoints() {
        placementHitboxes.clear();
        positionMap = new HashMap<>();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                float centerX = INITIAL_X + i * CELL_WIDTH;
                float centerY = INITIAL_Y + j * CELL_HEIGHT;
                 Rectangle hitbox = new Rectangle(
                     centerX - HITBOX_SIZE / 2,
                     centerY - HITBOX_SIZE / 2,
                     HITBOX_SIZE,
                     HITBOX_SIZE
                 );

                 placementHitboxes.add(hitbox);
            }
        }
    }

    public void handleEntityPlacement(EntityType entityType, float x, float y, float cardWidth, float cardHeight) {
        boolean placed = false;
        int i = 0;
        Rectangle cardArea = new Rectangle(x, y, cardWidth, cardHeight);

        if (entityType.getEnergy() <= player.getEnergy() && !placementHitboxes.isEmpty()) {
            do {
                Rectangle hitbox = placementHitboxes.get(i);
                if (!positionMap.containsKey(i) && hitbox.overlaps(cardArea)) {
                    float centerX = hitbox.x + hitbox.width / 2;
                    float centerY = hitbox.y + hitbox.height / 2;
                    Character entity = spawnEntity(entityType, centerX, centerY);
                    positionMap.put(i, entity);
                    placed = true;
                    player.modifyEnergy(-entityType.getEnergy());
                }
                i++;
            } while (!placed && i < placementHitboxes.size());
        }
    }

    public Character spawnEntity(EntityType entityType, float x, float y) {
        float adjustedX = x - (float) entityType.getHitboxWidth() / 2;
        float adjustedY = y - (float) entityType.getHitboxHeight() / 2;

        Character entity = entityType.getEntity(adjustedX, adjustedY, this);
        entity.scaleStats(difficultyLevel.getScalingFactor(), currentRoundIndex);
       // entity.getImage().setPosition(adjustedX, adjustedY);
        //stage.addActor(entity.getImage());
        characters.add(entity);

        return entity;
    }

    public void spawnBossFinal(Character bossFinal, float x, float y) {
        bossFinal.getImage().setPosition(x, y);
        stage.addActor(bossFinal.getImage());
        characters.add(bossFinal);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
        stage.addActor(projectile.getImage());
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
        stage.addActor(bomb.getImage()); // Añadir la imagen de la bomba al escenario
    }

    public void removeBomb(Bomb bomb) {
        bombs.removeValue(bomb, true);
        stage.getActors().removeValue(bomb.getImage(), true);
        bomb.dispose();
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.removeValue(projectile, true);
        stage.getActors().removeValue(projectile.getImage(), true);
        projectile.dispose();
    }

    public List<Rectangle> getPlacementHitboxes() {
            return placementHitboxes;
    }

    public void startEnemySpawner() {
        this.spawnInterval = difficultyLevel.getIntervalSpawn();
        this.spawnAccumulator = 0;
    }

    private void spawnRoundEnemy() {
        Round currentRound = roundManager.getRound(currentRoundIndex);

        if (!currentRound.getEnemies().isEmpty()) {
            EntityType enemyType = currentRound.getEnemy(0);
            currentRound.getEnemies().remove(0);
            float y = currentRound.getyPosition(0);
            currentRound.getyPositions().remove(0);
            float x = stage.getViewport().getWorldWidth();
            spawnEntity(enemyType, x, y);
            spawnAccumulator = 0;
            enemiesInGame++;
            System.out.println("Ingresé al primer if");
        } else if (enemiesInGame == 0 && !bossIsAlive) {
            currentRoundIndex++;
            System.out.println("Sumando...");
            scaleStatsAllPlacedCharacters();
        }

        if (currentRoundIndex == difficultyLevel.getMaxRound() && !bossIsAlive) {
            bossIsAlive = true;
            BossAnimator animator = new BossAnimator(stage);
            int hitboxWidth = 150;
            int hitboxHeight = 150;
            float adjustedX = stage.getViewport().getWorldWidth() - (float) hitboxWidth / 2;
            float adjustedY = 200.0f - (float) hitboxHeight / 2;
            bossFinal = new BossCharacter(
                loadTexture("characters/boss/boss.png"),
                adjustedX,
                adjustedY,
                hitboxWidth, hitboxHeight,
                difficultyLevel.getBossLives(),
                this,
                25,
                loadTexture("characters/boss/boss.png"),
                loadTexture("characters/boss/boss.png"),
                loadTexture("characters/boss/force-aura.png"),
                "enemy", 0, 1, 0, animator, true
            );
            spawnBossFinal(bossFinal, adjustedX, adjustedY);
            bossScalingFactor = difficultyLevel.getBossScalingFactor();
        } else if(currentRoundIndex > difficultyLevel.getMaxRound()) {
            placementHitboxes.clear();
            victoryMenu.createMenu();
        }
    }

    private void startSpawnEnemies() {
        for (EntityType type : enemyTypes) {
            if (type.getType().equalsIgnoreCase("enemy") && type.getCanBeSpawned()) {
                enemyList.add(type);
            }
        }

        if (!enemyList.isEmpty()) {
            Random r = new Random();
            int randomIndex = (int) (Math.random() * enemyList.size);
            EntityType randomEnemy = enemyList.get(randomIndex);
            float[] validYlanes = { 358.0f, 279.0f, 200.0f, 121.0f, 42.0f };

            float spawnX = stage.getViewport().getWorldWidth();
            float spawnY = validYlanes[r.nextInt(validYlanes.length)];
            Character enemy = spawnEntity(randomEnemy, spawnX, spawnY);
            enemy.scaleStatsBoss(bossScalingFactor);
        }
    }

    private void scaleStatsAllPlacedCharacters() {
        for (Character character : characters) {
            character.scaleStats(difficultyLevel.getScalingFactor(), currentRoundIndex);
        }
    }

    private void reduceInterval() {
        float minIntervalSpawn = 1.5f;
        spawnInterval -= 0.1f;

        if (spawnInterval < minIntervalSpawn) {
            spawnInterval = minIntervalSpawn;
        }
    }

    public void update(float delta) {
        if (!isPaused) {
            if (bossIsAlive) {
                if (bossFinal.getLives() <= 0) {
                    bossIsAlive = false;
                    bossFinal = null;
                    currentRoundIndex++;
                }
                bossScalingFactor *= delta;

                if (bossScalingFactor >= 1f) {
                    bossScalingFactor = difficultyLevel.getBossScalingFactor();
                }

                enemiesSpawnAccumulator += delta;
                if (enemiesSpawnAccumulator >= spawnInterval) {
                    startSpawnEnemies();
                    reduceInterval();
                    enemiesSpawnAccumulator = 0;
                }
            }

            player.update(delta);

            if (!player.isAlive()) {
                gameOverMenu.createMenu(player);
            }

            removeOffScreenCharacters();

            spawnAccumulator += delta;
            if (spawnAccumulator >= spawnInterval) {
                spawnRoundEnemy();
                spawnAccumulator = 0;
            }

            for (Character character : characters) {
                character.update(delta);
                character.checkForAttack(characters);
            }

            for (Projectile projectile : projectiles) {
                projectile.update(delta);
            }

            for (Bomb bomb : bombs) {
                bomb.update(delta);
            }

        }
    }

    public void handleRightClick(float clickX, float clickY) {
        boolean sold = false;
        int i = 0;
        do {
            Rectangle hitbox = placementHitboxes.get(i);
            if (hitbox.contains(clickX, clickY) && positionMap.containsKey(i)) {
                Character character = positionMap.get(i);

                if (character.getType().equalsIgnoreCase("tower")) {
                    player.modifyEnergy(Math.round(character.getEnergy() * 0.6f));
                    positionMap.remove(i);
                    removeCharacter(character);
                    sold = true;
                }
            }
            i++;
        } while(i < placementHitboxes.size() && !sold);
    }

    public void removeOffScreenCharacters() {
        for (int i = 0; i < characters.size; i++) {
            Character character = characters.get(i);
            if (character.getHitbox().x == character.getLEFT_LIMIT()) {
                character.setEnergy(0);
                player.loseLife(character.getDamageToPlayer());
                character.takeDamage(character.getLives());
            }
        }
    }

    public void releasePosition(Character character) {
        Integer index = null;

        for (Map.Entry<Integer, Character> entry : positionMap.entrySet()) {
            if (entry.getValue() == character) {
                index = entry.getKey();
            }
        }

        if (index != null) {
            positionMap.remove(index);
        }
    }

    public void pause() {
        isPaused = true;
        for (Character character : characters) {
            character.pause();
        }
    }

    public void resume() {
        isPaused = false;
        for (Character character : characters) {
            character.resumeMovement();
        }
    }

    public void setVictoryMenu(VictoryMenu victoryMenu) {
        this.victoryMenu = victoryMenu;
    }

    public Difficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setEnemiesInGame(int num) {
        enemiesInGame += num;
    }

    public int getRound() {
        return roundManager.getRound(currentRoundIndex).getRoundNumber();
    }

    public Array<Character> getCharacters() {
        return characters;
    }

    public float getCellWidth() {
        return CELL_WIDTH;
    }

    public float getCellHeight() {
        return CELL_HEIGHT;
    }

    public void removeCharacter(Character character) {
        if (character.getType().equalsIgnoreCase("enemy")) {
            player.modifyEnergy(character.getEnergy());
            enemiesInGame--;
        }
        releasePosition(character);
        character.getImage().remove();
        stage.getActors().removeValue(character.getImage(), true);
        characters.removeValue(character, true);
        character.dispose();
    }

    public void reset() {
        dispose();
        positionMap.clear();
        roundManager.reset();
        currentRoundIndex = 0;
        enemiesInGame = 0;
        bossIsAlive = false;
        bossFinal = null;
        enemyList.clear();
        initPlacementPoints();

        startEnemySpawner();
    }

    public void dispose() {
        for (Character character : characters) {
            character.dispose();
        }
        characters.clear();

        for (Bomb bomb : bombs) {
            bomb.dispose();
        }
        bombs.clear();

        for (Projectile projectile : projectiles) {
            projectile.dispose();
        }
        projectiles.clear();

    }

    public Array<Bomb> getBombs() {
        return bombs;
    }

    public HashMap<Integer, Character> getPositionMap() {
        return positionMap;
    }

    public void setGameOverMenu(GameOverMenu gameOverMenu) {
        this.gameOverMenu = gameOverMenu;
    }
}
