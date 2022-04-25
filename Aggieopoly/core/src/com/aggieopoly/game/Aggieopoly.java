package com.aggieopoly.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashSet;

import com.aggieopoly.game.views.ChatScreen;
import com.aggieopoly.game.views.GameScreen;
import com.aggieopoly.game.views.MainScreen;
import com.aggieopoly.game.views.MethodScreen;
import com.aggieopoly.game.views.RoleScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Aggieopoly extends Game 
{	
	private OrthographicCamera camera;
	
	public final static int MAIN_MENU = 1;
	public final static int TCP_UDP_CHOICE = 2;
	public final static int CLIENT_SERVER_CHOICE = 3;
	public final static int CHAT_SCREEN = 4;
	public final static int GAME_SCREEN = 5;
	
	public static int TCP_UDP = 0; 			// 1 = TCP, 2 = UDP
	public static int CLIENT_SERVER = 0;	// 1 = Server, 2 = Client
	public boolean NEW_MESSAGE = false;
	public String NEW_MESSAGE_TEXT = "";
	
	private boolean TCP_SERVER = false;
	private boolean TCP_CLIENT = false;
	private boolean UDP_SERVER = false;
	private boolean UDP_CLIENT = false;
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	MainScreen mainMenu;
	MethodScreen methodScreen;
	RoleScreen roleScreen;
	ChatScreen chatScreen;
	GameScreen gameScreen;
	
	//UDP SERVER Stuff
	DatagramSocket udpServerSocket = null;
	int serverPort;
	HashSet<Integer> portSet;
	
	//UDP CLIENT Stuff
	DatagramSocket udpClientSocket;
	ByteArrayOutputStream baos;
	DataOutputStream dos;
	byte[] sendData;
	InetAddress udp_ia;
	int udp_port;
	
	byte[] receiveData = new byte[1024];
	
	public void changeScreen(int nextScreen)
	{
		switch(nextScreen)
		{
		case MAIN_MENU:
			if (mainMenu == null) mainMenu = new MainScreen(this);
			this.setScreen(mainMenu);
			break;
		case TCP_UDP_CHOICE:
			if (methodScreen == null) methodScreen = new MethodScreen(this);
			this.setScreen(methodScreen);
			break;
		case CLIENT_SERVER_CHOICE:
			if (roleScreen == null) roleScreen = new RoleScreen(this);
			this.setScreen(roleScreen);			
			break;
		case CHAT_SCREEN:
			if (chatScreen == null) chatScreen = new ChatScreen(this);
			this.setScreen(chatScreen);
			break;
		case GAME_SCREEN:
			if (gameScreen == null) gameScreen = new GameScreen(this);
			this.setScreen(gameScreen);
			break;
		}
	}
	
	@Override
	public void create () 
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		
		this.changeScreen(MAIN_MENU);
	}
	
	@Override
	public void render () 
	{
		ScreenUtils.clear(0.3125f, 0, 0, 1);
		
		super.render();
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.end();
		
		NEW_MESSAGE = false;
		NEW_MESSAGE_TEXT = "";
		
		if(UDP_SERVER)
		{
			do {
				byte[] receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	
				try 
				{
					udpServerSocket.receive(receivePacket);
				} 
				catch (SocketTimeoutException ex)
				{
					break; // No packet coming in yet
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
	
				ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData());
				DataInputStream dis = new DataInputStream(bais);
	
				int usernameLen = 0;
				try {
					usernameLen = dis.readInt();
				} catch (IOException e) {
					e.printStackTrace();
				}
				int messageLen = 0;
				try {
					messageLen = dis.readInt();
				} catch (IOException e) {
					e.printStackTrace();
				}
				byte[] userName = new byte[usernameLen];
				byte[] message = new byte[messageLen];
				try {
					dis.read(userName);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					dis.read(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
	
				String userNameString = (new String(userName)).trim();
				String clientMessage = (new String(message)).trim();
				String returnMessage;
	
				if (clientMessage.equals("CLIENTCONNECTIONSTART")) {
					System.out.println("Client Connected - Socket Address: " + receivePacket.getSocketAddress());
					returnMessage = (userNameString + " has joined the chat.");
				} else if (clientMessage.equals("CLIENTCONNECTIONEND")) {
					System.out.println("Client Disconnected - Socket Address: " + receivePacket.getSocketAddress());
					returnMessage = (userNameString + " has left the chat.");
				} else {
					returnMessage = (userNameString+ ": " + clientMessage);
				}
	
				InetAddress clientIP = receivePacket.getAddress();
	
				int clientport = receivePacket.getPort();
				portSet.add(clientport);
	
				byte[] sendData  = new byte[1024];
	
				sendData = returnMessage.getBytes();
	
				for(Integer portx : portSet)
				{
					if(portx != clientport)
					{
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, portx);
						try {
							udpServerSocket.send(sendPacket);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} while(false);
		}
		else if(UDP_CLIENT)
		{
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	        try {
	            udpClientSocket.receive(receivePacket);
	            String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());

	            NEW_MESSAGE = true;
	            NEW_MESSAGE_TEXT = serverReply;
	            //System.out.println(serverReply);
	        }
	        catch( SocketTimeoutException ex)
	        {
	        	
	        }
	        catch (IOException ex) {
	           System.err.println(ex);
	        }
		}
		else if(TCP_SERVER)
		{
			
		}
		else if(TCP_CLIENT)
		{
			
		}
		
		//shapeRenderer.setProjectionMatrix(camera.combined);
		//shapeRenderer.begin(ShapeType.Line);
	    //shapeRenderer.end();
	}
	
	public void startUDP_Server(String hostname, int port)
	{
		UDP_SERVER = true;
		
		serverPort = port;
		portSet = new HashSet<Integer>();

		System.out.println("Server launched on port " + serverPort);
		
		try {
			udpServerSocket = new DatagramSocket(serverPort);
			udpServerSocket.setSoTimeout(100);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void startUDP_Client(String serverIP, int port, String username)
	{
		UDP_CLIENT = true;
		
		InetAddress ia = null;
		try {
			ia = InetAddress.getByName(serverIP);
			udpClientSocket = new DatagramSocket();
	        udpClientSocket.connect(ia, port);
	        udpClientSocket.setSoTimeout(100);
		} catch (UnknownHostException | SocketException e) {
			e.printStackTrace();
		}

		try {
			String data = "CLIENTCONNECTIONSTART";
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            sendData = new byte[1024];

            dos.writeInt(username.length());
            dos.writeInt(data.length());
            dos.write(username.getBytes());
            dos.write(data.getBytes());
            dos.flush();

            sendData = baos.toByteArray();

            DatagramPacket blankPacket = new DatagramPacket(sendData, sendData.length, ia, port);
            udpClientSocket.send(blankPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		udp_ia = ia;
		udp_port = port;
	}
	
	public void startTCP_Server()
	{
		TCP_SERVER = true;
	}
	
	public void startTCP_Client()
	{
		TCP_CLIENT = true;
	}
	
	public void sendMessage(String message, String userName)
	{
		if (UDP_CLIENT) 
		{			
			baos = new ByteArrayOutputStream();
	        dos = new DataOutputStream(baos);
	
	        String clientMessage = message;
	
	        sendData = new byte[1024];
	
	        try 
	        {
	        dos.writeInt(userName.length());
	        dos.writeInt(clientMessage.length());
	        dos.write(userName.getBytes());
	        dos.write(clientMessage.getBytes());
	        dos.flush();
	
	        sendData = baos.toByteArray();
	        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, udp_ia, udp_port);
	
	        System.out.println(userName + ": " + clientMessage);
	        udpClientSocket.send(sendPacket);
	        } catch(IOException ex)
	        {
	        	System.err.println(ex);
	        }
		}
	}
	
	public void disconnect(String userName)
	{
		if (true)
		{
			baos = new ByteArrayOutputStream();
	        dos = new DataOutputStream(baos);
			
			System.out.println("Disconnecting");
			String data = "CLIENTCONNECTIONEND";

            sendData = new byte[1024];

            try 
            {
	            dos.writeInt(userName.length());
	            dos.writeInt(data.length());
	            dos.write(userName.getBytes());
	            dos.write(data.getBytes());
	            dos.flush();
	
	            sendData = baos.toByteArray();
	
	            DatagramPacket blankPacket = new DatagramPacket(sendData, sendData.length, udp_ia, udp_port);
	            udpClientSocket.send(blankPacket);
            } catch( IOException ex)
            {
            	System.err.println(ex);
            }
		}
	}
	
	@Override
	public void dispose () 
	{
		batch.dispose();
		shapeRenderer.dispose();	
	}
}
