package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement{
	
	private Point2D Point2D;
	
	public Buraco(Point2D Point2D){
		this.Point2D = Point2D;
	}
	
	@Override
	public String getName() {
		return "Buraco";
	}

	@Override
	public Point2D getPosition() {
		return Point2D;
	}

	@Override
	public int getLayer() {
		return 0;
	}
}