package pt.iscte.poo.sokobanstarter.elementos;

import java.util.Random;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement{

	private Point2D position;
	private String imageName;
	
	public Empilhadora(Point2D initialPosition){
		position = initialPosition;
		imageName = "Empilhadora_D";
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
	    return newPosition.getX() >= 0 && newPosition.getX() < 10 &&
	           newPosition.getY() >= 0 && newPosition.getY() < 10;
	}
	
	
	
	public void move() {
		
		// Gera uma direcao aleatoria para o movimento
		Direction[] possibleDirections = Direction.values();
		Random randomizer = new Random();
		int randomNumber = randomizer.nextInt(possibleDirections.length);
		Direction randomDirection = possibleDirections[randomNumber];
		
		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Point2D newPosition = position.plus(randomDirection.asVector());
		if (newPosition.getX()>=0 && newPosition.getX()<10 && 
			newPosition.getY()>=0 && newPosition.getY()<10 ){
			position = newPosition;
		}
	}
}