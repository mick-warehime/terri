package main;

import org.newdawn.slick.*;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

import commands.FireCommand;
import commands.GenericCommand;
import commands.InteractCommand;
import commands.JumpCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import etherable.ProgressPoint;
import actors.Player;


//  To doo to doo to do to do do do to doooooo
 
//   platforms that have a limited number of times you can be on them?
//   drawing platform timers
//   enemy only platforms?
//   multiple weight sensitive switchs to open a door

//  find a way to skip empty objects on .tmx

//  weird bug when you jump ether objects tend to lag behind in drawing?? 
//  maybe check the order things are drawn

//  when do we want level to reset and when do we want to transport terri???!?

//  a float option when holding space after jumping? like a little parachute or booster pack or wings

//  ladders

//  gravity gun for enemies?

//  mario bullets canon or auto-turret aims at player

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
	private ProgressPoint progress;
	private FireCommand shootCommand;

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
		
		progress = level.getProgressPoint();
		

		if (terri.isDying()){
			initializeLevel(currentLevel);
			
//			// to teleport
//			level.setProgressPoint(progress);
//			terri.resetPlayer(level);
			
		}
		
		
		if( gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){gc.exit();}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		initializeKeyBindings(gc);

		initializeLevel(currentLevel);

		
	}

	private void initializeLevel(int levelNumber) throws SlickException{
		level = new Level(levelNumber);
		
		if(progress==null){
			progress = level.getProgressPoint();
		}
		
		level.setProgressPoint(progress);
		
		// i dont like this initialization
		collisionHandler = level.getCollisionHandler();

		terri = new Player(level.getProgressX(),level.getProgressY(),collisionHandler);

		
		//Keyboard stuff
		
		keyboardInputProvider.addListener(terri.getListener());
		shootCommand.setLevel(level);
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
		shootCommand = new FireCommand(gc.getInput());
		Command restore = new RestoreCommand();
		Command interact = new InteractCommand();
		
		//Bind commands to keys
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_E), interact);
		keyboardInputProvider.bindCommand(new MouseButtonControl(0), shootCommand);
		keyboardInputProvider.bindCommand(new MouseButtonControl(1), restore);
	}



	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();

		level.draw(g,(int) terri.getX(),(int)terri.getY(), mouseX,mouseY);
		terri.render(level.getMapX(),level.getMapY());
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
