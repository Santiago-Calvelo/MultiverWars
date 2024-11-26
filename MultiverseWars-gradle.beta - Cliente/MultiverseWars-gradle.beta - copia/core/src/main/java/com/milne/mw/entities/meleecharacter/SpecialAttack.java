package com.milne.mw.entities.meleecharacter;
import com.milne.mw.entities.Character;

public interface SpecialAttack {
    void execute(Character character, Character targetEnemy);
}
