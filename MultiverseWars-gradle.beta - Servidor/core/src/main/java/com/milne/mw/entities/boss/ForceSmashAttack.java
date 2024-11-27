package com.milne.mw.entities.boss;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.renders.BossAnimator;

public class ForceSmashAttack implements BossAttack {
    private String texture; // Textura específica para el ataque
    private float duration; // Duración del ataque

    public ForceSmashAttack(String texture) {
        this.texture = texture;
    }

    @Override
    public void execute(BossCharacter boss, EntityManager entityManager, BossAnimator animator, int damage, float duration) {
        this.duration = duration;
        boss.getImage().addAction(Actions.sequence(
            Actions.forever(Actions.run(() -> {
                // Obtener el rango del ataque
                Circle forceSmashRange = boss.getRange();

                // Llamar al animador para actualizar la animación
                animator.playForceSmashAnimation(texture, boss, forceSmashRange, duration);

                // Aplicar daño a los enemigos dentro del rango
                entityManager.getCharacters().forEach(enemy -> {
                    Vector2 hitboxCenterEnemy = enemy.getHitboxCenter();
                    if (enemy.getType().equalsIgnoreCase("tower") && forceSmashRange.contains(hitboxCenterEnemy)) {
                        enemy.takeDamage(damage);
                    }
                });
            })),
            Actions.delay(duration), // Ejecutar esta lógica durante la duración definida
            Actions.run(() -> {
                // Limpieza al finalizar el ataque, si es necesario
                System.out.println("Force Smash Attack terminado.");
            })
        ));
    }

    @Override
    public float getDuration() {
        return duration;
    }
}
