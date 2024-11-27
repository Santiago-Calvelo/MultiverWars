package com.milne.mw.difficulty;

public enum Difficulty {
    EASY(0.01f,0,6f, 1000, 750, 1f, 1000, 1f, 2, 3f, 1, 15, 1f, 15f, 10f, 10f, 0.025f),
    MEDIUM(0.03f,15,4f, 133, 50, 0.5f, 1500, 2f, 3, 4f, 2, 10, 1.5f, 12f, 8f, 6f, 0.035f),
    HARD(0.05f,20,2f, 75, 25, 0.25f, 2000, 2.5f, 7, 2f, 4, 18, 1f, 10f, 8f, 5f, 0.050f);

    private final float scalingFactor;
    private final int maxRound;
    private final float intervalSpawn;
    private final int initalLives;
    private final int initialEnergy;
    private final float energyGenerationRate;
    private final int bossLives;
    private final float bossRangeScale;
    private final int forceSmashDamage;
    private final float forceSmashDuration;
    private final int dashDamage;
    private final int dashXLR8;
    private final float dashDuration;
    private final float moveForceCooldown, forceSmashCooldown, dashCooldown, bossScalingFactor;


    Difficulty(float scalingFactor, int maxRound, float intervalSpawn, int initialLives, int initialEnergy, float energyGenerationRate, int bossLives, float bossRangeScale, int forceSmashDamage, float forceSmashDuration, int dashDamage, int dashXLR8, float dashDuration, float moveForceCooldown, float forceSmashCooldown, float dashCooldown, float bossScalingFactor) {
        this.scalingFactor = scalingFactor;
        this.maxRound = maxRound;
        this.intervalSpawn = intervalSpawn;
        this.initalLives = initialLives;
        this.initialEnergy = initialEnergy;
        this.energyGenerationRate = energyGenerationRate;
        this.bossLives = bossLives;
        this.bossRangeScale = bossRangeScale;
        this.forceSmashDamage = forceSmashDamage;
        this.forceSmashDuration = forceSmashDuration;
        this.dashDamage = dashDamage;
        this.dashXLR8 = dashXLR8;
        this.dashDuration = dashDuration;
        this.moveForceCooldown = moveForceCooldown;
        this.forceSmashCooldown = forceSmashCooldown;
        this.dashCooldown = dashCooldown;
        this.bossScalingFactor = bossScalingFactor;
    }

    public float getScalingFactor() {
        return scalingFactor;
    }

    public float getBossScalingFactor() {
        return bossScalingFactor;
    }

    public float getIntervalSpawn() {
        return intervalSpawn;
    }

    public int getMaxRound() {
        return maxRound;
    }

    public int getInitalLives() {
        return initalLives;
    }

    public int getInitialEnergy() {
        return initialEnergy;
    }

    public float getEnergyGenerationRate() {
        return energyGenerationRate;
    }

    public int getBossLives() {
        return bossLives;
    }

    public float getBossRangeScale() {
        return bossRangeScale;
    }

    public int getForceSmashDamage() {
        return forceSmashDamage;
    }

    public float getForceSmashDuration() {
        return forceSmashDuration;
    }

    public int getDashDamage() {
        return dashDamage;
    }

    public float getDashDuration() {
        return dashDuration;
    }

    public float getMoveForceCooldown() {
        return moveForceCooldown;
    }

    public float getForceSmashCooldown() {
        return forceSmashCooldown;
    }

    public float getDashCooldown() {
        return dashCooldown;
    }

    public int getDashXLR8() {
        return dashXLR8;
    }
}
