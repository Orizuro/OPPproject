package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement{
	

	
	public Teleporte(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "Teleporte";
	}



	@Override
	public int getLayer() {
		return 0;
	}

}
