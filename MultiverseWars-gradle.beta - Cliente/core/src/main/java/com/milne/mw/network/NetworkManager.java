package com.milne.mw.network;

import com.badlogic.gdx.Gdx;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.difficulty.DifficultySelectionScreen;
import com.milne.mw.globals.GameData;
import com.milne.mw.maps.MapScreen;
import com.milne.mw.maps.MapSelectionScreen;

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
    public void createMap(String map, String difficulty) {
        Gdx.app.postRunnable(() -> {
            GameData.game.setScreen(new MapScreen(map, Difficulty.valueOf(difficulty)));
        });

    }

    @Override
    public void spawnentity(int id, float x, float y, String entityImage, int hitboxWidth, int hitboxHeight) {
        if (GameData.game.getScreen() instanceof MapScreen) {
            mapScreen = (MapScreen) GameData.game.getScreen();
            Gdx.app.postRunnable(() -> {
                mapScreen.spawnEntity(id,x,y,entityImage,hitboxWidth,hitboxHeight);
            });
        }
    }
}
