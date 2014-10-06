package main;

import java.util.ArrayList;

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
	private Collide collisionHandler;     

	private Player terri;
	private Level level;



	public Game() {
		super("Monkey Head");
	}

	@Override
	public void update(GameContainer gc, int t) throws SlickException {

		terri.update();


		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();

		if( gc.getInput().isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
		
		// NOTE NOTE NOTE
		
		// getting the activeID is a bit cludgey. probably something should have an 
		// activeObjectId and do isempty(activeID) check instead of looking for it all the time
		 
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){

			collisionHandler = level.getCollisionHandler();
			ArrayList<Ether> etherObjects = collisionHandler.getEtherObjects();

			// if there is an active etherObject the left click returns it 
			if(collisionHandler.activeEtherObjectExists()){
				int activeEtherObjId = collisionHandler.getActiveEtherObject();
				etherObjects.get(activeEtherObjId).put(mouseX+level.getMapX(),mouseY+level.getMapY());
			}
			// if there is no active etherObject then check if the clicked location corresponds to an EtherObject and gets it
			else{
				// check if mouse is above an ether object
				if(!collisionHandler.activeEtherObjectExists()){
					if(collisionHandler.isAtEtherObject(mouseX+level.getMapX(),mouseY+level.getMapY())){
						int etherObjId = collisionHandler.getEtherObjectId(mouseX+level.getMapX(), mouseY+level.getMapY());
						etherObjects.get(etherObjId).get();
					}
				}

			}
		}
		if(gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)){

			collisionHandler = level.getCollisionHandler();
			ArrayList<Ether> etherObjects = collisionHandler.getEtherObjects();
			if(collisionHandler.activeEtherObjectExists()){
				int activeEtherObjId = collisionHandler.getActiveEtherObject();
				etherObjects.get(activeEtherObjId).restore();
			}
		}



	}

	@Override
	public void init(GameContainer gc) throws SlickException {

		level = new Level(0,0);

		terri = new Player(32,300,level.getCollisionHandler());



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
