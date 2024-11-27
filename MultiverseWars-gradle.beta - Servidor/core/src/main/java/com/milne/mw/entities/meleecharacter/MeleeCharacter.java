package com.milne.mw.entities.meleecharacter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.utils.Array;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.entities.boss.BossCharacter;

public class MeleeCharacter extends com.milne.mw.entities.Character {
    private Character targetEnemy;
    private SpecialAttack specialAttack;

    public MeleeCharacter(String texture, int hitboxWidth, int hitboxHeight, String attack1Texture,
                          String attack2Texture, Texture walk1Texture, Texture walk2Texture,
                          float x, float y, int lives, int speed, EntityManager entityManager,
                          String type, float attackCooldown, int damage, int energy, boolean canBeAttacked, SpecialAttack specialAttack, int damageToPlayer) {
        super(texture, x, y, hitboxWidth, hitboxHeight, lives, entityManager, speed, walk1Texture,
            walk2Texture, attack1Texture, attack2Texture, type, attackCooldown, damage, energy, canBeAttacked, damageToPlayer);
        this.specialAttack = specialAttack;
    }

    // Implementación del ataque cuerpo a cuerpo
    @Override
    public void attack() {

        if (targetEnemy != null && targetEnemy.getCanBeAttacked()) {
            this.isAttacking = true;
            if (specialAttack != null) {
                RunnableAction attackAndRemoveCharacter = new RunnableAction();
                attackAndRemoveCharacter.setRunnable(() -> {
                    targetEnemy.takeDamage(this.getDamage());
                    this.removeCharacter();
                    this.isAttacking = false;
                });
                this.specialAttack.execute(this,targetEnemy);
                this.getImage().addAction(Actions.sequence(
                    Actions.delay(this.getAttackCooldown()),
                    attackAndRemoveCharacter
                ));

            } else {
                targetEnemy.takeDamage(getDamage());
                targetEnemy = null;
                this.isAttacking = false;
            }
        }


    }


    @Override
    public void checkForAttack(Array<com.milne.mw.entities.Character> characters) {
        int i = 0;
        boolean collisionDetected = false;

        // Usamos un do-while para recorrer los personajes
        do {
            Character character = characters.get(i);
            if (this != character && this.getHitbox().overlaps(character.getHitbox()) && !character.getType().equalsIgnoreCase(this.getType())) {
                collisionDetected = true;
                targetEnemy = character;
                if (!this.getCanBeAttacked() && !(targetEnemy instanceof BossCharacter)) {
                    targetEnemy.pause();
                }
                if (this.getSpeed() != 0) {
                    stopMovementAndAttack();
                } else {
                    tryAttack();
                }
            }
            i++;
        } while (i < characters.size && !collisionDetected);

        // Si no hay colisiones, continuar moviéndose
        if (!collisionDetected) {
            resumeMovement();
        }
    }
}
