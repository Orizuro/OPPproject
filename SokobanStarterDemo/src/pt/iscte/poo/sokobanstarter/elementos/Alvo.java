package pt.iscte.poo.sokobanstarter.elementos;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.GameEngine;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement{
	
	private GameEngine instance = GameEngine.getInstance();

	private boolean hasBlock = false;
	
	public Alvo(Point2D Point2D){
		super(Point2D);
	}
	
	@Override
	public String getName() {
		return "Alvo";
	}

	@Override
	public int getLayer() {
		return 0;
	}



}
