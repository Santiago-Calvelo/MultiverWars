package com.milne.mw.network;

public interface NetworkListener {

    void startGame();
    void mapSelected(String map);
    void createMap(String map, String difficulty);
    void spawnentity(int id, float x, float y, String entityImage, float hitboxWidth, float hitboxHeight);
    void addCardsToPanel(String cardImage, float x, float y, int width, int height, String entityType);
}
