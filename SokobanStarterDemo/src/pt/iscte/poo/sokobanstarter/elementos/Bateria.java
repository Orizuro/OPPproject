package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.Consumable;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements Consumable {

	public Bateria(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Bateria";
	}

	@Override
	public int getLayer() {
		return 2;
	}

	@Override
	public void consume(Empilhadora bobcat) {
		bobcat.setBatteryLevel(50);
		instance.removeGameElement(this);
	}

}
