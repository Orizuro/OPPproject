package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement{

	private Point2D Point2D;
	
	public Caixote(Point2D Point2D){
		this.Point2D = Point2D;
	}
	
	@Override
	public String getName() {
		return "Caixote";
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
