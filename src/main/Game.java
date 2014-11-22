package main;

import org.newdawn.slick.*;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.gui.TextField;

import commands.ClimbCommand;
import commands.FireCommand;
import commands.InteractCommand;
import commands.JumpCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import gameobjects.ProgressPoint;
import actors.Player;

//  To doo to doo to do to do do do to doooooo

// Figure out how PlayerGraphics.render is putting the hover flame in the
// right place... 

//   platforms that have a limited number of times you can be on them?

//  find a way to skip empty objects on .tmx

//  weird bug when you jump ether objects tend to lag behind in drawing?? 
//  maybe check the order things are drawn check int vs float

//  what happens when a timed door needs to come back and there is an ether object that was put in the way?

// refactor action engine a bit (w.r.t. jump timers, etc.)


// TO DO 
// 1) doors that stay open?, turret switch (panel on wall)
// 2) start menu, lemming types
// 3) dog
// 4) item upgrades, doors to other areas, save points, map travel
// 0/5) graphics/ animations/particl effects

public class Game extends BasicGame {

	static int LOAD_STATE = 0;
	static int LEVEL_STATE = 1;

	static int width = 640;
	static int height = 480;
	static boolean fullscreen = false;
	static boolean showFPS = true;
	static String title = "Dvir is the woooorst.";
	static int fpslimit = 59;
	private InputProvider keyboardInputProvider;
	private Player terri;
	private Level level;
	private int currentLevel = 0;
	private ProgressPoint progress;
	private int gameState = LOAD_STATE;
	private TextField inputText;


	private int[] mousePos = new int[2];

	public Game() {
		super("Mick is a dick");
	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {


		if (gameState == LOAD_STATE){

			

			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)){
				gameState = LEVEL_STATE;
				currentLevel = Integer.parseInt(inputText.getText());
				initializeLevel(currentLevel);
			}

		}

		if (gameState == LEVEL_STATE){

			mousePos[0] = gc.getInput().getMouseX()+level.getMapX();
			mousePos[1] = gc.getInput().getMouseY()+level.getMapY();

			terri.update();
			level.update();

			progress = level.getProgressPoint();

			if (terri.isDying()){
				initializeLevel(currentLevel);
			}



		}
		if( gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){gc.exit();}
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		initializeKeyBindings(gc);

		inputText = new TextField(gc, gc.getDefaultFont(), height/2, height/2, 100, 20);
		
		

	}

	private void initializeLevel(int levelNumber) throws SlickException{
		level = new Level(levelNumber);

		if(progress==null){
			progress = level.getProgressPoint();
		}

		level.setProgressPoint(progress);
		level.setMousePosition(mousePos);
		// i dont like this initialization
		CollisionHandler collisionHandler = level.getCollisionHandler();

		terri = new Player(level.getProgressX(),level.getProgressY(),collisionHandler, mousePos);


		//Keyboard stuff
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
		Command shootCommand = new FireCommand();
		Command restore = new RestoreCommand();
		Command interact = new InteractCommand();
		Command ascend = new ClimbCommand(-1);
		Command descend = new ClimbCommand(+1);

		//Bind commands to keys
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_D), moveRight);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_W), ascend);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_S), descend);
		keyboardInputProvider.bindCommand(new KeyControl(Input.KEY_E), interact);
		keyboardInputProvider.bindCommand(new MouseButtonControl(0), shootCommand);
		keyboardInputProvider.bindCommand(new MouseButtonControl(1), restore);
	}



	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {

		if (gameState == LOAD_STATE){
			g.drawString("Enter Level: " , height/2, height/2-20);
			g.drawString("Press Enter to start", height/2, height*3/4);
			
			inputText.render(gc, g);
		}
		
		if (gameState == LEVEL_STATE){
			int mouseX = gc.getInput().getMouseX();
			int mouseY = gc.getInput().getMouseY();

			level.draw(g,(int) terri.getX(),(int)terri.getY());
			terri.render(level.getMapX(),level.getMapY());
		}
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
