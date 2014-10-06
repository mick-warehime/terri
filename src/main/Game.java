package main;

import io.JumpCommand;
import io.MoveCommand;

import org.newdawn.slick.*;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.KeyControl;

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


	private Ether activeEtherObject = null; 

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

		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			if(activeEtherObject==null){
				activeEtherObject = collisionHandler.isAtEtherObject(mouseX,mouseY);
				if(activeEtherObject!=null){
					activeEtherObject.setObjectToEther();
				}
			}else{

				if(collisionHandler.canPlaceEtherAt(activeEtherObject.getRect())){
					activeEtherObject.put(mouseX,mouseY);
				}

			}
		}

		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){

			if(activeEtherObject!=null){
				activeEtherObject.restore();
				activeEtherObject = null;
			}
		}




	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		level = new Level(0,0);
		// i dont like this initialization
		collisionHandler = level.getCollisionHandler();

		terri = new Player(32,300,collisionHandler);




		//This translates keyboard/mouse inputs into commands, for the appropriate listeners
		InputProvider provider = new InputProvider(gc.getInput());
		//The listener is linked to the provider
		provider.addListener(terri.getListener());

		//Define action commands for provider
		Command Jump = new JumpCommand("Jump");
		//		Command moveDown = new MoveCommand("move down", 0 ,8);
		Command moveLeft = new MoveCommand("move left", -1);
		Command moveRight = new MoveCommand("move right", 1);

		//Bind commands to keys
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), Jump);
		provider.bindCommand(new KeyControl(Input.KEY_A), moveLeft);
		//		provider.bindCommand(new KeyControl(Input.KEY_S), moveDown);
		provider.bindCommand(new KeyControl(Input.KEY_D), moveRight);


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
