package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.sokobanstarter.elementos.Empilhadora;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{
	public abstract String getName();
	public abstract Point2D getPosition();
	public abstract int getLayer();
	
	public boolean isMovable(Direction direction) {
		return false;
	}

	public boolean isConsumable(Direction direction) {
		return false;
	}
	public boolean move(Direction direction, GameEngine instance) {
		return false;
	}
	public boolean consume(Direction direction, Empilhadora bobcat, GameEngine instance) {
		return false;
	}
	


}
