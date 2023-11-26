package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement{
	
	private Point2D position;
	
	public Bateria(Point2D Point2D){
		this.position = Point2D;
	}
	
	@Override
	public String getName() {
		return "Bateria";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	@Override
	public boolean isConsumable() {
		return true;
	}
	
	public boolean consume( Empilhadora bobcat, GameEngine instance) {
		bobcat.batteryLevel += 50;
		instance.removeGameElement(position, getLayer());
		return true;
	}

}
