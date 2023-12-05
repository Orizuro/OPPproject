package pt.iscte.poo.sokobanstarter.elementos;




import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement{
	
	
	
	public ParedeRachada(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "ParedeRachada";
	}



	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public boolean isColidable(Empilhadora bobcat) {
		if(bobcat.searchToll("Martelo")) {
			instance.removeGameElement(this);
			return false;
		}
		return true;
	}


}
