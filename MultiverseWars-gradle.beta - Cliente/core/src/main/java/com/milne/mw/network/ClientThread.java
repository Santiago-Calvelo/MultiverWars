package com.milne.mw.network;

import com.badlogic.gdx.Game;
import com.milne.mw.globals.GameData;
import com.milne.mw.globals.NetworkData;

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
                System.out.println("Servidor desconectado");
            }
        }
    }

    private void processMessage(DatagramPacket packet) {
        String message = new String(packet.getData()).trim();
        //System.out.println("Mensaje recibido: " + message);
        String[] parts = message.split(specialChar);
        float x;
        float y;

        switch(parts[0]){
            case "connection":
                manageConnection(parts[1], Integer.parseInt(parts[2]), packet.getAddress());
                sendMessage("ping!" + GameData.clientNumber);
                break;
            case "startgame":
                GameData.networkListener.startGame();
                break;
            case "mapselected":
                GameData.networkListener.mapSelected(parts[1]);
                break;
            case "createmap":
                GameData.networkListener.createMap(parts[1]);
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
                GameData.networkListener.addEntity(id,x,y,parts[4],hitboxWidth,hitboxHeight);
                break;
            case "updateplayerstate":
                int lives = Integer.parseInt(parts[1]);
                int energy = Integer.parseInt(parts[2]);
                GameData.networkListener.updatePlayerState(lives,energy);
                break;
            case "updateround":
                GameData.networkListener.updateRound(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                break;
            case "moveentity":
                GameData.networkListener.moveEntity(Integer.parseInt(parts[1]),Float.parseFloat(parts[2]),Float.parseFloat(parts[3]));
                break;
            case "animatetextureentity":
                GameData.networkListener.animateTextureEntity(Integer.parseInt(parts[1]),parts[2]);
                break;
            case "removeentity":
                GameData.networkListener.removeEntity(Integer.parseInt(parts[1]));
                break;
            case "bossattack":
                GameData.networkListener.drawBossAttack(parts[1],parts[2],Float.parseFloat(parts[3]),Float.parseFloat(parts[4]),Float.parseFloat(parts[5]),Float.parseFloat(parts[6]));
                break;
            case "bossattackupdate":
                System.out.println("Actualizando ataque jefe: ");
                GameData.networkListener.updateBossAttack(parts[1],Float.parseFloat(parts[2]),Float.parseFloat(parts[3]));
                break;
            case "bossattackremove":
                GameData.networkListener.bossAttackRemove(parts[1]);
                break;
            case "gameover":
                GameData.networkListener.gameOver();
                break;
            case "win":
                GameData.networkListener.win();
                break;
        }

    }

    private void manageConnection(String state, int clientNumber, InetAddress serverIp) {
        this.serverIp = serverIp;
        switch(state){
            case "successful":
                GameData.clientNumber = clientNumber;
                System.out.println(clientNumber);
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
