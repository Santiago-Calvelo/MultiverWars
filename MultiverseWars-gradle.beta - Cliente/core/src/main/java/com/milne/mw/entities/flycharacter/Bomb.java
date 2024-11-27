package com.milne.mw.entities.flycharacter;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.milne.mw.entities.EntityManager;

import static com.milne.mw.globals.Global.loadTexture;

public class Bomb {
    private final Image image;
    private boolean isDetonated = false;
    private Circle explosionRange;
    private final int damage;
    private final EntityManager entityManager;
    private final float explosionDisplayTime = 0.5f; // Tiempo para mostrar la explosión

    public Bomb(float x, float y, int damage, EntityManager entityManager, float targetY) {
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
        //entityManager.addBomb(this);
    }

    private void moveDownward(float targetY) {
        // Mueve la bomba hacia abajo hasta la posición objetivo
        image.addAction(Actions.sequence(
            Actions.moveTo(image.getX(), targetY, 0.5f),
            Actions.run(this::detonate) // Detona cuando termina de moverse
        ));
    }

    private void updateExplosionRange() {
        explosionRange.setPosition(image.getX() + image.getWidth() / 2, image.getY() + image.getHeight() / 2);
    }

    public void update(float delta) {
        if (!isDetonated) {
            updateExplosionRange();
        }
    }

    private void detonate() {
        if (isDetonated) return; // Evita múltiples detonaciones

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
            Actions.delay(explosionDisplayTime),
            //Actions.run(() -> entityManager.removeBomb(this)),
            Actions.run(this::dispose)
        ));
    }

    public void dispose() {
        image.clearActions();
        explosionRange = null;
        image.remove();

    }

    public Circle getExplosionRange() {
        return explosionRange;
    }

    public Image getImage() {
        return image;
    }
}
