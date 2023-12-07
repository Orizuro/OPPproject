package pt.iscte.poo.sokobanstarter.elementos;

import java.util.List;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends GameElement implements Movable {

	private boolean isInHole = false; // Indicates if Palete is in a Buraco
	private boolean justTeletreasported = false; // Indicates if it was teletrasported recently

	public Palete(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Palete";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public boolean move(Direction direction) {
		// If the Palete is in a Buraco or has a element behind, exit the method
		if (hasObjectBehind(direction) || isInHole)
			return false;

		Point2D newPosition = Point2D.plus(direction.asVector());
		// Resets the indicator
		justTeletreasported = false;
		Point2D = newPosition;
		return true;

	}

	@Override
	public boolean isColidable() {
		// Get elements at this Palete location
		List<GameElement> actualElement = instance.getGameElement(Point2D);
		for (GameElement element : actualElement) {
			// Checks if it inside of a Buraco, and if so doesn't collide
			if (element instanceof Buraco) {
				isInHole = true;
				return false;
			}
			;
		}
		return true;
	}

	@Override
	public boolean hasObjectBehind(Direction direction) {
		Point2D newPosition = Point2D.plus(direction.asVector());
		List<GameElement> actualElement = instance.getGameElement(newPosition);

		for (GameElement element : actualElement) {
			if (element.isColidable())
				return true;
		}
		return false;
	}

	@Override
	public void setPosition(Point2D newpoint) {
		this.Point2D = newpoint;
	}

	@Override
	public void setJustTeletrasported(boolean bool) {
		justTeletreasported = bool;
	}

	@Override
	public boolean justTeletrasported() {
		return justTeletreasported;

	}

}