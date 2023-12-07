package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement {

	boolean key = false;

	public ParedeRachada(Point2D Point2D) {
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
	public boolean isColidable() {
		return !key;
	}

	public void checkKey(Empilhadora bobcat) {
		if (bobcat.hasTool("Martelo")) {
			key = true;
			instance.removeGameElement(this);
		}
	}

}
