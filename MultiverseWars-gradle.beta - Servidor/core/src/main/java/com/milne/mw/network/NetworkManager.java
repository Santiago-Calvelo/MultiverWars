package com.milne.mw.network;

import com.badlogic.gdx.Gdx;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.maps.MapScreen;
import com.milne.mw.screens.TwoPlayerModeScreen;

public class NetworkManager implements NetworkListener {
    private MapScreen mapScreen;
    @Override
    public void createMap(String map, String difficulty) {
        Gdx.app.postRunnable(() -> {
            GameData.game.setScreen(new MapScreen(map, Difficulty.valueOf(difficulty)));

            if (GameData.game.getScreen() instanceof MapScreen) {
                this.mapScreen = (MapScreen) GameData.game.getScreen();
            }
        });
    }

    @Override
    public void endGame() {
        Gdx.app.postRunnable(() -> {
            NetworkData.serverThread.end();
            NetworkData.serverThread = null;
            this.mapScreen = null;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            GameData.game.setScreen(new TwoPlayerModeScreen());
        });
    }

    @Override
    public void spawnTower(String entityType, float x, float y, float cardWidth, float cardHeight, int numberPlayer) {
        Gdx.app.postRunnable(() -> {
            this.mapScreen.summonTower(entityType,x,y,cardWidth,cardHeight,numberPlayer);
        });
    }
}
