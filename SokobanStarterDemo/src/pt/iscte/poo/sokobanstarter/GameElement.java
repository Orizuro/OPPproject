package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameElement implements ImageTile{

	protected GameEngine instance = GameEngine.getInstance();
	protected Point2D Point2D;
	
	public GameElement(Point2D Point2D) {
		this.Point2D = Point2D;
	};
	
	public Point2D getPosition() {
		return Point2D;
	}
	
	public abstract int getLayer();
	
	public abstract String getName();
	
	public  boolean isColidable() {
		return false;
	}

	

	


}
