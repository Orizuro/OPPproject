package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement{

	private Point2D position;
	
	public Caixote(Point2D Point2D){
		this.position = Point2D;
	}
	public void move(Direction direction ) {
		Point2D newPosition = position.plus(Direction.DOWN.asVector());
	    if (isValidPosition(newPosition)) {
	        position = newPosition;
	    }
	}
	private boolean isValidPosition(Point2D position) {
		return true;
	}
	
	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 0;
	}

}
