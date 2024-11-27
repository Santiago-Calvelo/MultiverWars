package com.milne.mw.player;

public class Player {
    private int initialLives;
    private int initialEnergy;
    private int lives;
    private int energy;
    private final float energyGenerationRate;
    private float energyAccumulator;

    public Player(int initialLives, int initialEnergy, float energyGenerationRate) {
        this.initialLives = initialLives;
        this.initialEnergy = initialEnergy;
        this.lives = initialLives;
        this.energy = initialEnergy;
        this.energyGenerationRate = energyGenerationRate;
        this.energyAccumulator = 0;
    }

    public void update(float delta) {
        energyAccumulator += delta;

        // Generar energía cada segundo
        if (energyAccumulator >= 1f) {
            modifyEnergy((int) (energyGenerationRate * energyAccumulator));
            energyAccumulator = 0;
        }
    }
    public void modifyEnergy(int amount) {
        energy += amount;
        if (energy < 0) {
            energy = 0;
        }

    }

    public void loseLife(int lives) {
        this.lives -= lives;
    }

    public int getLives() {
        return lives;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public void reset() {
        lives = initialLives;
        energy = initialEnergy;
    }
}
