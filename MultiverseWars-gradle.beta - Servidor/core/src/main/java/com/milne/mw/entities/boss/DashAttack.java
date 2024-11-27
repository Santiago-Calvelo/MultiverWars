package com.milne.mw.entities.boss;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.renders.BossAnimator;

public class DashAttack implements BossAttack{
    private float duration;


    @Override
    public void execute(BossCharacter boss, EntityManager entityManager, BossAnimator animator, int damage, float duration) {
        this.duration = duration;

        increaseSpeed(boss,entityManager.getDifficultyLevel().getDashXLR8());


        RunnableAction applyDamage = new RunnableAction();
        applyDamage.setRunnable(() -> {
            for (int i = 0; i < entityManager.getCharacters().size; i++) {
                Character character = entityManager.getCharacters().get(i);
                if (boss.getHitbox().overlaps(character.getHitbox()) && character.getCanBeAttacked() && character.getType().equalsIgnoreCase("tower")) {
                    character.takeDamage(damage);
                }
            }
        });

        RunnableAction resetBaseSpeed = new RunnableAction();
        resetBaseSpeed.setRunnable(() -> {
            boss.setSpeed(boss.getBaseSpeed());
            boss.pause();
            boss.resumeMovement();
        });

        boss.getImage().addAction(Actions.sequence(
            Actions.delay(duration),
            resetBaseSpeed
        ));

        boss.getImage().addAction(Actions.sequence(
            Actions.repeat((int) (duration / 0.5f), Actions.sequence(
                applyDamage,
                Actions.delay(0.5f) // Intervalo entre daños
            ))
        ));
    }

    private void increaseSpeed(BossCharacter boss, int multiplier) {
        boss.setSpeed(boss.getSpeed()*multiplier);
        boss.pause();
        boss.resumeMovement();
    }

    @Override
    public float getDuration() {
        return duration;
    }
}
