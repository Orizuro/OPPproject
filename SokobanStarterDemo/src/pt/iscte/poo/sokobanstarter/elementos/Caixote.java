package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement{

	private Point2D position;
	private GameEngine instance = GameEngine.getInstance();
	
	public Caixote(Point2D Point2D){
		this.position = Point2D;
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
		return 1;
	}
	private boolean hasObjectBehind(Direction direction) {
		Point2D newPosition = position.plus(direction.asVector());
		GameElement actualElement = instance.getGAmeElementFromLayer(instance.getGameElement(newPosition),1);
		
		if(actualElement != null) {
			return true;
		}
		return false;
		
	}
	
	@Override
	public boolean move(Direction direction) {
		if(hasObjectBehind(direction)) return false;
		Point2D newPosition = position.plus(direction.asVector());
		GameElement actualElement = instance.getGAmeElementFromLayer(instance.getGameElement(newPosition),0);
		if(actualElement.getName().equals("Alvo")) 
			instance.removeGameElement(this);
		position = newPosition;
		return true;
		
	}
	@Override
	public boolean isMovable(Direction direction) {
		
		if(hasObjectBehind(direction)) return false;
		return true;
	}
	@Override
	public boolean isColidable() {
		return true;
	}

}
