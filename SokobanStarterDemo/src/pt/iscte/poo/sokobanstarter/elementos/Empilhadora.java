package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.Consumable;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.Movable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Movable{
	
	private boolean justTeletreasported = false; // Indicates if it was teletrasported recently
	private String imageName;
	private int batteryLevel;
	private List<GameElement> toolList = new ArrayList<GameElement>();
	
	public Empilhadora(Point2D Point2D){
		super(Point2D);
		imageName = "Empilhadora_D";
		batteryLevel = 100;
	}
	
	public int getBateryLevel() {
		return batteryLevel;
	}
	
	public void setBatteryLevel(int val) {
		batteryLevel += val;
	}
	
	public void addToll(GameElement tool){
		toolList.add(tool);
	}
	
	public boolean hasTool(String tool) {
		for(GameElement element : toolList ) {
			if(element.getName().equals(tool));
				return true;
		}
		return false;
	}
	
	@Override
	public String getName() {
		return imageName;
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	public int getBatteryLevel() {
		return batteryLevel;
	}
	
	private boolean didIntercatWithElement(GameElement element,Direction direction){
		// Checks if the element can be moved
		if(element instanceof Movable) {
			// If it moves, decreases battery
			if(((Movable) element).move(direction)) {
				batteryLevel --;
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	private boolean isValidPosition(  Point2D newPosition) {
		 return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
		           newPosition.getY() >= 0 && newPosition.getY() < 10;
		
	}
	
	@Override
	public boolean move(Direction direction) {
	    Point2D newPosition = Point2D.plus(direction.asVector());
	    List<GameElement> elementList = instance.getGameElement(newPosition);
	    
	    //Checks if the position is inside of the borders of the map
	    if(!isValidPosition(newPosition)) return false;
	    
	    for(GameElement element: elementList) {
	    	
	    	if(batteryLevel == 0)return false;
			
	    	if(element instanceof ParedeRachada ) {
				((ParedeRachada) element).checkKey(this);
			}
			
	    	if(element.isColidable() && batteryLevel >  1) {
				if(!didIntercatWithElement(element, direction)) return false;
			}
			
		    if(element instanceof Consumable) {
	    		((Consumable) element).consume(this);
	    	} 
		    
	    }
	    batteryLevel--;	    	
	    justTeletreasported = false;
	    Point2D = newPosition;
        imageName = "Empilhadora_" + direction.name().charAt(0);
        return true;
	   
	    
	}


	@Override
	public void setPosition(Point2D newpoint) {
		this.Point2D = newpoint;
	}
	
	@Override
	public void setJustTeletrasported(boolean bool) {
		justTeletreasported = bool;
	}

	@Override
	public boolean justTeletrasported() {
		return justTeletreasported;
		
	}

	@Override
	public boolean hasObjectBehind(Direction direction) {
		return false;
	}
	
	@Override
	public boolean isColidable() {
		return true;
	}


	
}