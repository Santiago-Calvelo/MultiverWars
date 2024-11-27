package com.milne.mw.entities.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.Array;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.renders.BossAnimator;

import java.util.HashMap;
import java.util.Random;

public class BossCharacter extends Character {
    private float forceSmashAccumulator = 0f;
    private float dashAttackAccumulator = 0f;
    private float moveForceAccumulator = 0f;
    private float moveForceCooldown, forceSmashCooldown, dashAttackCooldown;
    private boolean isExecutingSpecial = false;
    private float switchLaneAccumulator = 0f;
    private float specialDuration = 0f;
    private BossAttacks lastAttack;
    private BossAnimator animator;
    private BossAttacks currentSpecialAttack = null;
    private HashMap<BossAttacks, BossAttack> attackMap = new HashMap<>();
    private boolean isDashing = false;
    private boolean movingRight = true;

    public BossCharacter(String texture, float x, float y, int hitboxWidth, int hitboxHeight, int lives,
                         EntityManager entityManager, int speed, Texture walkTexture, String attackTexture, String forceSmash, String type, float attackCooldown, int damage, int energy, BossAnimator animator, boolean canBeAttacked) {
        super(texture, x, y, hitboxWidth, hitboxHeight, lives, entityManager, speed,
            walkTexture, walkTexture, attackTexture, attackTexture, type, attackCooldown, damage, energy, canBeAttacked,0);
        this.animator = animator;
        attackMap.put(BossAttacks.FORCE_SMASH, new ForceSmashAttack(forceSmash));
        attackMap.put(BossAttacks.MOVE_FORCE, new MoveForceAttack());
        attackMap.put(BossAttacks.DASH_ATTACK, new DashAttack());
        this.moveForceCooldown = entityManager.getDifficultyLevel().getMoveForceCooldown();
        this.forceSmashCooldown = entityManager.getDifficultyLevel().getForceSmashCooldown();
        this.dashAttackCooldown = entityManager.getDifficultyLevel().getDashCooldown();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (getLives() > 0) {
            checkLimitsMaps();

            switchLaneAccumulator += delta;
            if (switchLaneAccumulator >= 10f && !isDashing) {
                switchLane();
            }

            if (isExecutingSpecial) {
                // Continúa ejecutando el ataque especial actual
                specialDuration += delta;
                if (specialDuration >= getCurrentSpecialDuration()) {
                    finishSpecial(); // Finaliza el ataque especial
                }
            } else {
                // Acumula tiempo para activar el siguiente ataque especial
                forceSmashAccumulator += delta;
                moveForceAccumulator += delta;
                dashAttackAccumulator += delta;

                if (forceSmashAccumulator >= forceSmashCooldown && lastAttack != BossAttacks.FORCE_SMASH) { // Cooldown de Force Smash
                    startSpecialAttack(BossAttacks.FORCE_SMASH);
                } else if (moveForceAccumulator >= moveForceCooldown && lastAttack != BossAttacks.MOVE_FORCE) { // Cooldown de Move Force
                    startSpecialAttack(BossAttacks.MOVE_FORCE);
                } else if (dashAttackAccumulator >= dashAttackCooldown && lastAttack != BossAttacks.DASH_ATTACK) {
                    startSpecialAttack(BossAttacks.DASH_ATTACK);
                    isDashing = true;
                }
            }

        }
    }

    private void checkLimitsMaps() {
        float hitboxX = this.getHitbox().x;
        if (isNearLimit(hitboxX, getLEFT_LIMIT()) && !movingRight) {
            movingRight = true;
            changeDirection();
        } else if (isNearLimit(hitboxX, getRIGHT_LIMIT()) && movingRight) {
            movingRight = false;
            changeDirection();
        }
    }

    private void changeDirection() {
        float targetX = getMovingDirectionTargetX();
        super.setTargetX(targetX);
        startMovement();
    }

    private void startSpecialAttack(BossAttacks attack) {
        BossAttack bossAttack = attackMap.get(attack);

        if (bossAttack != null) {
            isExecutingSpecial = true;
            specialDuration = 0f;
            currentSpecialAttack = attack;
            animator.reset();
            bossAttack.execute(this, entityManager, animator, getDamageSpecial(attack), getDurationSpecial(attack));
            lastAttack = attack;
            resetAccumulatorForAttack(attack);
        }
    }

    private float getDurationSpecial(BossAttacks attack) {
        float duration = 1f;

        switch (attack) {
            case FORCE_SMASH:
                duration = entityManager.getDifficultyLevel().getForceSmashDuration();
                break;
            case DASH_ATTACK:
                duration = entityManager.getDifficultyLevel().getDashDuration();
                break;
        }

        return duration;
    }

    private int getDamageSpecial(BossAttacks attack) {
        int damage = 0;

        switch (attack) {
            case FORCE_SMASH:
                damage = entityManager.getDifficultyLevel().getForceSmashDamage();
                break;
            case DASH_ATTACK:
                damage = entityManager.getDifficultyLevel().getDashDamage();
                break;
        }

        return damage;
    }

    private void resetAccumulatorForAttack(BossAttacks attack) {
        switch (attack) {
            case FORCE_SMASH:
                forceSmashAccumulator = 0f;
                break;
            case MOVE_FORCE:
                moveForceAccumulator = 0f;
                break;
            case DASH_ATTACK:
                dashAttackAccumulator = 0f;
                break;
        }
    }

    public void resetAll() {
        this.isDashing = false;
        this.setSpeed(getBASE_SPEED());
        this.forceSmashAccumulator = 0f;
        this.moveForceAccumulator = 0f;
        this.dashAttackAccumulator = 0f;
        this.switchLaneAccumulator = 0f;
        this.isExecutingSpecial = false;
    }

    private float getCurrentSpecialDuration() {
        return attackMap.get(currentSpecialAttack).getDuration();
    }

    protected Circle getRange() {
        float forceSmashRadius = (float) (Math.sqrt(entityManager.getCellWidth() * entityManager.getCellWidth() +
            entityManager.getCellHeight() * entityManager.getCellHeight())) * entityManager.getDifficultyLevel().getBossRangeScale();


        return new Circle(getHitboxCenter().x, getHitboxCenter().y, forceSmashRadius);
    }

    private void switchLane() {
        switchLaneAccumulator = 0f;
        float[] validYlanes = {  279.0f, 200.0f, 121.0f };
        float targetY;
        Random random = new Random();
        do {
            targetY = validYlanes[random.nextInt(validYlanes.length)] - getHitbox().height / 2;
        } while (targetY == getHitbox().y);

        pause();

        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(getHitbox().x, targetY);
        moveAction.setDuration(1f);

        RunnableAction onCompleteAction = new RunnableAction();
        onCompleteAction.setRunnable(this::resumeMovement);

        getImage().addAction(Actions.sequence(moveAction, onCompleteAction));
    }

    protected Character findClosestTower() {
        Character closestCharacter = null;
        float closestDistance = Float.MAX_VALUE;

        for (int i = 0; i < entityManager.getCharacters().size; i++) {
           Character character = entityManager.getCharacters().get(i);
            if (character.getType().equalsIgnoreCase("tower")) {
                float distance = (float) Math.sqrt(
                    Math.pow(character.getHitbox().x - this.getHitbox().x, 2) +
                        Math.pow(character.getHitbox().y - this.getHitbox().y, 2)
                );
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestCharacter = character;
                }
            }
        }

        return closestCharacter;
    }

    private boolean isNearLimit(float position, float limit) {
        return Math.abs(position - limit) < 1f;
    }

    protected float getMovingDirectionTargetX() {
        return movingRight ? getRIGHT_LIMIT() : getLEFT_LIMIT();
    }

    public boolean getIsDashing() {
        return isDashing;
    }

    public void finishSpecial() {
        isExecutingSpecial = false;
        if (isDashing) {
            isDashing = false;
        }
        currentSpecialAttack = null; // Limpia el ataque especial actual
    }


    @Override
    public void attack() {
    }

    @Override
    public void checkForAttack(Array<Character> characters) {
    }

}
