package com.milne.mw.network;

import com.badlogic.gdx.Gdx;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.difficulty.DifficultySelectionScreen;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.maps.MapScreen;
import com.milne.mw.maps.MapSelectionScreen;
import com.milne.mw.screens.MainMenuScreen;

public class NetworkManager implements NetworkListener {
    private MapScreen mapScreen;
    @Override
    public void startGame() {
        Gdx.app.postRunnable(() -> {
            GameData.game.setScreen(new MapSelectionScreen());
        });
    }

    @Override
    public void mapSelected(String map) {
        Gdx.app.postRunnable(() -> {
            GameData.game.setScreen(new DifficultySelectionScreen(map));
        });
    }

    @Override
    public void createMap(String map) {
        Gdx.app.postRunnable(() -> {
            GameData.game.setScreen(new MapScreen(map));

            if (GameData.game.getScreen() instanceof MapScreen) {
                this.mapScreen = (MapScreen) GameData.game.getScreen();
            }
        });

    }

    @Override
    public void addEntity(int id, float x, float y, String entityImage, float hitboxWidth, float hitboxHeight) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.addEntity(id,x,y,entityImage,hitboxWidth,hitboxHeight);
        });
    }

    @Override
    public void addCardsToPanel(String cardImage, float x, float y, int width, int height, String entityType) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.addCardsToPanel(cardImage,x,y,width,height,entityType);
        });
    }

    @Override
    public void updatePlayerState(int lives, int energy) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.getRenderManager().updatePlayerLabels(lives,energy);
        });
    }

    @Override
    public void moveEntity(int id, float currentX, float currentY) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.moveEntity(id,currentX,currentY);
        });
    }

    @Override
    public void animateTextureEntity(int id, String walkTexture) {
        Gdx.app.postRunnable(() ->  {
            this.mapScreen.animateTextureEntity(id,walkTexture);
        });
    }

    @Override
    public void removeEntity(int id) {
        Gdx.app.postRunnable(() ->  {
            this.mapScreen.removeEntity(id);
        });
    }

    @Override
    public void updateRound(int currentRound, int maxRound) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.getRenderManager().updateRoundLabels(currentRound,maxRound);
        });
    }

    @Override
    public void drawBossAttack(String idAttack, String texture, float x, float y, float width, float height) {
        Gdx.app.postRunnable(() ->  {
            this.mapScreen.getRenderManager().drawBossAttack(idAttack,texture,x,y,width,height);
        });
    }

    @Override
    public void updateBossAttack(String idAttack, float x, float y) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.getRenderManager().updateBossAttack(idAttack,x,y);
        });
    }

    @Override
    public void bossAttackRemove(String idAttack) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.getRenderManager().removeBossAttack(idAttack);
        });
    }

    @Override
    public void gameOver() {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.createGameOverMenu();
        });
    }

    @Override
    public void win() {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.createVictoryMenu();
        });
    }

}
