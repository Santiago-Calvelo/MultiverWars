package com.milne.mw.entities.flycharacter;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;

import static com.milne.mw.globals.Global.loadTexture;

public class Bomb {
    private String texture;
    private final Image image;
    private boolean isDetonated = false;
    private Circle explosionRange;
    private int damage;
    private EntityManager entityManager;
    private final float EXPLOSION_DELAY_TIME = 0.5f; // Tiempo para mostrar la explosión
    private int id = 0;

    public Bomb(float x, float y, int damage, EntityManager entityManager, float targetY) {
        this.texture = "characters/projectile/bomba.png";
        this.image = new Image(loadTexture("characters/projectile/bomba.png"));
        this.image.setSize(30, 30); // Tamaño inicial de la bomba
        this.image.setPosition(x, y);
        this.damage = damage;
        this.entityManager = entityManager;

        // Define el rango de la explosión basado en las dimensiones de las celdas
        float explosionRadius = (float) Math.sqrt(entityManager.getCellWidth() * entityManager.getCellWidth() +
            entityManager.getCellHeight() * entityManager.getCellHeight());
        this.explosionRange = new Circle(x + image.getWidth() / 2, y + image.getHeight() / 2, explosionRadius);

        moveDownward(targetY);
        entityManager.addBomb(this);
    }

    private void moveDownward(float targetY) {
        // Mueve la bomba hacia abajo hasta la posición objetivo
        image.addAction(Actions.sequence(
            Actions.moveTo(image.getX(), targetY, 0.5f),
            Actions.run(this::detonate) // Detona cuando termina de moverse
        ));
    }

    private void updateExplosionRange() {
        if (Global.multiplayer) {
            NetworkData.serverThread.sendMessageToAll("moveentity!" + id + "!" + image.getX() + "!" + image.getY());
        }
        explosionRange.setPosition(image.getX() + image.getWidth() / 2, image.getY() + image.getHeight() / 2);
    }

    public void update(float delta) {
        if (!isDetonated) {
            updateExplosionRange();
        }
    }

    private void detonate() {
        // Cambia la textura a la de la explosión
        image.setDrawable(new TextureRegionDrawable(loadTexture("characters/projectile/explosion.png")));
        image.setSize(explosionRange.radius * 2, explosionRange.radius * 2);
        image.setPosition(explosionRange.x - explosionRange.radius, explosionRange.y - explosionRange.radius);

        // Aplica daño a los personajes dentro del rango de explosión
        entityManager.getCharacters().forEach(character -> {
            if (!character.getType().equalsIgnoreCase("tower")) {
                if (explosionRange.overlaps(new Circle(
                    character.getHitboxCenter().x,
                    character.getHitboxCenter().y,
                    0))) {
                    character.takeDamage(damage);
                }
            }
        });

        // Marca como detonada y programa la eliminación
        isDetonated = true;
        image.addAction(Actions.sequence(
            Actions.delay(EXPLOSION_DELAY_TIME),
            Actions.run(() -> entityManager.removeBomb(this)),
            Actions.run(this::dispose)
        ));
    }

    public Circle getExplosionRange() {
        return explosionRange;
    }

    public Image getImage() {
        return image;
    }

    public String getTexture() {
        return texture;
    }

    public void dispose() {
        image.clearActions();
        explosionRange = null;
        image.remove();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
