package com.aggieopoly.game.views;

import com.aggieopoly.game.Aggieopoly;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ChatScreen implements Screen
{
	Aggieopoly parent;
	Stage stage;
	
	Label label;
	
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
			}
			else if (Aggieopoly.CLIENT_SERVER == 2)
			{
				str = "TCP Server Mode";
			}
				
		}
		else if (Aggieopoly.TCP_UDP == 2)
		{
			if (Aggieopoly.CLIENT_SERVER == 1)
			{
				str = "UDP Client";
			}
			else if (Aggieopoly.CLIENT_SERVER == 2)
			{
				str = "UDP Server";
			}
		}
		
		label = new Label(str, skin);
		stage.addActor(label);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
