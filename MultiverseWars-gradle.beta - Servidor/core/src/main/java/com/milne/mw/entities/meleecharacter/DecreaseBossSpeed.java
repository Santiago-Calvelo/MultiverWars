package com.milne.mw.entities.meleecharacter;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.boss.BossCharacter;

public class DecreaseBossSpeed implements SpecialAttack {
    @Override
    public void execute(Character character, Character targetEnemy) {
        if (targetEnemy instanceof BossCharacter) {
            BossCharacter boss = (BossCharacter) targetEnemy;

            boss.resetAll();
            boss.setSpeed((int) (boss.getSpeed() * 0.7f));
            boss.startMovement();
            RunnableAction resetCurrentSpeed = new RunnableAction();
            resetCurrentSpeed.setRunnable(() -> {
                boss.setSpeed(boss.getBASE_SPEED());
                character.removeCharacter();
            });

            boss.getImage().addAction(Actions.sequence(
                Actions.delay(0.5f),
                resetCurrentSpeed
            ));
        }
    }
}
