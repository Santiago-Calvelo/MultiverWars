package com.milne.mw.entities;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.milne.mw.entities.EntityManager;

public class SellTowerListener extends InputListener {
    private final EntityManager entityManager;

    public SellTowerListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == Buttons.RIGHT) {
            System.out.println("Clic derecho detectado.");
            float stageX = event.getStageX();
            float stageY = event.getStageY();
            entityManager.handleRightClick(stageX, stageY);
            return true;
        }
        return false;
    }
}
