package com.milne.mw.difficulty;

import com.milne.mw.entities.EntityType;

public class EnemySpawn {
    private final Float yPosition;
    private final EntityType entityType;

    public EnemySpawn(Float yPosition, EntityType entityType) {
        this.yPosition = yPosition;
        this.entityType = entityType;
    }

    public Float getYPosition() {
        return yPosition;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
