package com.aggieopoly.game;

import com.aggieopoly.game.views.ChatScreen;
import com.aggieopoly.game.views.GameScreen;
import com.aggieopoly.game.views.MainScreen;
import com.aggieopoly.game.views.MethodScreen;
import com.aggieopoly.game.views.RoleScreen;
import com.badlogic.gdx.Game;
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
	
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	
	MainScreen mainMenu;
	MethodScreen methodScreen;
	RoleScreen roleScreen;
	ChatScreen chatScreen;
	GameScreen gameScreen;
	
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
		
		//shapeRenderer.setProjectionMatrix(camera.combined);
		//shapeRenderer.begin(ShapeType.Line);
	    //shapeRenderer.end();
	}
	
	@Override
	public void dispose () 
	{
		batch.dispose();
		shapeRenderer.dispose();	
	}
}
