package com.milne.mw.renders;


import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.entities.boss.BossCharacter;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;

public class BossAnimator {
    private Stage stage;
    private Image forceSmashImage;

    public BossAnimator(Stage stage) {
        this.stage = stage;
    }

    public void reset() {
        // Limpia las referencias y elimina actores residuales
        if (forceSmashImage != null) {
            if (Global.multiplayer) {
                NetworkData.serverThread.sendMessageToAll("bossattackremove!" + "forcesmash");
            }
            forceSmashImage.remove();
            forceSmashImage = null;
        }
    }

    public void playForceSmashAnimation(String texture, BossCharacter boss, Circle forceSmashRange, float duration) {
        if (forceSmashImage == null) {
            forceSmashImage = new Image(Global.loadTexture(texture));
            forceSmashImage.setSize(forceSmashRange.radius * 2, forceSmashRange.radius * 2);
            forceSmashImage.setPosition(forceSmashRange.x - forceSmashRange.radius, forceSmashRange.y - forceSmashRange.radius);
            stage.addActor(forceSmashImage);
            forceSmashImage.setZIndex(1);
            if (Global.multiplayer) {
                NetworkData.serverThread.sendMessageToAll("bossattack!" + "forcesmash" + "!" + texture + "!" + forceSmashImage.getX() + "!" + forceSmashImage.getY() + "!" + forceSmashImage.getWidth() + "!" + forceSmashImage.getHeight());
            }
        } else {
            if (forceSmashImage != null) {
                forceSmashImage.setPosition(
                    boss.getImage().getX() + boss.getImage().getWidth() / 2 - forceSmashRange.radius,
                    boss.getImage().getY() + boss.getImage().getHeight() / 2 - forceSmashRange.radius
                );

                if (Global.multiplayer) {
                    NetworkData.serverThread.sendMessageToAll("bossattackupdate!" + "forcesmash" + "!" + forceSmashImage.getX() + "!" + forceSmashImage.getY());
                }
            }

        }

        /*RunnableAction updatePosition = new RunnableAction();
        updatePosition.setRunnable(() -> {
            forceSmashImage.setPosition(
                boss.getImage().getX() + boss.getImage().getWidth() / 2 - forceSmashRange.radius,
                boss.getImage().getY() + boss.getImage().getHeight() / 2 - forceSmashRange.radius
            );
        });*/

        forceSmashImage.addAction(
            Actions.sequence(
                Actions.delay(duration),
                Actions.run(() -> {
                    forceSmashImage.remove();
                    if (Global.multiplayer) {
                        NetworkData.serverThread.sendMessageToAll("bossattackremove!" + "forcesmash");
                    }
                    forceSmashImage = null;
                })
            )
        );
    }
}
