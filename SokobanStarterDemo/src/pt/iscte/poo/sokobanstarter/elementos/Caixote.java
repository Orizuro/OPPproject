package pt.iscte.poo.sokobanstarter.elementos;


import pt.iscte.poo.sokobanstarter.GameElement;

import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement implements Movable{


	public Caixote(Point2D Point2D){
		super(Point2D);
	}

	
	@Override
	public String getName() {
		return "Caixote";
	}



	@Override
	public int getLayer() {
		return 1;
	}
	
	
	@Override
	public boolean move(Direction direction) {
		if(hasObjectBehind(direction)) return false;
		Point2D newPosition = Point2D.plus(direction.asVector());
		
		Point2D = newPosition;
		return true;
		
	}
	@Override
	public boolean isColidable(Empilhadora bobcat) {
		return true;
	}


}
