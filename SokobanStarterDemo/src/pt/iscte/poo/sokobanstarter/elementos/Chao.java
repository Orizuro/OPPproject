package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Chao extends GameElement {

	public Chao(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Chao";
	}

	@Override
	public int getLayer() {
		return 0;
	}

}
