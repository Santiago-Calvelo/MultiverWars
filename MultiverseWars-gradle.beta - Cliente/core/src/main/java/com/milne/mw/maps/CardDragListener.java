package com.milne.mw.maps;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.entities.EntityType;
import com.milne.mw.globals.NetworkData;

public class CardDragListener extends InputListener {
    private final String entityType;
    private final Image cardImage;
    private float originalX;
    private float originalY;
    private boolean isDragging = false;

    public CardDragListener(String entityType, Image cardImage) {
        this.entityType = entityType;
        this.cardImage = cardImage;

        // Guardamos la posición original de la carta
        this.originalX = cardImage.getX();
        this.originalY = cardImage.getY();
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (button == Buttons.LEFT) {
            isDragging = true;
            return true;
        }
        return false;
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        if (isDragging) {
            cardImage.moveBy(x - cardImage.getWidth() / 2, y - cardImage.getHeight() / 2);
        }
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (isDragging) {
            float cardX = cardImage.getX();
            float cardY = cardImage.getY();
           // entityManager.handleEntityPlacement(entityType, cardX, cardY, cardImage.getWidth(), cardImage.getHeight());
            NetworkData.clientThread.sendMessage("spawntower!" + entityType + "!" + cardX + "!" + cardY + "!" + cardImage.getWidth() + "!" + cardImage.getHeight());
            cardImage.setPosition(originalX, originalY);
            isDragging = false;
        }
    }
}
