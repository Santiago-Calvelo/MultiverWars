package com.milne.mw.entities.flycharacter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.entities.EntityType;

public class Plane extends FlyCharacter{
    private int enemiesSpawned = 0;
    public Plane(Texture texture, int hitboxWidth, int hitboxHeight, Texture walkTexture, Texture attackTexture, float x, float y, int lives, int speed, EntityManager entityManager, String type, float attackCooldown, int damage, int energy, boolean canBeAttacked) {
        super(texture, hitboxWidth, hitboxHeight, walkTexture, attackTexture, x, y, lives, speed, entityManager, type, attackCooldown, damage, energy, canBeAttacked);
    }

    public void makeAttack() {
        final float MIDDLE_X = entityManager.getCellWidth() * 3;
        final int maxEnemies = 3;
        if (getHitbox().x >= MIDDLE_X && enemiesSpawned < maxEnemies) {


            EntityType[] enemyTypes = EntityType.values();
            Array<EntityType> enemyList = new Array<>();
            for (EntityType type : enemyTypes) {
                if (type.getType().equalsIgnoreCase("enemy") && type.getCanBeSpawned()) {
                    enemyList.add(type);
                }
            }

            if (!enemyList.isEmpty()) {
                int randomIndex = (int) (Math.random() * enemyList.size);
                EntityType randomEnemy = enemyList.get(randomIndex);

                float spawnX = getHitboxCenter().x;
                float spawnY = getHitboxCenter().y;
                entityManager.spawnEntity(randomEnemy, spawnX, spawnY);
                entityManager.setEnemiesInGame(1);
                enemiesSpawned++;
            }
        }
    }
}
