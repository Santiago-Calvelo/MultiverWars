package com.milne.mw.entities.rangedcharacter;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;

public class Projectile {
    private Image image;
    private String texture;
    private Rectangle hitbox;
    private int damage;
    private Character targetEnemy;
    private EntityManager entityManager;
    private String type;
    private int id = 0;

    public Projectile(String texture, float x, float y, EntityManager entityManager, Character targetEnemy, String type, int damage) {
        this.texture = texture;
        this.image = new Image(Global.loadTexture(texture));
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
        if (Global.multiplayer) {
            NetworkData.serverThread.sendMessageToAll("moveentity!" + id + "!" + hitbox.x + "!" + hitbox.y);
        }
        hitbox.setPosition(image.getX(), image.getY());
    }

    public String getTexture() {
        return texture;
    }

    public Rectangle getHitbox() {
        return hitbox;
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

    public void setId(int idProjectile) {
        this.id = idProjectile;
    }

    public int getId() {
        return id;
    }
}
