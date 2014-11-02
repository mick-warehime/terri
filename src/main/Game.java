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
//  weight sensitive switches
//  mirrors to reflect beam
//  portals to transform ether--> solid vice versa
//  certain time before you have to put object then no timer?

//  FIX BUG WITH TIMED PLATFORMS YOU CAN GET TRAPPED IN THEM (PUT TIMED PLATFORM JUST BELOW STARTING LOCATION AND JUST STAND THERE)
 
//  ENEMY CANNON 

//  make one enemy type that when it hits a put object it restores it

// TODO: 
//
// Refactor Level, TileData?
// Should TimedElevator extend Elevator?


public class Game extends BasicGame {

	static int width = 640;
	static int height = 480;
	static int pixel = 16;
	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Monkey Head";
	static int fpslimit = 59;
	private CollisionHandler collisionHandler;     
	private InputProvider keyboardInputProvider;
	private Player terri;
	private Level level;
	private int currentLevel = 1;

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

		if (terri.isDying()){
			initializeLevel(currentLevel);
			
			//This is a stupid kludge that should be fixed
			//It should be defined once, but I need to pass it
			// a new level when initializeLevel runs.
			Command shoot = new FireCommand(gc.getInput(),level);
			keyboardInputProvider.bindCommand(new MouseButtonControl(0), shoot);
			
		}

	}

	@Override
	public void init(GameContainer gc) throws SlickException {



		initializeKeyBindings(gc);

		initializeLevel(currentLevel);
		
		Command shoot = new FireCommand(gc.getInput(),level);
		keyboardInputProvider.bindCommand(new MouseButtonControl(0), shoot);
	}

	private void initializeLevel(int levelNumber) throws SlickException{
		level = new Level(levelNumber);
		// i dont like this initialization
		collisionHandler = level.getCollisionHandler();

		terri = new Player(level.getMap(),collisionHandler);

		keyboardInputProvider.addListener(terri.getListener());
	}
	
	private void initializeKeyBindings(GameContainer gc){
		//This translates keyboard/mouse inputs into commands, for the appropriate listeners
		keyboardInputProvider = new InputProvider(gc.getInput());
		//The listener is linked to the provider


		//Define action commands for provider
		Command jump = new JumpCommand();
		//Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand( -1);
		Command moveRight = new MoveCommand( 1);
		
		Command restore = new RestoreCommand();
		Command interact = new InteractCommand();

		//Bind commands to keys
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_E), interact);
		
		keyboardInputProvider.bindCommand(new MouseButtonControl(1), restore);
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
