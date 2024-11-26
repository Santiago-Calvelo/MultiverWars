package com.milne.mw.entities.flycharacter;

import com.badlogic.gdx.graphics.Texture;
import com.milne.mw.entities.EntityManager;

public class Bloon extends FlyCharacter{
    public Bloon(Texture texture, int hitboxWidth, int hitboxHeight, Texture walkTexture, Texture attackTexture, float x, float y, int lives, int speed, EntityManager entityManager, String type, float attackCooldown, int damage, int energy, boolean canBeAttacked) {
        super(texture, hitboxWidth, hitboxHeight, walkTexture, attackTexture, x, y, lives, speed, entityManager, type, attackCooldown, damage, energy, canBeAttacked);
    }

    @Override
    public void makeAttack() {
        float bombX = getHitboxCenter().x + entityManager.getCellWidth();
        float bombY = getHitbox().y + image.getHeight();
        float targetY = getHitbox().y;
        Bomb bomb = new Bomb(bombX, bombY, getDamage(), entityManager, targetY);
        entityManager.addBomb(bomb);
    }

}
