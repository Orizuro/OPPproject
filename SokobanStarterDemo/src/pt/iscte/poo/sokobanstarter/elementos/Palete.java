package pt.iscte.poo.sokobanstarter.elementos;	

import java.util.List;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends GameElement implements Movable {

	private boolean inHole =false;
	
	public Palete(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "Palete";
	}



	@Override
	public int getLayer() {
		return 1;
	}

	
	@Override
	public boolean move(Direction direction) {
		if(hasObjectBehind(direction)|| inHole) return false;
		Point2D newPosition = Point2D.plus(direction.asVector());
		
		Point2D = newPosition;
		return true;
		
	}

	@Override
	public boolean isColidable(Empilhadora bobcat) {
		List<GameElement> actualElement = instance.getGameElement(Point2D);
		for(GameElement element: actualElement) {
			if(element.getName().equals("Buraco")) {
				inHole=true;
				return false;
			};
		}
		return true;
	}

}