package com.milne.mw.entities.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.renders.BossAnimator;

public class ForceSmashAttack implements BossAttack {
    private final Texture texture; // Textura específica para el ataque
    private float duration; // Duración del ataque

    public ForceSmashAttack(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void execute(BossCharacter boss, EntityManager entityManager, BossAnimator animator, int damage, float duration) {
        this.duration = duration;
        RunnableAction applyDamage = new RunnableAction();
        applyDamage.setRunnable(() -> {
            Circle forceSmashRange = boss.getRange();
            animator.playForceSmashAnimation(texture, boss, forceSmashRange, duration);

            entityManager.getCharacters().forEach(enemy -> {
                Vector2 hitboxCenterEnemy = enemy.getHitboxCenter();
                if (enemy.getType().equalsIgnoreCase("tower") && forceSmashRange.contains(hitboxCenterEnemy)) {
                    enemy.takeDamage(damage);
                }
            });
        });

        boss.getImage().addAction(Actions.sequence(
            Actions.repeat((int) (duration / 0.5f), Actions.sequence(
                applyDamage,
                Actions.delay(0.5f) // Intervalo entre daños
            ))
        ));
    }

    @Override
    public float getDuration() {
        return duration;
    }
}
