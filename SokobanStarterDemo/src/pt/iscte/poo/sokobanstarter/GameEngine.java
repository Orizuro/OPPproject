package pt.iscte.poo.sokobanstarter;
import pt.iscte.poo.sokobanstarter.elementos.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.sokobanstarter.elementos.Alvo;
import pt.iscte.poo.sokobanstarter.elementos.Bateria;
import pt.iscte.poo.sokobanstarter.elementos.Buraco;
import pt.iscte.poo.sokobanstarter.elementos.Caixote;
import pt.iscte.poo.sokobanstarter.elementos.Chao;
import pt.iscte.poo.sokobanstarter.elementos.Empilhadora;
import pt.iscte.poo.sokobanstarter.elementos.Martelo;
import pt.iscte.poo.sokobanstarter.elementos.Palete;
import pt.iscte.poo.sokobanstarter.elementos.Parede;
import pt.iscte.poo.sokobanstarter.elementos.ParedeRachada;
import pt.iscte.poo.sokobanstarter.elementos.Teleporte;
import pt.iscte.poo.sokobanstarter.elementos.Vazio;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;



public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;
	private List<GameElement> elementList;// Lista de imagens
	private List<GameElement> elementUpdate;  
	private Empilhadora bobcat;	        // Referencia para a empilhadora
	private int currentLevel = 0;
	
	private GameEngine() {
		elementList = new ArrayList<>();  
		elementUpdate = new ArrayList<>();
	}

	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}
	
	public void lose() {
		restart();
	}
	
	public void restart() {


			ImageMatrixGUI.getInstance().clearImages();
			elementUpdate = new ArrayList<>();
			elementList = new ArrayList<>(); 
			
			readLevelFromFile(); 
			sendImagesToGUI();
			
			gui.setStatusMessage("Sokoban Starter | Level: "+(currentLevel+1)+"  Battery: "+ bobcat.getBateryLevel()+"%");

			gui.update();
		
	}
	// Inicio
	public void start() throws FileNotFoundException {		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);
		// Criar o cenario de jogo
		readLevelFromFile();
		sendImagesToGUI();
		gui.setStatusMessage("Sokoban Starter | Level: "+(currentLevel+1)+"  Battery: "+ bobcat.getBateryLevel()+"%");
		gui.update();	// 3. registar o objeto ativo GameEngine como observador da GUI
		
		gui.go();                              // 4. lancar a GUI
	}
	
	private void ceckWon() {
		for(GameElement element : elementUpdate) {
			if(element instanceof Alvo) {
				if(!((Alvo) element).getBoxOnTop()) return;
			}	
		}
		currentLevel ++;
		restart();
	}
	
	private void checkIfLost() {
		int boxes = 0;
		int alvo = 0;
		for(GameElement element : elementList) {
			if(element instanceof Alvo) {
				alvo ++;
			}
			if(element instanceof Caixote) {
				boxes ++;
			}	
		}
		if(boxes < alvo) {
			restart();
		}
	}
	
	public void update(Observed source) {
		
		
		int key = gui.keyPressed();   

		if (key == KeyEvent.VK_UP) 
	        bobcat.move(Direction.UP);
	    if (key == KeyEvent.VK_DOWN)
	    	bobcat.move(Direction.DOWN);
	    if (key == KeyEvent.VK_LEFT) 
	    	bobcat.move(Direction.LEFT);
	    if (key == KeyEvent.VK_RIGHT)
	    	bobcat.move(Direction.RIGHT);
	    
		for(GameElement element : elementUpdate) {
			((onUpdateElement) element).elementUpdate();
		}
		ceckWon();
		checkIfLost();
		
		if(key == KeyEvent.VK_R)
			restart();
		
		gui.setStatusMessage("Sokoban Starter | Level: "+(currentLevel+1)+"  Battery: "+ bobcat.getBateryLevel()+"%");
		
		gui.update();
		
	}

	private void readLevelFromFile() {
		File levelsFile = new File("levels/level"+currentLevel+".txt");
		
		int x=0, y=0; //Coordenadas dos objetos
		
		try {
			Scanner scanner = new Scanner(levelsFile);
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] elements = line.split("");
				for(int i = 0; i<elements.length; i++) {
					createWarehouse(elements[i],x,y);
					createMoreStuff(elements[i],x,y);
					x++;
				}
				y++;
				x=0;
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Criacao da planta do armazem - so' chao neste exemplo 
	private void createWarehouse(String element, int x, int y) {
		GameElement gameElement= null;
		switch(element) {
			case "X":
				gameElement = new Alvo(new Point2D(x, y));
				break;
			case "#":
				gameElement = new Parede(new Point2D(x,y));
				break;
			case "=":
				gameElement = new Vazio(new Point2D(x, y));
				break;
			default:
				gameElement = new Chao(new Point2D(x, y));
				break;
				
		}
		elementList.add(gameElement);
		if(gameElement instanceof onUpdateElement)
			elementUpdate.add(gameElement);
		
	}

	private void createMoreStuff(String element, int x, int y) {
		GameElement gameElement = null;
		switch(element) {
			case "E":
				bobcat = new Empilhadora(new Point2D(x, y));
				elementList.add(bobcat);
				break;
			case "C":
				gameElement = ( new Caixote(new Point2D(x, y)));
				break;
			
			case "B":
				gameElement = (new Bateria(new Point2D(x, y)));
				break;
			case "O":
				gameElement = (new Buraco(new Point2D(x, y)));
				break;
			case "P":
				gameElement = (new Palete(new Point2D(x, y)));
				break;
			case "M":
				gameElement = (new Martelo(new Point2D(x, y)));
				break;
			case "%":
				gameElement = (new ParedeRachada(new Point2D(x, y)));
				break;
			case "T":
				gameElement = new Teleporte(new Point2D(x, y));
				break;
		}
		if(gameElement == null) return;
		elementList.add(gameElement);
		if(gameElement instanceof onUpdateElement)
			elementUpdate.add(gameElement);
	}
	
	
	public List<GameElement> getUpdateElmets(){
		return elementUpdate;
	}
	
	
	public void removeGameElement(GameElement element) {

		gui.removeImage(element);
		elementList.remove(element);

	}
	
	public List<GameElement> getGameElement(Point2D point)  {
		List<GameElement> allElements = new ArrayList<>();
		for ( GameElement ele : elementList ) {
			if(ele.getPosition().equals(point)) {
				allElements.add(ele);
			}
		}		
		return allElements; 
	}
	
	public boolean searchElement(List<GameElement> allElements, String name) {
		for ( GameElement ele : allElements ) {
			if(ele.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	private void sendImagesToGUI() {
		
		tileList = new ArrayList<>(); 
		for ( int i = 0; i < elementList.size(); i++ ) {
			tileList.add( elementList.get( i ) );
		}
		gui.addImages(tileList);
	}
}
