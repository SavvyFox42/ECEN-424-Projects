package com.aggieopoly.game.views;

import com.aggieopoly.game.Aggieopoly;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.HashSet;
import java.util.Scanner;

public class ChatScreen implements Screen
{
	String ipadd = null;
	int portadd = -1;
	boolean connected = false;
	
	
	int mode = -1;
	
	Aggieopoly parent;
	Stage stage;
	Table mainTable;
	
	TextField tf1;
	TextField ip;
	
	TextArea ta;
	TextField port;
	TextButton connect;
	TextButton disconnect;
	TextButton send;
	TextButton toMM;
	
	Label label;
	Label cd;
	
	public ChatScreen(Aggieopoly parent)
	{
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
	}
	

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		
		Skin skin = new Skin(Gdx.files.local("skins/glassy/skin/glassy-ui.json"));
				
		String str = null;
		
		if (Aggieopoly.TCP_UDP == 1)
		{
			if (Aggieopoly.CLIENT_SERVER == 1)
			{
				str = "TCP Client Mode";
				mode = 1;
			}
			else if (Aggieopoly.CLIENT_SERVER == 2)
			{
				str = "TCP Server Mode";
				mode = 2;
			}
				
		}
		else if (Aggieopoly.TCP_UDP == 2)
		{
			if (Aggieopoly.CLIENT_SERVER == 1)
			{
				str = "UDP Client";
				mode = 3;
			}
			else if (Aggieopoly.CLIENT_SERVER == 2)
			{
				str = "UDP Server";
				mode = 4;
			}
		}
		
		mainTable = new Table();
		mainTable.top();
		mainTable.setFillParent(true);
		//mainTable.setDebug(true);
		
		ta = new TextArea("DDD", skin);
		Window wind = new Window("Parameters", skin);
		tf1 = new TextField("Enter Message: ", skin);
		send = new TextButton("Send", skin);
		connect = new TextButton("Connect", skin);
		disconnect = new TextButton("Disconnect", skin);
		Label ipLabel = new Label("IP Address: ", skin);
		ipLabel.setColor(1, 0, 0, 1);
		ipLabel.setFontScale(2.0f);
		Label portLabel = new Label("Port: ", skin);
		portLabel.setColor(1, 0, 0, 1);
		portLabel.setFontScale(2.0f);
		ip = new TextField("xxx.xxx.xxx.xxx", skin);
		port = new TextField("xxxx", skin);
		cd = new Label("No Connection", skin);
		cd.setFontScale(4.0f);
		cd.setColor(Color.BLUE);
		Table subTable = new Table();
		toMM = new TextButton("Main Menu", skin);
		subTable.center();
		subTable.add(send).width(stage.getWidth()/6).height(stage.getHeight()*.1f).padRight(stage.getWidth()/24);
		subTable.add(toMM).width(stage.getWidth()/6).height(stage.getHeight()*.1f).padLeft(stage.getWidth()/24);
		
		

		mainTable.add(ta).width(stage.getWidth()/2).height((stage.getHeight()*.9f));
		//mainTable.pad(10);
		mainTable.add(wind).width(stage.getWidth()/2).height((stage.getHeight()*.9f));
		mainTable.row();
		mainTable.add(tf1).width(stage.getWidth()/2).height(stage.getHeight()*.1f);
		mainTable.add(subTable);
		//mainTable.add(send).width(stage.getWidth()/6).height(stage.getHeight()*.1f);
		
		wind.add(cd).colspan(2);
		wind.row().pad(15);
		wind.add(ipLabel).padRight(5);
		wind.add(ip);
		wind.row().pad(15);
		wind.add(portLabel).padRight(5);
		wind.add(port);
		wind.row().pad(15);
		wind.add(connect);
		wind.row().pad(15);
		wind.add(disconnect);
		
		stage.addActor(mainTable);
		
		send.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				// Send to UDP Server
				// Clear the input space
				if (tf1.getText() != "" )
				{
					ta.setText(ta.getText() + "\n" + tf1.getText());
					tf1.setText("");
				}
			}
		});
		
		connect.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				
				ipadd = ip.getText();
				try
				{
					portadd = Integer.parseInt(port.getText());
				}
				catch (NumberFormatException e)
				{
					
				}
				
				if (mode == 3)
				{
					connected = true;
					// UDP Client Startup

					int clientPort = portadd;
					String host = "localhost";

					System.out.println("Connected to host " + host + ", on port " + clientPort);


					InetAddress ia = null;
					try {
						ia = InetAddress.getByName(host);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}

					Scanner myObj = new Scanner(System.in);
					System.out.print("Enter username: ");

					String userName = myObj.nextLine();
					System.out.println("Your username is: " + userName);

					SenderThread sender = null;
					try {
						sender = new SenderThread(ia, clientPort, userName);
					} catch (SocketException e) {
						e.printStackTrace();
					}
					sender.start();
					ReceiverThread receiver = null;
					try {
						receiver = new ReceiverThread(sender.getSocket());
					} catch (SocketException e) {
						e.printStackTrace();
					}
					receiver.start();
				}
				else if (mode == 4)
				{
					// UDP Server Startup

					int serverPort = portadd;
					HashSet<Integer> portSet = new HashSet<Integer>();

					System.out.println("Server launched on port " + serverPort);


					DatagramSocket udpServerSocket = null;
					try {
						udpServerSocket = new DatagramSocket(serverPort);
					} catch (SocketException e) {
						e.printStackTrace();
					}

					System.out.println("Server is now ready...\n");
					connect.setDisabled(true);
					disconnect.setDisabled(false);
					cd.setText("Connected!");
					cd.setColor(Color.GREEN);

					while(true)
					{
						byte[] receiveData = new byte[1024];

						DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

						try {
							udpServerSocket.receive(receivePacket);
						} catch (IOException e) {
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

						for(Integer port : portSet)
						{
							if(port != clientport)
							{
								DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);
								try {
									udpServerSocket.send(sendPacket);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}

				}
				else if (mode == 1)
				{
					// TCP Client Startup
				}
				else if (mode == 2)
				{
					// TCP Server Startup
				}
				if(connected)
				{
					connect.setDisabled(true);
					disconnect.setDisabled(false);
					cd.setText("Connected!");
					cd.setColor(Color.GREEN);
				}
			}
		});
		
		disconnect.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				// Send the Disconnect Command for either server or client
				connected = false;
						
				if (connected == false)
				{
					connect.setDisabled(false);
					disconnect.setDisabled(true);
					cd.setText("Not connected!");
					cd.setColor(Color.RED);
				};
				//Gdx.app.exit();
			}
		});
		
		toMM.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				parent.changeScreen(Aggieopoly.MAIN_MENU);
			}
		});
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.3125f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Add any incoming message from server to the chat window
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
