package pt.iscte.poo.sokobanstarter.elementos;

import java.util.List;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.sokobanstarter.onUpdateElement;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement  implements onUpdateElement{
	
	private boolean hasBoxOnTop = false;
	
	public Alvo(Point2D Point2D){
		super(Point2D);
	}
	public boolean getBoxOnTop() {
		return hasBoxOnTop;
	}
	
	@Override
	public String getName() {
		return "Alvo";
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public void elementUpdate() {
		if(instance.searchElement(instance.getGameElement(this.Point2D), "Caixote")) {
			hasBoxOnTop =  true;
		}else {
		hasBoxOnTop = false;
		}
	}




}
