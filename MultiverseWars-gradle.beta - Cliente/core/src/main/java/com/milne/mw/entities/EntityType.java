package com.milne.mw.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.milne.mw.entities.meleecharacter.DecreaseBossSpeed;
import com.milne.mw.entities.meleecharacter.MeleeCharacter;
import com.milne.mw.entities.flycharacter.Bloon;
import com.milne.mw.entities.flycharacter.Plane;
import com.milne.mw.entities.meleecharacter.SpecialAttack;
import com.milne.mw.entities.meleecharacter.StunBoss;
import com.milne.mw.entities.rangedcharacter.RangedCharacter;

import static com.milne.mw.globals.Global.loadTexture;

public enum EntityType {
    PEASHOOTER("characters/peashooter/lanzaguisantes-carta.png", "characters/peashooter/lanzaguisantes.png", "characters/peashooter/lanzaguisantes2.png", "characters/peashooter/lanzaguisantes3.png", "characters/peashooter/lanzaguisantes2.png", "characters/projectile/guisante.png",  30, "tower", false, true, 50, 50, 0, 5, 1f, 5, 20, 0) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new RangedCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(), loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),
                loadTexture(getProjectilePath()), x, y, getLives(), getSpeed(), entityManager, getType(),
                getRange(), getAttackCooldown(), getDamage(), getEnergy(), getCanBeAttacked(), getDamageToPlayer()
            );
        }
    },

    COWBOY("characters/cowboy/carta-vaquero.jpg", "characters/cowboy/vaqueroMov1.png", "characters/cowboy/vaqueroMov2.png", "characters/cowboy/vaqueroAtaque1.png", "characters/cowboy/vaqueroMov2.png", "characters/projectile/bala.png",  25, "tower", false, true, 50, 50, 0, 7, 1.5f, 8, 75, 0) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new RangedCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(), loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),
                loadTexture(getProjectilePath()), x, y, getLives(), getSpeed(), entityManager, getType(),
                getRange(), getAttackCooldown(), getDamage(), getEnergy(), getCanBeAttacked(), getDamageToPlayer()
            );
        }
    },

    JIRAIYA("characters/jiraiya/carta-jiraiya.jpg", "characters/jiraiya/jiraiya01.png", "characters/jiraiya/jiraiya02.png", "characters/jiraiya/jiraiya04.png", "characters/jiraiya/jiraiya04.png",  50, "tower", false, false, 50, 50, 0, 1f, 40, 25, new DecreaseBossSpeed(), 0) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new MeleeCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(),
                loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),  loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), x, y, getLives(), getSpeed(), entityManager, getType(), getAttackCooldown(),
                getDamage(), getEnergy(), getCanBeAttacked(), getSpecialAttack(), getDamageToPlayer()
            );
        }
    },

    VOLTORB("characters/voltorb/carta-voltorb.jpg", "characters/voltorb/vol01.png", "characters/voltorb/vol01.png", "characters/voltorb/ZAAP.png",  "characters/voltorb/ZAAP!.png", 50, "tower", false, false, 50, 50, 0, 1f, 40, 25, new StunBoss(), 0) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new MeleeCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(),
                loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),  loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), x, y, getLives(), getSpeed(), entityManager, getType(), getAttackCooldown(),
                getDamage(), getEnergy(), getCanBeAttacked(), getSpecialAttack(), getDamageToPlayer()
            );
        }
    },

    BLOON("characters/bloon/CARTA-GLOBO.jpg", "characters/bloon/globo.png", "characters/bloon/globo1.png", 10, "tower", false, true, 50, 50, 0, 1f, 25, 125) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new Bloon(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(), loadTexture(getWalk1Path()),
                loadTexture(getAttack1Path()), x, y, getLives(), getSpeed(),
                entityManager, getType(), getAttackCooldown(), getDamage(), getEnergy(), getCanBeAttacked()
            );
        }
    },

    SKELETON(null, "characters/skeleton/skeleton.png", "characters/skeleton/skeleton paso.png", "characters/skeleton/skeleton2.png", "characters/skeleton/skeleton3.png",  25, "enemy", true, true, 50, 50, 100, 1f, 10, 5, null, 1) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new MeleeCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(),
                loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),  loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), x, y, getLives(), getSpeed(), entityManager, getType(), getAttackCooldown(),
                getDamage(), getEnergy(), getCanBeAttacked(), getSpecialAttack(), getDamageToPlayer()
            );
        }
    },

    STORMTROOPER(null, "characters/stormtrooper/storm01.png", "characters/stormtrooper/storm02.png", "characters/stormtrooper/storm03.png", "characters/stormtrooper/storm02.png", "characters/projectile/laser.png",  35, "enemy", true, true, 50, 50, 100, 5, 1f, 5, 10, 4) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new RangedCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(), loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),
                loadTexture(getProjectilePath()), x, y, getLives(), getSpeed(), entityManager, getType(),
                getRange(), getAttackCooldown(), getDamage(), getEnergy(), getCanBeAttacked(), getDamageToPlayer()
            );
        }
    },

    BARBARIAN(null, "characters/barbarian/barbaro01.png", "characters/barbarian/barbaro02.png", "characters/barbarian/barbaro03.png", "characters/barbarian/barbaro04.png", 40, "enemy", true, true, 50, 50, 50, 0.5f, 15, 15, null, 20) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new MeleeCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(),
                loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),  loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), x, y, getLives(), getSpeed(), entityManager, getType(), getAttackCooldown(),
                getDamage(), getEnergy(), getCanBeAttacked(), getSpecialAttack(), getDamageToPlayer()
            );
        }
    },

    SOLDIER(null, "characters/soldier/soldado01.png", "characters/soldier/soldado02.png", "characters/soldier/soldado03.png", "characters/soldier/soldado04.png", "characters/projectile/bala.png",  20, "enemy", true, true, 50, 50, 75, 7, 1.5f, 8, 12, 12) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new RangedCharacter(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(), loadTexture(getWalk1Path()),
                loadTexture(getWalk2Path()), loadTexture(getAttack1Path()), loadTexture(getAttack2Path()),
                loadTexture(getProjectilePath()), x, y, getLives(), getSpeed(), entityManager, getType(),
                getRange(), getAttackCooldown(), getDamage(), getEnergy(), getCanBeAttacked(), getDamageToPlayer()
            );
        }
    },

    PLANE("characters/wingmonkey/wingmonkey.png", "characters/wingmonkey/wingmonkey.png", "characters/wingmonkey/wingmonkey1.png", 1, "enemy", false, false, 50, 50, 533, 0.233f, 0, 0) {
        @Override
        public Character getEntity(float x, float y, EntityManager entityManager) {
            return new Plane(
                loadTexture(getTexturePath()), getHitboxWidth(), getHitboxHeight(), loadTexture(getWalk1Path()),
                loadTexture(getAttack1Path()), x, y, getLives(), getSpeed(),
                entityManager, getType(), getAttackCooldown(), getDamage(), getEnergy(), getCanBeAttacked()
            );
        }
    };

    // Atributos
    private Texture cardTexture;
    private String texturePath;
    private String walk1Path, walk2Path;
    private String attack1Path, attack2Path;
    private String projectilePath;
    private int lives, hitboxWidth, hitboxHeight, speed, range, damage, energy;
    private String type;
    private float attackCooldown;
    private boolean canBeSpawned;
    private boolean canBeAttacked;
    private SpecialAttack specialAttack;
    private int damageToPlayer;
    // Constructor
    EntityType(String cardTexturePath, String walk1Path, String walk2Path, String attack1Path, String attack2Path, String projectilePath, int lives, String type, boolean canBeSpawned, boolean canBeAttacked, int hitboxWidth, int hitboxHeight, int speed, int range, float attackCooldown, int damage, int energy, int damageToPlayer) {
        if (cardTexturePath != null) {
            this.cardTexture = new Texture(Gdx.files.internal(cardTexturePath));
        }
        this.texturePath = walk1Path;
        this.walk1Path = walk1Path;
        this.walk2Path = walk2Path;
        this.attack1Path = attack1Path;
        this.attack2Path = attack2Path;
        this.projectilePath = projectilePath;
        this.lives = lives;
        this.type = type;
        this.canBeSpawned = canBeSpawned;
        this.canBeAttacked = canBeAttacked;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.speed = speed;
        this.range = range;
        this.attackCooldown = attackCooldown;
        this.damage = damage;
        this.energy = energy;
        this.damageToPlayer = damageToPlayer;
    }

    // Constructor alternativo para entidades sin proyectil
    EntityType(String cardTexturePath, String walk1Path, String walk2Path, String attack1Path, String attack2Path, int lives, String type, boolean canBeSpawned, boolean canBeAttacked, int hitboxWidth, int hitboxHeight, int speed, float attackCooldown, int damage, int energy, SpecialAttack specialAttack, int damageToPlayer) {
        if (cardTexturePath != null) {
            this.cardTexture = new Texture(Gdx.files.internal(cardTexturePath));
        }
        this.texturePath = walk1Path;
        this.attack1Path = attack1Path;
        this.attack2Path = attack2Path;
        this.walk1Path = walk1Path;
        this.walk2Path = walk2Path;
        this.lives = lives;
        this.type = type;
        this.canBeSpawned = canBeSpawned;
        this.canBeAttacked = canBeAttacked;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.speed = speed;
        this.attackCooldown = attackCooldown;
        this.damage = damage;
        this.energy = energy;
        this.specialAttack = specialAttack;
        this.damageToPlayer = damageToPlayer;
    }

    EntityType(String cardTexturePath, String walkPath, String attackPath, int lives, String type, boolean canBeSpawned, boolean canBeAttacked, int hitboxWidth, int hitboxHeight, int speed, float attackCooldown, int damage, int energy) {
        if (cardTexturePath != null) {
            this.cardTexture = new Texture(Gdx.files.internal(cardTexturePath));
        }
        this.texturePath = walkPath;
        this.attack1Path = attackPath;
        this.walk1Path = walkPath;
        this.lives = lives;
        this.type = type;
        this.canBeSpawned = canBeSpawned;
        this.canBeAttacked = canBeAttacked;
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.speed = speed;
        this.attackCooldown = attackCooldown;
        this.damage = damage;
        this.energy = energy;
    }

    public abstract Character getEntity(float x, float y, EntityManager entityManager);

    public boolean getCanBeSpawned() { return canBeSpawned; }
    public Texture getCardTexture() { return cardTexture; }
    public String getTexturePath() { return texturePath; }
    public String getWalk1Path() { return walk1Path; }
    public String getWalk2Path() { return walk2Path; }
    public String getAttack1Path() { return attack1Path; }
    public String getAttack2Path() { return attack2Path; }
    public String getProjectilePath() { return projectilePath; }
    public String getType() { return type; }
    public int getLives() { return lives; }
    public int getHitboxWidth() { return hitboxWidth; }
    public int getHitboxHeight() { return hitboxHeight; }
    public int getSpeed() { return speed; }
    public int getRange() { return range; }
    public float getAttackCooldown() { return attackCooldown; }
    public int getDamage() { return damage; }
    public int getEnergy() { return energy; }
    public boolean getCanBeAttacked() {
        return canBeAttacked;
    }

    public SpecialAttack getSpecialAttack() {
        return specialAttack;
    }

    public int getDamageToPlayer() {
        return damageToPlayer;
    }
}
