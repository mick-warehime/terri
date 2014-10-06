package main;


import io.JumpCommand;
import io.MoveCommand;
import io.PlayerInputListener;

import java.util.Vector;

import io.GenericCommand;

import org.newdawn.slick.*;
//import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.geom.Rectangle;

//TODO: Fix fall stutter. Add wall jumping.
public class Player {


    private float x;
    private float y;
    private float vx;
    private float vy;
    private float ups = 20;
    private float wallUps = 15;
    private float gravity = 1;
    private float runAcc = 2;
    private float runDec = 1;
    private float maxSpeed = 5;
    private int height = 32;
    private int width = 32;
    private int jumpTimer= 0;
    private int jumpTimerIncrement = 20;
    private Rectangle rect = new Rectangle(0,0,width,height);
    private Image sprite = new Image("data/head.png"); 
    /////////
    //This object is what listens for commands for the player
    PlayerInputListener listener = new PlayerInputListener();	
    private Collide collisionHandler;
    
    public Player(int x, int y, Collide collisionHandler) throws SlickException {
        
        this.x = (float) x;
        this.y = (float) y;
        this.vx = 0;
        this.vy = 0;
        rect.setX((int)this.x);
        rect.setY((int)this.y);

        this.collisionHandler = collisionHandler;        
        this.collisionHandler.addPlayerRect(rect);

    }

    public int getX (){return (int)x;}
    public int getY (){return (int)y;}

    public void render(Graphics g, int mapX, int mapY){
        sprite.draw((int)this.x-mapX,(int)this.y-mapY);    
    }


    public void update(){

        doActions();
        movePhysics();

        updateTimers();
    }

    public PlayerInputListener getListener(){
        return listener;
    }

    public void attemptRunTo(int direction) {
    	//Only accelerate if not in air
    	if (!isTouchingGround()){return;}
    	
        if (direction>0 ){
            vx = Math.min(vx + runAcc, maxSpeed);
        }else if(direction<0){
            vx = Math.max(vx - runAcc, -maxSpeed);
        }
//        System.out.println("Velocity:" + vx);
//        }else{
//            vx = 0;
//        }

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

    ///////////////////////////PRIVATES

    //
    private void attemptWallJump(){
    	
    	if (canWallJump()){
//    		System.out.println("Yippee!");
    		this.vy -=wallUps;
            jumpTimer += jumpTimerIncrement;
            this.vx = -this.vx;
    		}
//    	else{ 
////    		System.out.println("Aw...");
//    		
//    		}
    	
    	return;
    }
    
    //
    private boolean canWallJump(){
    	boolean answer = false;
    	
    	//Check that I am touching a wall in the direction
    	//that I'm moving	
    	if (vx<0){
    		displace(-1,0);
    		answer = isCollided();
    		displace(1,0);
    	}else{
    		displace(1,0);
    		answer = isCollided();
    		displace(-1,0);
    	}
    	//Check that jumpTimer is 0;
    	answer = answer && (jumpTimer == 0); 
    	
    	
    	return answer;
    }
    
    
    // Attempts a displacement, or a smaller
    // one if possible. returns success or failure
    private boolean attemptDisplacement(float dx, float dy){
        boolean notCollided = false;
        
        //Null displacement always succeeds
        if (dx == 0 && dy == 0){return true;}
        
        //x only displacement
        if (dy == 0){
        	if (dx>0){
        		for (int ddx = (int) dx; ddx >0 ; ddx--){//Try displacements until they work
        			displace(ddx,0);
        			notCollided = !isCollided();
        			if (notCollided){break;};
        			displace(-ddx,0); // Only keep the displacement if no collision occured
        		} 	
        		return notCollided;
        	}
        	if (dx<0){
        		for (int ddx = (int) dx; ddx <0 ; ddx++){//Try displacements until they work
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
        		for (int ddy =(int) dy; ddy >0 ; ddy--){//Try displacements until they work
        			displace(0,ddy);
        			notCollided = !isCollided();
        			if (notCollided){break;};
        			displace(0,-ddy); // Only keep the displacement if no collision occured
        		} 	
        		return notCollided;
        	}
        	if (dy<0){
        		for (int ddy =(int) dy; ddy <0 ; ddy++){//Try displacements until they work
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

        boolean triedMove = false;
        boolean triedJump = false;
        
        for (Command cmd : commands){
            ((GenericCommand)cmd).execute(this);
            if (cmd instanceof JumpCommand){
            	triedJump = true;
            }
            if (cmd instanceof MoveCommand){
            	triedMove = true;
            }
        }
        //Attempt a wall jump (conditioned on having tried a jump and move)
        if (triedJump && triedMove){
        	attemptWallJump();
        }
        //Decelerate if no move command given
        if (!triedMove && isTouchingGround()){
        	decelerate();
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
    private void displace(float dx, float dy){
        this.x += dx;
        this.y += dy;
        rect.setX((int)this.x);
        rect.setY((int)this.y);
        return;
    }

    private void decelerate(){
    	if (vx>0){ vx = Math.max(vx-runDec,(float) 0);}
    	if (vx<0){ vx = Math.min(vx+runDec,(float) 0);}
    }
}
