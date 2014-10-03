package main;


import io.PlayerInputListener;

import java.util.Vector;

import io.GenericCommand;


import org.newdawn.slick.*;
//import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;

//TODO: Fix fall stutter. Add wall jumping.
public class Player {


    private int x;
    private int y;
    private int vx;
    private int vy;
    private int ups = 25;
    private int gravity = 1;
    private int speed = 3;
    private int height = 32;
    private int width = 32;
    private int jumpTimer= 0;
    private int jumpTimerIncrement = 30;
    private Rectangle rect = new Rectangle(0,0,width,height);
    private Image sprite = new Image("data/head.png"); 
    /////////
    //This object is what listens for commands for the player
    PlayerInputListener listener = new PlayerInputListener();	
    private Collide collisionHandler;
    
    public Player(int x, int y, Collide collisionHandler) throws SlickException {
        
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        rect.setX(this.x);
        rect.setY(this.y);

        this.collisionHandler = collisionHandler;        
        this.collisionHandler.addPlayerRect(rect);

    }

    public double getX (){return x;}
    public double getY (){return y;}

    public void render(Graphics g, int mapX, int mapY){
        sprite.draw(this.x-mapX,this.y-mapY);    
    }


    public void update(){

        doActions();
        movePhysics();

        updateTimers();
    }

    public PlayerInputListener getListener(){
        return listener;
    }

    public void setVxToDirection(int direction) {
        if (direction>0){
            vx = speed;
        }else if(direction<0){
            vx = -speed;
        }else{
            vx = 0;
        }

        return;

    }

    public void attemptJump() {
        //Check that player is on solid ground
        if (canJump()){
            this.vy -=ups;
            jumpTimer += jumpTimerIncrement;
        }

        return;

    }

    ///////////////////////////

    // Attempts a displacement, or a smaller
    // one if possible. returns success or failure
    private boolean attemptDisplacement(int dx, int dy){
        boolean notCollided = false;
        
        //Null displacement always succeeds
        if (dx == 0 && dy == 0){return true;}
        
        //x only displacement
        if (dy == 0){
        	if (dx>0){
        		for (int ddx = dx; ddx >0 ; ddx--){//Try displacements until they work
        			displace(ddx,0);
        			notCollided = !isCollided();
        			if (notCollided){break;};
        			displace(-ddx,0); // Only keep the displacement if no collision occured
        		} 	
        		return notCollided;
        	}
        	if (dx<0){
        		for (int ddx = dx; ddx <0 ; ddx++){//Try displacements until they work
        			displace(ddx,0);
        			notCollided = !isCollided();
        			if (notCollided){break;};
        			displace(-ddx,0); // Only keep the displacement if no collision occured
        		} 	
        		return notCollided;
        	}
        }
        
        //y only displacement
        if (dx == 0){
        	if (dy>0){
        		for (int ddy = dy; ddy >0 ; ddy--){//Try displacements until they work
        			displace(0,ddy);
        			notCollided = !isCollided();
        			if (notCollided){break;};
        			displace(0,-ddy); // Only keep the displacement if no collision occured
        		} 	
        		return notCollided;
        	}
        	if (dy<0){
        		for (int ddy = dy; ddy <0 ; ddy++){//Try displacements until they work
        			displace(0,ddy);
        			notCollided = !isCollided();
        			if (notCollided){break;};
        			displace(0,-ddy); // Only keep the displacement if no collision occured
        		} 	
        		return notCollided;
        	}
        }
        
        //If x and y displacements occur, a success occurs if there is any
        // displacement
        boolean xAttemptSuccess = attemptDisplacement(dx,0);
        boolean yAttemptSuccess = attemptDisplacement(0,dy);
        
        return (xAttemptSuccess || yAttemptSuccess);
        
    }
    
    private void updateTimers(){
        if (jumpTimer>0){
            jumpTimer -=1;
        }
//        System.out.println(jumpTimer);
    }

    private boolean canJump(){
        return (isTouchingGround() && (jumpTimer==0)) ;
    }

    private boolean isTouchingGround() {
        boolean answer = false;
        displace(0,+1);
        answer = isCollided();
        displace(0,-1);

        return answer;
    }



    private void doActions(){

        Vector<Command> commands = listener.getCommands();

        for (Command cmd : commands){
            //            System.out.println("Doing something..." + ((BasicCommand)cmd).getName());
            ((GenericCommand)cmd).execute(this);
        }
    }

    //Displace the player according to his velocity, gravity, etc.
    // while checking for collisions
    private void movePhysics(){        

        //Apply gravity if not touching the ground
        if (!isTouchingGround()){
            this.vy += gravity;
        }


        //Horizontal movement and collision checking
        attemptDisplacement(vx,0);
        //reset the velocity to 0 (move command has to be called constantly)
        this.vx =0;

        //Vertical displacement
        boolean success = attemptDisplacement(0,vy);
        if (!success){this.vy = 0;} //Only set vy to 0 on a vertical collision
        



        assert !isCollided() : "Player is inside an object!";

    }

    //Fix this. Level is a global variable
    private boolean isCollided(){
        //            System.out.println(Level.isBlocked(rect));
//        System.out.println(collisionHandler.isCollided(rect));
    	return collisionHandler.isCollided(rect);
    }

    //Displaces the player 
    private void displace(int dx, int dy){
        this.x += dx;
        this.y += dy;
        rect.setX(this.x+2);
        rect.setY(this.y+2);
        return;
    }

}
