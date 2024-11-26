package com.milne.mw;

import com.badlogic.gdx.Game;
import com.milne.mw.screens.SplashScreen;

public class MultiverseWars extends Game {

    @Override
    public void create() {
        MusicManager.playMusic("bye bye.mp3");
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        MusicManager.stopMusic();
    }
}
