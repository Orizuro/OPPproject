package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;

import java.util.Random;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{
	private GameEngine instance;
	private Point2D position;
	private String imageName;
	
	public Empilhadora(Point2D initialPosition, GameEngine INSTANCE){
		position = initialPosition;
		imageName = "Empilhadora_D";
		instance = INSTANCE;
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
	
	public void moveDown() {
	    Point2D newPosition = position.plus(Direction.DOWN.asVector());
	    if (isValidPosition(newPosition)) {
	        position = newPosition;
	        imageName="Empilhadora_D";
	    }
	}

	public void moveLeft() {
	    Point2D newPosition = position.plus(Direction.LEFT.asVector());
	    if (isValidPosition(newPosition)) {
	        position = newPosition;
	        imageName="Empilhadora_L";
	    }	
	}

	public void moveRight() {
	    Point2D newPosition = position.plus(Direction.RIGHT.asVector());
	    if (isValidPosition(newPosition)) {
	        position = newPosition;
	        imageName="Empilhadora_R";
	    }
	}
	public void moveUP() {
	    Point2D newPosition = position.plus(Direction.UP.asVector());
	    if (isValidPosition(newPosition)) {
	        position = newPosition;
	        imageName="Empilhadora_U";
	    }
	}

	private boolean isValidPosition(Point2D newPosition) {
		if(instance.getGameElement( newPosition) == null) return false;
		if(instance.getGameElement( newPosition).getName().equals("Parede")) {
			return false;
		}
			
	    return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
	           newPosition.getY() >= 0 && newPosition.getY() < 10;
	}
	
}