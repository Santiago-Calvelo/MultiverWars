package com.milne.mw.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.entities.flycharacter.FlyCharacter;
import com.milne.mw.renders.RenderManager;

public abstract class Character {
    protected Image image;
    private final Rectangle hitbox;
    private int lives;
    private EntityType entityType;
    protected EntityManager entityManager;
    private int speed;
    private final Texture walk1Texture, walk2Texture, attack1Texture, attack2Texture;
    private MoveToAction moveAction;
    private boolean isMoving, canAttack;
    private final float attackCooldown;
    private float cooldownElapsed;
    private final String type;
    private int damage, energy;
    private final int BASE_LIVES, BASE_SPEED, BASE_DAMAGE;
    private int lastScaledRound = -1;
    private final boolean canBeAttacked;
    private int damageToPlayer;
    private float targetX;
    private final float RIGHT_LIMIT, LEFT_LIMIT;

    public Character(Texture texture, float x, float y, int hitboxWidth, int hitboxHeight, int lives,
                     EntityManager entityManager, int speed,
                     Texture walk1Texture, Texture walk2Texture, Texture attack1Texture,
                     Texture attack2Texture, String type,
                     float attackCooldown, int damage, int energy, boolean canBeAttacked, int damageToPlayer) {
        this.image = new Image(texture);
        this.image.setPosition(x, y);
        if (!(this instanceof FlyCharacter)) {
            this.image.setSize(hitboxWidth, hitboxHeight);
        }
        this.hitbox = new Rectangle(x, y, hitboxWidth, hitboxHeight);
        this.lives = lives;
        this.BASE_LIVES = lives;
        this.entityManager = entityManager;
        this.walk1Texture = walk1Texture;
        this.walk2Texture = walk2Texture;
        this.attack1Texture = attack1Texture;
        this.attack2Texture = attack2Texture;
        this.isMoving = false;
        this.speed = speed;
        this.BASE_SPEED = speed;
        this.type = type;
        this.canAttack = false;
        this.attackCooldown = attackCooldown;
        this.cooldownElapsed = 0;
        this.damage = damage;
        this.BASE_DAMAGE = damage;
        this.energy = energy;
        this.canBeAttacked = canBeAttacked;
        this.damageToPlayer = damageToPlayer;
        this.RIGHT_LIMIT = x;
        this.LEFT_LIMIT = 0f;

        this.targetX = LEFT_LIMIT;
        if (speed != 0) {
            startMovement();
        }
    }

    public void startMovement() {
        moveAction = new MoveToAction();
        float distanceX = Math.abs(hitbox.x - targetX);
        float duration = distanceX / speed;
        moveAction.setPosition(targetX, getHitbox().y);
        moveAction.setDuration(duration);
        image.addAction(moveAction);
        isMoving = true;
    }

    public void update(float delta) {
        cooldownElapsed += delta;

        if (!canAttack && cooldownElapsed >= attackCooldown) {
            canAttack = true;
            cooldownElapsed = 0; // Reinicia cooldown después de estar listo
        }
    }

    public void scaleStatsBoss(float scalingFactor) {
        this.lives = Math.round(BASE_LIVES * scalingFactor);
        this.damage = Math.round(BASE_DAMAGE * scalingFactor);
        this.speed = Math.round(BASE_SPEED * Math.min(scalingFactor, 1.05f));
    }

    public void scaleStats(float scalingFactor, int roundNumber) {
        if (lastScaledRound != roundNumber) {
            lastScaledRound = roundNumber;

            float multiplier = 1.0f + (roundNumber * scalingFactor);
            if (!this.getType().equalsIgnoreCase("tower")) {
                this.lives = Math.round(BASE_LIVES * multiplier);
            }
            this.damage = Math.round(BASE_DAMAGE * multiplier);
            this.speed = Math.round(BASE_SPEED * Math.min(multiplier, 1.05f));
        }
    }

    public void removeCharacter() {
        entityManager.removeCharacter(this);
    }

    public void tryAttack() {
        RenderManager.getInstance().animateCharacterAttack(this, attackCooldown);

        if (this instanceof FlyCharacter) {
            this.isMoving = false;
        }
        if (canAttack && !isMoving) {
            attack();
            canAttack = false;
            cooldownElapsed = 0;
        }
    }

    public abstract void attack();
    public abstract void checkForAttack(Array<Character> characters);

    public void takeDamage(int damage) {
        this.lives -= damage;
        if (lives <= 0) {
            entityManager.removeCharacter(this);
        }
    }

    public void pause() {
        if (isMoving) {
            image.clearActions();
            isMoving = false;
        }
    }

    public void resumeMovement() {
        if (!isMoving && BASE_SPEED != 0) {
            startMovement();
        }
    }

    public void stopMovementAndAttack() {
        if (isMoving) {
            pause();
        }
        tryAttack();
    }

    public Image getImage() { return image; }
    public String getType() { return type; }
    public Texture getWalk1Texture() { return walk1Texture; }
    public Texture getWalk2Texture() { return walk2Texture; }
    public Texture getAttack1Texture() { return attack1Texture; }
    public Texture getAttack2Texture() { return attack2Texture; }

    public float getAttackCooldown() {
            return attackCooldown;
    }

    public boolean getCanBeAttacked () {
        return canBeAttacked;
    }

    public Rectangle getHitbox() {
        hitbox.setPosition(image.getX(), image.getY());
        return hitbox;
    }

    public float getRIGHT_LIMIT() {
        return RIGHT_LIMIT;
    }

    public float getLEFT_LIMIT() {
        return LEFT_LIMIT;
    }

    public int getBASE_SPEED() {
        return BASE_SPEED;
    }

    public int getDamage() { return damage; }
    public int getEnergy() { return energy; }
    public int getSpeed() { return speed; }
    public int getLives() { return lives; }

    protected void setTargetX(float targetX) {
        this.targetX = targetX;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Vector2 getHitboxCenter() {
        return new Vector2(hitbox.x + hitbox.width / 2, hitbox.y + hitbox.height / 2);
    }

    public void dispose() {
        image.clearActions();
        image.remove();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getDamageToPlayer() {
        return damageToPlayer;
    }
}

