package com.milne.mw.entities.boss;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.renders.BossAnimator;
import com.milne.mw.entities.Character;

import java.util.Random;

public class MoveForceAttack implements BossAttack {
    private float duration; // Duración del ataque

    @Override
    public void execute(BossCharacter boss, EntityManager entityManager, BossAnimator animator, int damage, float duration) {
        this.duration = duration;
        Character closestCharacter = boss.findClosestTower();

        if (closestCharacter != null) {
            moveTowerToRandomPosition(closestCharacter, entityManager, boss);
        } else {
            boss.finishSpecial();
        }
    }

    private void moveTowerToRandomPosition(Character tower, EntityManager entityManager, BossCharacter boss) {
        Random random = new Random();
        Rectangle targetHitbox;
        int newIndex;

        do {
            newIndex = random.nextInt(entityManager.getPlacementHitboxes().size());
            targetHitbox = entityManager.getPlacementHitboxes().get(newIndex);
        } while (entityManager.getPositionMap().containsKey(newIndex));

        float targetX = targetHitbox.x + targetHitbox.width / 2 - tower.getHitbox().getWidth() / 2;
        float targetY = targetHitbox.y + targetHitbox.height / 2 - tower.getHitbox().getHeight() / 2;

        entityManager.releasePosition(tower);
        tower.pause();

        MoveToAction moveAction = new MoveToAction();
        moveAction.setPosition(targetX, targetY);
        moveAction.setDuration(duration);

        RunnableAction onCompleteAction = new RunnableAction();
        int finalNewIndex = newIndex;
        onCompleteAction.setRunnable(() -> {
            entityManager.getPositionMap().put(finalNewIndex, tower);
            tower.resumeMovement();
        });

        tower.getImage().addAction(Actions.sequence(moveAction, onCompleteAction));
    }

    @Override
    public float getDuration() {
        return duration;
    }
}
