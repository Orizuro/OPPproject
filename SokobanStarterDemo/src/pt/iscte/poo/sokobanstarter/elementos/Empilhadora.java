package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;

import java.util.Random;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{
	private GameEngine instance;
	private Point2D position;
	private String imageName;
	public int batteryLevel;
	
	public Empilhadora(Point2D initialPosition, GameEngine INSTANCE){
		position = initialPosition;
		imageName = "Empilhadora_D";
		instance = INSTANCE;
		batteryLevel = 100;
	}
	
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}
	public int getBatteryLevel() {
		return batteryLevel;
	}
	
	public boolean move(Direction direction) {
	    Point2D newPosition = position.plus(direction.asVector());
	    GameElement ColidableElement = instance.getGAmeElementFromLayer(instance.getGameElement(newPosition),1);
	    GameElement ConsumableeElement = instance.getGAmeElementFromLayer(instance.getGameElement(newPosition),2);
	    if (isValidPosition(ColidableElement, newPosition, direction) && batteryLevel > 0 ) {
	    	
	    	try {
	    		if(ColidableElement.move(direction, instance) && batteryLevel > 1 ) {
	    			batteryLevel = batteryLevel-1;
	    		}
	    	}catch(NullPointerException ex) { 
	    	}
	    	try {
	    		ConsumableeElement.consume(direction, this, instance);
	    	}catch(NullPointerException ex) { 
	    	}
	    	
	    	batteryLevel = batteryLevel-1;
	        position = newPosition;
	        
	        switch (direction) {
	            case UP:
	                imageName = "Empilhadora_U";
	                break;
	            case DOWN:
	                imageName = "Empilhadora_D";
	                break;
	            case LEFT:
	                imageName = "Empilhadora_L";
	                break;
	            case RIGHT:
	                imageName = "Empilhadora_R";
	                break;
	            // Add more cases if needed
	        }
	        return true;
	    }
	    return false;
	}

	private boolean isValidPosition(GameElement actualElement, Point2D newPosition, Direction direction) {
		System.out.println(batteryLevel);
		try {
		
		if(!actualElement.isMovable(direction)){
			return false;
		}

		return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
	           newPosition.getY() >= 0 && newPosition.getY() < 10;
		}catch(NullPointerException ex){
			return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
			           newPosition.getY() >= 0 && newPosition.getY() < 10;
		}
	}


	
}
