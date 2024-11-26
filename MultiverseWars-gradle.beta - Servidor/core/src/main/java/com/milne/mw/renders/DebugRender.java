package com.milne.mw.renders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.milne.mw.entities.Character;
import com.milne.mw.entities.EntityManager;
import com.milne.mw.entities.flycharacter.Bomb;
import com.milne.mw.menu.PauseMenu;

public class DebugRender {
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Stage stage;

    public DebugRender(Stage stage) {
        this.stage = stage;
    }

    public void drawPlacementZones(EntityManager entityManager, PauseMenu pauseMenu) {
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);

        shapeRenderer.circle(pauseMenu.getPauseButtonHitbox().x, pauseMenu.getPauseButtonHitbox().y, pauseMenu.getPauseButtonHitbox().radius);
        for (Rectangle hitbox : entityManager.getPlacementHitboxes()) {
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }

        shapeRenderer.end();
    }

    public void drawHitboxes(EntityManager entityManager) {
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (Character character : entityManager.getCharacters()) {
            Rectangle hitbox = character.getHitbox();
            shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }

        shapeRenderer.end();
    }

    public void drawBombRanges(EntityManager entityManager) {
        shapeRenderer.setProjectionMatrix(stage.getViewport().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.YELLOW);

        for (Bomb bomb : entityManager.getBombs()) { // Asegúrate de que `EntityManager` exponga las bombas.
            Circle explosionRange = bomb.getExplosionRange();
            if (explosionRange != null) {
                shapeRenderer.circle(explosionRange.x, explosionRange.y, explosionRange.radius);
            }
        }

        shapeRenderer.end();
    }
}
