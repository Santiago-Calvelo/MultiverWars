package com.milne.mw;

import com.badlogic.gdx.Game;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.NetworkData;
import com.milne.mw.screens.SplashScreen;

public class MultiverseWars extends Game {

    @Override
    public void create() {
        MusicManager.playMusic("bye bye.mp3");
        GameData.game = this;
        setScreen(new SplashScreen());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if (NetworkData.clientThread != null) {
            NetworkData.clientThread.sendMessage("disconnect!" + GameData.clientNumber);
            NetworkData.clientThread.end();
            NetworkData.clientThread = null;
        }
        MusicManager.stopMusic();
    }
}
