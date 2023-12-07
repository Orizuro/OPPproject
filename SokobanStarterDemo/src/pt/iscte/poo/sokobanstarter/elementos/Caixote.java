package pt.iscte.poo.sokobanstarter.elementos;

import java.util.List;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement implements Movable {

	private boolean justTeletreasported = false; // Indicates if it was teletrasported recently


	public Caixote(Point2D Point2D) {
		super(Point2D);
	}

	@Override
	public String getName() {
		return "Caixote";
	}

	@Override
	public int getLayer() {
		return 1;
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
	public boolean move(Direction direction) {
		if (hasObjectBehind(direction))
			return false;
		justTeletreasported = false;
		Point2D newPosition = Point2D.plus(direction.asVector());

		Point2D = newPosition;
		return true;

	}

	@Override
	public boolean isColidable() {
		return true;
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
