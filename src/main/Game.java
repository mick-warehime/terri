package main;

import org.newdawn.slick.*;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

import commands.FireCommand;
import commands.InteractCommand;
import commands.JumpCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import actors.Enemy;
import actors.Player;


// features to add
//
//  timed doors
//  weight sensitive switches
//  mirrors to reflect beam
//  portals to transform ether--> solid vice versa
//  add starting point and finish point collideable objects on the map
//  certain time before you have to put object then no timer?
//  other ones that get stuck until you can place it?
//  FIX BUG WITH TIMED PLATFORMS YOU CAN GET TRAPPED IN THEM (PUT TIMED PLATFORM JUST BELOW STARTING LOCATION AND JUST STAND THERE)
//  spikes, fire, death objects

//  make one enemy type that when it hits a put object it restores it

// Dvir TODO: 
// Fix inconsistency between Command and BasicCommand declarations (remove Command)
// Finish determining enemy behavior, figure out how to kill an enemy;
//
//   gameobject >> is near >> slightly bigger DOESNT SEEM TO BE WORKING?

public class Game extends BasicGame {

	static int width = 640;
	static int height = 480;
	static int pixel = 16;
	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Monkey Head";
	static int fpslimit = 59;
	private CollisionHandler collisionHandler;     

	private Player terri;
	private Level level;

//	private Ether activeEtherObject = null; 

	public Game() {
		super("Monkey Head");
	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {

		int mouseX = gc.getInput().getMouseX()+level.getMapX();
		int mouseY = gc.getInput().getMouseY()+level.getMapY();

		terri.update();
		level.update(mouseX, mouseY);

		if( gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){gc.exit();}

	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		level = new Level(0,0);
		// i dont like this initialization
		collisionHandler = level.getCollisionHandler();

		terri = new Player(32,300,collisionHandler);
		
		Enemy bob = new Enemy(240,300, collisionHandler);

		level.addEnemy(bob);

		//This translates keyboard/mouse inputs into commands, for the appropriate listeners
		InputProvider provider = new InputProvider(gc.getInput());
		//The listener is linked to the provider
		provider.addListener(terri.getListener());

		//Define action commands for provider
		Command jump = new JumpCommand();
		//Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand( -1);
		Command moveRight = new MoveCommand( 1);
		Command shoot = new FireCommand(gc.getInput(),level);
		Command restore = new RestoreCommand();
		Command interact = new InteractCommand();

		//Bind commands to keys
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		provider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		provider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		provider.bindCommand(new KeyControl(Input.KEY_E), interact);
		provider.bindCommand(new MouseButtonControl(0), shoot);
		provider.bindCommand(new MouseButtonControl(1), restore);
		
	}


	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();

		level.draw(g,(int) terri.getX(),(int)terri.getY(), mouseX,mouseY);
		terri.render(g, level.getMapX(),level.getMapY());
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		app.setDisplayMode(width, height, fullscreen);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(fpslimit);
		app.setShowFPS(showFPS);
		app.setAlwaysRender(true);
		app.start();

	}




}
