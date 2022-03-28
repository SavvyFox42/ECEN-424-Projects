package com.aggieopoly.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Aggieopoly extends ApplicationAdapter 
{	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	private Rectangle rect1;
	
	Texture img;
	Texture img2;
	
	@Override
	public void create () 
	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
		batch = new SpriteBatch();
		img = new Texture("monopoly logo.png");
		img2 = new Texture("TAMU Places/Academic Plaza.jpg");
	}

	@Override
	public void render () 
	{
		ScreenUtils.clear(0.3125f, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(img2, 100, 100);
		batch.end();
	}
	
	@Override
	public void dispose () 
	{
		batch.dispose();
		img.dispose();
	}
}
