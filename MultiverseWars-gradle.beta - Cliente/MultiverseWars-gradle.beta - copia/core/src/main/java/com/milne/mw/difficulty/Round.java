package com.milne.mw.difficulty;

import com.milne.mw.entities.EntityType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Round {
    private final ArrayList<EntityType> enemies;
    private final ArrayList<Float> yPositions;
    private final int roundNumber;

    // Constructor opcional para añadir enemigos de forma directa
    public Round(int roundNumber, EnemySpawn... enemySpawns) {
        this.roundNumber = roundNumber;
        this.enemies = new ArrayList<>();
        this.yPositions = new ArrayList<>();

        for (EnemySpawn spawn : enemySpawns) {
            this.enemies.add(spawn.getEntityType());
            this.yPositions.add(spawn.getYPosition());
        }
    }

    public Float getyPosition(int index) {
        return yPositions.get(index);
    }

    public ArrayList<Float> getyPositions() {
        return yPositions;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public ArrayList<EntityType> getEnemies() {
        return enemies;
    }

    public EntityType getEnemy(int index) {
        return enemies.get(index);
    }
}
