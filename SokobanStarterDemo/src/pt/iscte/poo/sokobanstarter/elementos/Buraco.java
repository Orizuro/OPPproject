package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement{
	

	
	public Buraco(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "Buraco";
	}



	@Override
	public int getLayer() {
		return 1;
	}

}
