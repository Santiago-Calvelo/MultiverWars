package com.milne.mw.network;
import com.milne.mw.player.Player;
import java.net.InetAddress;

public class Client {

    private InetAddress ip;
    private int port;
    private int number;
    private Player player;

    public Client(InetAddress ip, int port, int number) {
        this.ip = ip;
        this.port = port;
        this.number = number;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getNumber() {
        return number;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
