package com.aggieopoly.game.views;

import com.aggieopoly.game.Aggieopoly;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen
{
	Aggieopoly game;
	Stage stage;
	
	TextButton startChatting;
	TextButton preferences;
	TextButton exitChat;

	public MainScreen(Aggieopoly parent)
	{
		this.game = parent;
		stage = new Stage(new ScreenViewport());
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
		
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(false);
		stage.addActor(table);
		
		Skin skin = new Skin(Gdx.files.local("skins/glassy/skin/glassy-ui.json"));
		
		startChatting = new TextButton("Start Chatting", skin);
		preferences = new TextButton("Preferences", skin);
		exitChat = new TextButton("Exit Chat", skin);
		
		table.add(startChatting).fillX().uniform();
		table.row().pad(30, 0, 30, 0);
		table.add(preferences).fillX().uniform();
		table.row();
		table.add(exitChat).fillX().uniform();
		
		startChatting.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				game.changeScreen(Aggieopoly.TCP_UDP_CHOICE);
			}
			
		});
		
		// Preferences
		
		exitChat.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Gdx.app.exit();
			}
			
		});
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
		Gdx.gl.glClearColor(0.3125f, 0, 0, 11);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
