package com.aggieopoly.game;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

class ReceiverThread extends Thread {

   private DatagramSocket udpClientSocket;

   public ReceiverThread(DatagramSocket ds) throws SocketException {
      this.udpClientSocket = ds;
   }

   public void run() {

      byte[] receiveData = new byte[1024];

      while (true) {

         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         try {
            udpClientSocket.receive(receivePacket);
            String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println(serverReply);

            Thread.yield();
         }
         catch (IOException ex) {
            System.err.println(ex);
         }
      }
   }
}