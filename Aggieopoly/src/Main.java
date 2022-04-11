/* import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main 
{
	public static void main(String args[])
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Aggieopoly("Aggieopoly"));
			
			appgc.setDisplayMode(1920, 1080, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Aggieopoly.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

} */



import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Main extends BasicGame
{
	public Main(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.drawString("Howdy!", 10, 10);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Main("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

/* 
public class Main
{
	public static void main(String args[])
	{
		System.out.println(org.lwjgl.Version.getVersion());
	}
}
*/