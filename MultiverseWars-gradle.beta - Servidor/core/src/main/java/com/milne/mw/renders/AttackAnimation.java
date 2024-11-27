package com.milne.mw.renders;


import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.milne.mw.entities.Character;

public class AttackAnimation {
    private Character character;
    private float duration;
    private float animationTime;

    public AttackAnimation(Character character, float duration) {
        this.character = character;
        this.duration = duration;
        this.animationTime = 0;

        // Inicia la animación con la primera textura de ataque
        character.getImage().setDrawable(new TextureRegionDrawable(character.getAttack1Texture()));
    }

    public boolean update(float delta) {
        animationTime += delta;

        // Cambia a la segunda textura de ataque en la mitad de la duración del ataque
        if (animationTime >= (duration / 2) - 0.05f && animationTime < duration) {
            character.getImage().setDrawable(new TextureRegionDrawable(character.getAttack2Texture()));
        }

        // Devuelve true si la animación ha terminado
        return animationTime >= duration;
    }
}
