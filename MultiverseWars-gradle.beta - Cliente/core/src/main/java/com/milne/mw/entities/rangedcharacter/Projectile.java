package com.milne.mw.entities.rangedcharacter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;

public class Projectile {
    private Image image;
    private Rectangle hitbox;
    private int damage;
    private com.milne.mw.entities.Character targetEnemy;
    private EntityManager entityManager;
    private String type;

    public Projectile(Texture texture, float x, float y, EntityManager entityManager, Character targetEnemy, String type, int damage) {
        this.image = new Image(texture);
        this.image.setSize(20, 20);
        this.image.setPosition(x, y);
        this.hitbox = new Rectangle(x, y, 20, 20);
        this.entityManager = entityManager;
        this.targetEnemy = targetEnemy;
        this.type = type;
        this.damage = damage;

        moveAction();
    }

    private void moveAction() {
        MoveToAction moveAction = new MoveToAction();
        if (this.type.equalsIgnoreCase("tower")) {
            moveAction.setPosition(hitbox.x + 800, hitbox.y);
        } else {
            moveAction.setPosition(hitbox.x - 800, hitbox.y);
        }
        moveAction.setDuration(2);
        image.addAction(moveAction);
    }

    public void update(float delta) {
        updateHitbox();
        checkForCollision();
    }

    private void updateHitbox() {
        hitbox.setPosition(image.getX(), image.getY());
    }

    private void checkForCollision() {
        if (targetEnemy != null && hitbox.overlaps(targetEnemy.getHitbox())) {
            targetEnemy.takeDamage(damage);
            entityManager.removeProjectile(this);
        }
    }

    public Image getImage() {
        return image;
    }

    public void dispose() {
        image.clearActions();
        image.remove();
    }
}
