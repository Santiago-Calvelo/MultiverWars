package com.milne.mw.renders;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.entities.boss.BossCharacter;

public class BossAnimator {
    private final Stage stage;
    private Image forceSmashImage;

    public BossAnimator(Stage stage) {
        this.stage = stage;
    }

    public void reset() {
        // Limpia las referencias y elimina actores residuales
        if (forceSmashImage != null) {
            forceSmashImage.remove();
            forceSmashImage = null;
        }
    }

    public void playForceSmashAnimation(Texture texture, BossCharacter boss, Circle forceSmashRange, float duration) {
        reset(); // Asegura que cualquier animación anterior está limpia

        if (forceSmashImage == null) {
            forceSmashImage = new Image(texture);
            forceSmashImage.setSize(forceSmashRange.radius * 2, forceSmashRange.radius * 2);
            forceSmashImage.setPosition(forceSmashRange.x - forceSmashRange.radius, forceSmashRange.y - forceSmashRange.radius);
            stage.addActor(forceSmashImage);
            forceSmashImage.setZIndex(1);
        }

        RunnableAction updatePosition = new RunnableAction();
        updatePosition.setRunnable(() -> {
            forceSmashImage.setPosition(
                boss.getImage().getX() + boss.getImage().getWidth() / 2 - forceSmashRange.radius,
                boss.getImage().getY() + boss.getImage().getHeight() / 2 - forceSmashRange.radius
            );
        });

        forceSmashImage.addAction(
            Actions.sequence(
                Actions.repeat((int) (duration / 0.016f), updatePosition),
                Actions.run(() -> {
                    forceSmashImage.remove();
                    forceSmashImage = null;
                })
            )
        );
    }
}
