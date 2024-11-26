package com.milne.mw.network;

public interface NetworkListener {


    void createMap(String map, String difficulty);
    void endGame();
    void spawnTower(String part, float x, float y, float cardWidth, float cardHeight);
}
