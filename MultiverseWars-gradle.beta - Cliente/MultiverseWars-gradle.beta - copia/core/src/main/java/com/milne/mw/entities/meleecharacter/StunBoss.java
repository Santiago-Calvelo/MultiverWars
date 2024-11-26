package com.milne.mw.entities.meleecharacter;

import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.boss.BossCharacter;

public class StunBoss implements SpecialAttack{
    private final float STUN_PROB = 0.1f;

    @Override
    public void execute(Character character, Character targetEnemy) {
        if (targetEnemy instanceof BossCharacter) {
            Random r = new Random();
            if (r.nextFloat() < STUN_PROB) {
                BossCharacter boss = (BossCharacter) targetEnemy;

                boss.resetAll();
                boss.pause();

                RunnableAction removeStun = new RunnableAction();
                removeStun.setRunnable(boss::resumeMovement);

                // Agrega un retraso para simular la duración del aturdimiento
                boss.getImage().addAction(Actions.sequence(
                    Actions.delay(2f), // Duración del aturdimiento
                    removeStun
                ));
            }
        }
    }
}
