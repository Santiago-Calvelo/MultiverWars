package com.milne.mw.renders;


import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.milne.mw.entities.Character;
import com.milne.mw.globals.Global;
import com.milne.mw.globals.NetworkData;

public class AttackAnimation {
    private Character character;
    private float duration;
    private float animationTime;

    private TextureRegionDrawable attack1Drawable;
    private TextureRegionDrawable attack2Drawable;
    private boolean secondTextureApplied; // Evita cambios redundantes
    private static final float UPDATE_INTERVAL = 0.016f; // Cada 16 ms (60 FPS)
    private float accumulatedDelta = 0;

    public AttackAnimation(Character character, float duration) {
        this.character = character;
        this.duration = duration;
        this.animationTime = 0;
        this.secondTextureApplied = false;

        // Predefine los drawables
        this.attack1Drawable = new TextureRegionDrawable(Global.loadTexture(character.getAttack1Texture()));
        this.attack2Drawable = new TextureRegionDrawable(Global.loadTexture(character.getAttack2Texture()));

        // Inicia la animación con la primera textura de ataque
        character.getImage().setDrawable(attack1Drawable);
       /* if (Global.multiplayer) {
            NetworkData.serverThread.sendMessageToAll("animatetextureentity!" + character.getId() + "!" + character.getAttack1Texture());
        }*/
    }

    public boolean update(float delta) {
        animationTime += delta;

        // Cambia a la segunda textura de ataque en la mitad de la duración del ataque
        if (!secondTextureApplied && animationTime >= (duration / 2)) {
            character.getImage().setDrawable(attack2Drawable);
            secondTextureApplied = true; // Evita cambios redundantes
           /* if (Global.multiplayer) {
                NetworkData.serverThread.sendMessageToAll("animatetextureentity!" + character.getId() + "!" + character.getAttack2Texture());
            }*/
        }

        // Devuelve true si la animación ha terminado
        return animationTime >= duration;
    }
}
