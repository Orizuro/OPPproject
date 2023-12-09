package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.Consumable;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.Movable;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement implements Movable{
	
	private boolean tele = false;
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
		batteryLevel =batteryLevel+val;
	}
	
	public void addToll(GameElement tool){
		toolList.add(tool);
	}
	
	public boolean searchToll(String tool) {
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
	
	@Override
	public boolean move(Direction direction) {
	    Point2D newPosition = Point2D.plus(direction.asVector());
	    List<GameElement> elementList = instance.getGameElement(newPosition);
	    if (isValidPosition(elementList, newPosition, direction)  ) {
	    	batteryLevel--;	    	
	    	tele = false;
	    	for(GameElement element : elementList) {
	    		if(element instanceof Consumable) {
	    			((Consumable) element).consume(this);
	    		} 
	    	}
	    	Point2D = newPosition;
            imageName = "Empilhadora_" + direction.name().charAt(0);
	        return true;
	    }
	    
	    return false;
	}

	private boolean isValidPosition(List<GameElement> elementList, Point2D newPosition, Direction direction) {
		if(batteryLevel == 0)return false;
		for(GameElement element : elementList ) {
			if(element instanceof ParedeRachada ) {
				((ParedeRachada) element).checkKey(this);
			}
			if(element.isColidable()&& batteryLevel >  1) {
				if(element instanceof Movable) {
					if(((Movable) element).move(direction)) {
						batteryLevel --;
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
	           newPosition.getY() >= 0 && newPosition.getY() < 10;
	}

	@Override
	public boolean hasObjectBehind(Direction direction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPosition(Point2D newpoint) {
		this.Point2D = newpoint;
	}
	@Override
	public void setJustTeletrasported(boolean bool) {
		tele = bool;
	}


	@Override
	public boolean justTeletrasported() {
		return tele;
		
	}



	
}