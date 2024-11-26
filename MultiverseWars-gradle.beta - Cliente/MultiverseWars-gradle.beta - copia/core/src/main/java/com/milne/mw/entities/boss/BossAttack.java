package com.milne.mw.entities.boss;

import com.milne.mw.entities.EntityManager;
import com.milne.mw.renders.BossAnimator;

public interface BossAttack {
    void execute(BossCharacter boss, EntityManager entityManager, BossAnimator animator, int damage, float duration);
    float getDuration();
}
