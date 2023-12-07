package pt.iscte.poo.sokobanstarter.elementos;

import java.util.List;

import pt.iscte.poo.sokobanstarter.Consumable;
import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.sokobanstarter.onUpdateElement;
import pt.iscte.poo.utils.Point2D;

public class Buraco extends GameElement implements onUpdateElement, Consumable {

	boolean isCoverd = false; // Indicates if the hole is covered by a palette

	public Buraco(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Buraco";
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public void elementUpdate() {
		// Get elements at this buraco location
		List<GameElement> elementList = instance.getGameElement(this.Point2D);

		for (GameElement element : elementList) {
			// Check if the boraco is covered by a palette
			if (element instanceof Palete) {
				isCoverd = true;
				// Remove movable elements if the buraco is not covered by a palette
			} else if (element instanceof Movable && !isCoverd) {
				instance.removeGameElement(element);
			}

		}
	}

	@Override
	public void consume(Empilhadora bobcat) {
		// If the buraco is not covered by a palette and Empilhadora consumes it,
		// trigger a lose condition
		if (!isCoverd) {
			instance.lose();
		}
	}

}
