package com.milne.mw.difficulty;

import com.milne.mw.entities.EntityType;

public class EnemySpawn {
    private final Float Y_POSITION;
    private final EntityType ENTITY_TYPE;

    public EnemySpawn(Float yPosition, EntityType entityType) {
        this.Y_POSITION = yPosition;
        this.ENTITY_TYPE = entityType;
    }

    public Float getYPosition() {
        return Y_POSITION;
    }

    public EntityType getENTITY_TYPE() {
        return ENTITY_TYPE;
    }
}
