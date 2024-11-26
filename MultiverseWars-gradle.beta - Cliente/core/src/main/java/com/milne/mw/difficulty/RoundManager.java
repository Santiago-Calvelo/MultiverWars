package com.milne.mw.difficulty;

import com.milne.mw.entities.EntityType;

import java.util.ArrayList;

public class RoundManager {
    private ArrayList<Round> rounds;

    public RoundManager() {
        this.rounds = new ArrayList<>();
        configureRounds();
    }

    private void configureRounds() {
        rounds.add(new Round(1,
            new EnemySpawn(358f, EntityType.SKELETON),
            new EnemySpawn(200f, EntityType.SKELETON),
            new EnemySpawn(121f, EntityType.SKELETON)
        ));
        rounds.add(new Round(2,
            new EnemySpawn(279f, EntityType.SKELETON),
            new EnemySpawn(121f, EntityType.SKELETON),
            new EnemySpawn(42f, EntityType.STORMTROOPER)
        ));
        rounds.add(new Round(3,
            new EnemySpawn(200f, EntityType.SKELETON),
            new EnemySpawn(42f, EntityType.STORMTROOPER),
            new EnemySpawn(358f, EntityType.STORMTROOPER)
        ));
        rounds.add(new Round(4,
            new EnemySpawn(42.0f, EntityType.STORMTROOPER),
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(42.0f, EntityType.SKELETON)
        ));
        rounds.add(new Round(5,
            new EnemySpawn(42.0f, EntityType.SKELETON),
            new EnemySpawn(121.0f, EntityType.SKELETON),
            new EnemySpawn(200.0f, EntityType.BARBARIAN)
        ));
        rounds.add(new Round(6,
            new EnemySpawn(358.0f, EntityType.BARBARIAN),
            new EnemySpawn(42.0f, EntityType.SKELETON),
            new EnemySpawn(279.0f, EntityType.SKELETON)
        ));
        rounds.add(new Round(7,
            new EnemySpawn(42.0f, EntityType.STORMTROOPER),
            new EnemySpawn(121.0f, EntityType.BARBARIAN),
            new EnemySpawn(200.0f, EntityType.STORMTROOPER)
        ));
        rounds.add(new Round(8,
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(121.0f, EntityType.BARBARIAN),
            new EnemySpawn(200.0f, EntityType.SOLDIER)
        ));
        rounds.add(new Round(9,
            new EnemySpawn(279.0f, EntityType.SOLDIER),
            new EnemySpawn(358.0f, EntityType.BARBARIAN),
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(200.0f, EntityType.STORMTROOPER)
        ));
        rounds.add(new Round(10,
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(200.0f, EntityType.BARBARIAN),
            new EnemySpawn(42.0f, EntityType.SKELETON),
            new EnemySpawn(279.0f, EntityType.SOLDIER),
            new EnemySpawn(200.0f, EntityType.STORMTROOPER)
        ));
        rounds.add(new Round(11,
            new EnemySpawn(42.0f, EntityType.SOLDIER),
            new EnemySpawn(279.0f, EntityType.BARBARIAN),
            new EnemySpawn(121.0f, EntityType.STORMTROOPER),
            new EnemySpawn(279.0f, EntityType.SKELETON)
        ));
        rounds.add(new Round(12,
            new EnemySpawn(42.0f, EntityType.SKELETON),
            new EnemySpawn(279.0f, EntityType.BARBARIAN),
            new EnemySpawn(121.0f, EntityType.SKELETON),
            new EnemySpawn(121.0f, EntityType.STORMTROOPER),
            new EnemySpawn(121.0f, EntityType.BARBARIAN)
        ));
        rounds.add(new Round(13,
            new EnemySpawn(200.0f, EntityType.SOLDIER),
            new EnemySpawn(279.0f, EntityType.BARBARIAN),
            new EnemySpawn(358.0f, EntityType.STORMTROOPER),
            new EnemySpawn(200.0f, EntityType.STORMTROOPER)
        ));
        rounds.add(new Round(14,
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(42.0f, EntityType.BARBARIAN),
            new EnemySpawn(42.0f, EntityType.SOLDIER),
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(121.0f, EntityType.BARBARIAN)
        ));
        rounds.add(new Round(15,
            new EnemySpawn(200.0f, EntityType.BARBARIAN),
            new EnemySpawn(42.0f, EntityType.BARBARIAN),
            new EnemySpawn(200.0f, EntityType.STORMTROOPER),
            new EnemySpawn(121.0f, EntityType.SOLDIER),
            new EnemySpawn(42.0f, EntityType.SOLDIER)
        ));
        rounds.add(new Round(16,
            new EnemySpawn(200.0f, EntityType.PLANE),
            new EnemySpawn(279.0f, EntityType.SKELETON),
            new EnemySpawn(358.0f, EntityType.BARBARIAN),
            new EnemySpawn(121.0f, EntityType.SKELETON)
        ));
        rounds.add(new Round(17,
            new EnemySpawn(121.0f, EntityType.STORMTROOPER),
            new EnemySpawn(358.0f, EntityType.BARBARIAN),
            new EnemySpawn(279.0f, EntityType.SOLDIER)
        ));
        rounds.add(new Round(18,
            new EnemySpawn(121.0f, EntityType.PLANE),
            new EnemySpawn(42.0f, EntityType.SOLDIER),
            new EnemySpawn(279.0f, EntityType.STORMTROOPER),
            new EnemySpawn(358.0f, EntityType.SKELETON),
            new EnemySpawn(42.0f, EntityType.PLANE)
        ));
        rounds.add(new Round(19,
            new EnemySpawn(121.0f, EntityType.BARBARIAN),
            new EnemySpawn(42.0f, EntityType.STORMTROOPER),
            new EnemySpawn(121.0f, EntityType.SOLDIER),
            new EnemySpawn(42.0f, EntityType.SKELETON),
            new EnemySpawn(42.0f, EntityType.PLANE)
        ));
        rounds.add(new Round(20,
            new EnemySpawn(200.0f, EntityType.PLANE),
            new EnemySpawn(358.0f, EntityType.PLANE),
            new EnemySpawn(279.0f, EntityType.BARBARIAN),
            new EnemySpawn(42.0f, EntityType.SOLDIER),
            new EnemySpawn(358.0f, EntityType.STORMTROOPER),
            new EnemySpawn(121.0f, EntityType.SKELETON)
        ));
    }

    public Round getRound(int index) {
        return rounds.get(index);
    }

    public void reset() {
        rounds.clear();
        configureRounds();
    }
}
