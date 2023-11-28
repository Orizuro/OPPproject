package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;

import java.util.List;
import java.util.Random;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{
	private GameEngine instance = GameEngine.getInstance();
	private Point2D position;
	private String imageName;
	public int batteryLevel;
	
	public Empilhadora(Point2D initialPosition){
		position = initialPosition;
		imageName = "Empilhadora_D";
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
	
	@Override
	public boolean move(Direction direction) {
	    Point2D newPosition = position.plus(direction.asVector());
	    List<GameElement> elementList = instance.getGameElement(newPosition);
	    if (isValidPosition(elementList, newPosition, direction) && batteryLevel > 0 ) {
	    	
	    	for(GameElement element : elementList) {
	    		if(element.move(direction) && batteryLevel > 1 ) {
	    			batteryLevel = batteryLevel-1;
	    		}
	    		if(element.isConsumable())
	    			element.consume( this);
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

	private boolean isValidPosition(List<GameElement> elementList, Point2D newPosition, Direction direction) {
		for(GameElement element : elementList ) {
			if(element.isColidable()) {
				if(!element.isMovable(direction)){
					return false;
				}
			}
		}
		return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
	           newPosition.getY() >= 0 && newPosition.getY() < 10;

	}


	
}
