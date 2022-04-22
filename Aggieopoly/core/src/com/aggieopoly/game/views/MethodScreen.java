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

public class MethodScreen implements Screen 
{
	Aggieopoly game;
	Stage stage;
	
	TextButton udp_mode;
	TextButton tcp_mode;
	
	public MethodScreen(Aggieopoly parent)
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
		
		udp_mode = new TextButton("UDP Mode", skin);
		tcp_mode = new TextButton("TCP Mode", skin);
		
		table.add(udp_mode).fillX().uniform();
		table.row().pad(30, 0, 30, 0);
		table.add(tcp_mode).fillX().uniform();
		table.row();
		
		udp_mode.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Aggieopoly.TCP_UDP = 2;
				game.changeScreen(Aggieopoly.CLIENT_SERVER_CHOICE);
			}
			
		});
		
		tcp_mode.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// TODO Auto-generated method stub
				Aggieopoly.TCP_UDP = 1;
				game.changeScreen(Aggieopoly.CLIENT_SERVER_CHOICE);
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
