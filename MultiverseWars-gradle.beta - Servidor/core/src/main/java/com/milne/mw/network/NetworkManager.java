package com.milne.mw.network;

import com.badlogic.gdx.Gdx;
import com.milne.mw.difficulty.Difficulty;
import com.milne.mw.globals.GameData;
import com.milne.mw.maps.MapScreen;

public class NetworkManager implements NetworkListener {

    @Override
    public void createMap(String map, String difficulty) {
        Gdx.app.postRunnable(() -> {
            GameData.game.setScreen(new MapScreen(map, Difficulty.valueOf(difficulty)));
        });
    }

    @Override
    public void endGame() {

    }
}
