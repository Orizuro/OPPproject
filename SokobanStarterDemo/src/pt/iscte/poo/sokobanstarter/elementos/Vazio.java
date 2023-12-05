package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Vazio extends GameElement{
	

	public Vazio(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "Vazio";
	}



	@Override
	public int getLayer() {
		return 0;
	}


}
