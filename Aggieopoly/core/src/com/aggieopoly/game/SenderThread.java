package com.aggieopoly.game;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class SenderThread extends Thread {

    private InetAddress serverIPAddress;
    private DatagramSocket udpClientSocket;
    private int serverport;
    private String userName;

    public SenderThread(InetAddress address, int serverport, String userName) throws SocketException {
        this.serverIPAddress = address;
        this.serverport = serverport;
        this.userName = userName;
        this.udpClientSocket = new DatagramSocket();
        this.udpClientSocket.connect(serverIPAddress, serverport);
    }

    public DatagramSocket getSocket() {
        return this.udpClientSocket;
    }

    public void run() {
        try {
            String data = "CLIENTCONNECTIONSTART";
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);

            byte[] sendData = new byte[1024];

            dos.writeInt(userName.length());
            dos.writeInt(data.length());
            dos.write(userName.getBytes());
            dos.write(data.getBytes());
            dos.flush();

            sendData = baos.toByteArray();

            DatagramPacket blankPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverport);
            udpClientSocket.send(blankPacket);

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                baos = new ByteArrayOutputStream();
                dos = new DataOutputStream(baos);

                String clientMessage = inFromUser.readLine();

                if (clientMessage.equals("/disconnect"))
                    break;

                sendData = new byte[1024];

                dos.writeInt(userName.length());
                dos.writeInt(clientMessage.length());
                dos.write(userName.getBytes());
                dos.write(clientMessage.getBytes());
                dos.flush();

                sendData = baos.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverport);

                System.out.println(userName + ": " + clientMessage);
                udpClientSocket.send(sendPacket);

                Thread.yield();
            }

            data = "CLIENTCONNECTIONEND";

            sendData = new byte[1024];

            dos.writeInt(userName.length());
            dos.writeInt(data.length());
            dos.write(userName.getBytes());
            dos.write(data.getBytes());
            dos.flush();

            sendData = baos.toByteArray();

            blankPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverport);
            udpClientSocket.send(blankPacket);

            System.out.println("You have left the chat.");
            System.exit(0);

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
