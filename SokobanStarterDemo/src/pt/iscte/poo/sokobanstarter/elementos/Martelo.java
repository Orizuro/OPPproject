package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.Consumable;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements Consumable{
	
	public Martelo(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "Martelo";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public boolean consume( Empilhadora bobcat) {
		bobcat.addToll( this) ;
		instance.removeGameElement(this);
		return true;
	}


}
