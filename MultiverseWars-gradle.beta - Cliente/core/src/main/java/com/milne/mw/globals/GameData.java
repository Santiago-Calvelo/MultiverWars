package com.milne.mw.globals;

import com.badlogic.gdx.Game;
import com.milne.mw.network.NetworkListener;
import com.milne.mw.network.NetworkManager;

public abstract class GameData{

    public static Game game;
    public static int clientNumber = -1;
    public static NetworkListener networkListener = new NetworkManager();

}
