package com.milne.mw.network;
import java.net.InetAddress;

public class Client {

    private InetAddress ip;
    private int port;
    private int number;

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

}
