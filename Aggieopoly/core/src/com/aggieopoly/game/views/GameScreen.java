package com.aggieopoly.game.views;

import com.aggieopoly.game.Aggieopoly;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen
{
	Aggieopoly parent;
	Stage stage;
	
	Table mainTable;
	
	Table[] locations;
	Image[] prop_images;
	
	
	public GameScreen(Aggieopoly parent)
	{
		this.parent = parent;
		stage = new Stage(new ScreenViewport());
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		
		mainTable = new Table();
		
		for (int i = 0; i <40; i++)
		{
			locations[i] = new Table();
			locations[i].add(prop_images[i]);
		}
		mainTable.add(locations[0]);
		mainTable.add(locations[1]);
		mainTable.add(locations[2]);
		mainTable.add(locations[3]);
		mainTable.add(locations[4]);
		mainTable.add(locations[5]);
		mainTable.add(locations[6]);
		mainTable.add(locations[7]);
		mainTable.add(locations[8]);
		mainTable.add(locations[9]);
		mainTable.add(locations[10]);
		mainTable.row();
		mainTable.add(locations[11]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[12]);
		mainTable.row();
		mainTable.add(locations[13]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[14]);
		mainTable.row();
		mainTable.add(locations[15]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[16]);
		mainTable.row();
		mainTable.add(locations[17]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[18]);
		mainTable.row();
		mainTable.add(locations[19]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[20]);
		mainTable.row();
		mainTable.add(locations[21]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[22]);
		mainTable.row();
		mainTable.add(locations[23]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[24]);
		mainTable.row();
		mainTable.add(locations[25]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[26]);
		mainTable.row();
		mainTable.add(locations[27]);
		mainTable.add(new Actor()).colspan(9);
		mainTable.add(locations[28]);
		mainTable.row();
		mainTable.add(locations[29]);
		mainTable.add(locations[30]);
		mainTable.add(locations[31]);
		mainTable.add(locations[32]);
		mainTable.add(locations[33]);
		mainTable.add(locations[34]);
		mainTable.add(locations[35]);
		mainTable.add(locations[36]);
		mainTable.add(locations[37]);
		mainTable.add(locations[38]);
		mainTable.add(locations[39]);
		mainTable.add(locations[11]);
		
		
		stage.addActor(mainTable);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
		stage.draw();
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
