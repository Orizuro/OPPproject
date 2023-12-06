package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public interface Movable {
	
	default boolean move(Direction direction) {
		return false;
	}
	void setJustTeletrasported(boolean bool);
	
	boolean justTeletrasported();
	
	boolean hasObjectBehind(Direction direction);
	
	abstract void setPosition(Point2D newpoint);
	

}

