package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.onUpdateElement;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement implements onUpdateElement {

	private boolean hasBoxOnTop = false;// Indicates if the Alvo has Caixote on top

	public Alvo(Point2D Point2D) {
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
		if (instance.searchElement(instance.getGameElement(this.Point2D), "Caixote")) {
			hasBoxOnTop = true;
		} else {
			hasBoxOnTop = false;
		}
	}

}
