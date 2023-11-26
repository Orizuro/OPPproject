package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;

public abstract class GameElement implements ImageTile{
	public abstract boolean isMovable( Direction direction);



}
