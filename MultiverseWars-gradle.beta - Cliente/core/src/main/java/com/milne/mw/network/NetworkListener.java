package com.milne.mw.network;

public interface NetworkListener {

    void startGame();
    void mapSelected(String map);
    void createMap(String map);
    void addEntity(int id, float x, float y, String entityImage, float hitboxWidth, float hitboxHeight);
    void addCardsToPanel(String cardImage, float x, float y, int width, int height, String entityType);
    void updatePlayerState(int lives, int energy);
    void moveEntity(int id, float currentX, float currentY);
    void removeEntity(int id);
    void updateRound(int currentRound, int maxRound);
    void drawBossAttack(String idAttack, String texture, float x, float y, float width, float height);
    void updateBossAttack(String idAttack, float x, float y);
    void bossAttackRemove(String idAttack);
    void gameOver();
    void win();
}
