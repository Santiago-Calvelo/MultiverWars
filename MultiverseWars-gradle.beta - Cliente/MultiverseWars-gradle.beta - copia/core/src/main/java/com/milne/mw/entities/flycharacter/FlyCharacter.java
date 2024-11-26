    package com.milne.mw.entities.flycharacter;

    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.utils.Array;
    import com.milne.mw.entities.Character;
    import com.milne.mw.entities.EntityManager;


    public abstract class FlyCharacter extends Character {

        public FlyCharacter(Texture texture, int hitboxWidth, int hitboxHeight, Texture walkTexture,
                            Texture attackTexture, float x, float y, int lives, int speed,
                            EntityManager entityManager, String type, float attackCooldown,
                            int damage, int energy, boolean canBeAttacked) {
            super(texture, x, y, hitboxWidth, hitboxHeight, lives, entityManager, speed, walkTexture, walkTexture, attackTexture, attackTexture, type, attackCooldown, damage, energy, canBeAttacked, 0);
        }

        public abstract void makeAttack();

        @Override
        public void attack() {
            this.makeAttack();
        }

        @Override
        public void checkForAttack(Array<Character> characters) {
            tryAttack();
        }
    }
