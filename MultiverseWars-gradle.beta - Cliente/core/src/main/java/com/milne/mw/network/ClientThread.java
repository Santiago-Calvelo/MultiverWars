package com.milne.mw.network;

import com.milne.mw.globals.GameData;

import java.io.IOException;
import java.net.*;

public class ClientThread extends Thread {

    private InetAddress serverIp;
    private final int SERVER_PORT = 9999;
    private DatagramSocket socket;
    private boolean end = false;
    private String specialChar = "!";

    public ClientThread() {

        try {
            serverIp = InetAddress.getByName("255.255.255.255");
            socket = new DatagramSocket();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(!end){
            DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
            try {
                socket.receive(packet);
                processMessage(packet);
            } catch (IOException e) {

            }
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        System.out.println("Mensaje recibido: " + message);
        String[] parts = message.split(specialChar);
        float x;
        float y;

        switch(parts[0]){
            case "connection":
                manageConnection(parts[1], Integer.parseInt(parts[2]), packet.getAddress());
                break;
            case "startgame":
                GameData.networkListener.startGame();
                break;
            case "mapselected":
                GameData.networkListener.mapSelected(parts[1]);
                break;
            case "createmap":
                GameData.networkListener.createMap(parts[1],parts[2]);
                break;
            case "addcards":
                String cardImage = parts[1];
                x = Float.parseFloat(parts[2]);
                y = Float.parseFloat(parts[3]);
                int width = Integer.parseInt(parts[4]);
                int height = Integer.parseInt(parts[5]);
                GameData.networkListener.addCardsToPanel(cardImage,x,y,width,height,parts[6]);
                break;
            case "spawnentity":
                int id = Integer.parseInt(parts[1]);
                x = Float.parseFloat(parts[2]);
                y = Float.parseFloat(parts[3]);
                float hitboxWidth = Float.parseFloat(parts[5]);
                float hitboxHeight = Float.parseFloat(parts[6]);
                GameData.networkListener.spawnentity(id,x,y,parts[4],hitboxWidth,hitboxHeight);
                break;
            case "position":
               // GameData.networkListener.updateDinoPosition(Integer.parseInt(parts[3]),Float.parseFloat(parts[1]), Float.parseFloat(parts[2]));
                break;
            case "enemy":
               // handleEnemy(parts);
                break;
            case "scoredistance":
               // GameData.networkListener.setScoreDistance(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                break;
            case "gameover":
               // GameData.networkListener.gameOver();
                break;
        }

    }

    private void handleEnemy(String[] parts) {
        switch(parts[1]){
            case "create":
               // GameData.networkListener.addEnemy(Float.parseFloat(parts[2]), Float.parseFloat(parts[3]));
                break;
            case "move":
              //  GameData.networkListener.moveEnemy(Integer.parseInt(parts[2]), Float.parseFloat(parts[3]), Float.parseFloat(parts[4]));
                break;
            case "remove":
              //  GameData.networkListener.removeEnemy(Integer.parseInt(parts[2]));
                break;
        }
    }

    private void manageConnection(String state, int clientNumber, InetAddress serverIp) {
        this.serverIp = serverIp;
        switch(state){
            case "successful":
                GameData.clientNumber = clientNumber;
                break;
        }
    }

    public void sendMessage(String msg){
        byte[] data = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, serverIp, SERVER_PORT);
        try {
            socket.send(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void end(){
        end = true;
        socket.close();
    }

}
