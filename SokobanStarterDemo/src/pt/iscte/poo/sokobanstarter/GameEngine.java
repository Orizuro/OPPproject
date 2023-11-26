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
import pt.iscte.poo.utils.Point2D;



public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui;  		// Referencia para ImageMatrixGUI (janela de interface com o utilizador) 
	private List<ImageTile> tileList;
	private List<GameElement> elementList;// Lista de imagens
	private Empilhadora bobcat;	        // Referencia para a empilhadora

	private int currentLevel = 0;
	private int batteryLevel;

	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		elementList = new ArrayList<>();   
	}


	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE==null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Inicio
	public void start() throws FileNotFoundException {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que ser feitos!
		
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);            // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go();                              // 4. lancar a GUI

		
		// Criar o cenario de jogo
		readLevelFromFile();
		//createWarehouse();      // criar o armazem
		//createMoreStuff();      // criar mais algun objetos (empilhadora, caixotes,...)
		sendImagesToGUI();      // enviar as imagens para a GUI

		
		// Escrever uma mensagem na StatusBarpackage pt.iscte.poo.sokobanstarter;

		gui.setStatusMessage("Sokoban Starter - demo  Battery: "+ batteryLevel);
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed();   

		if (key == KeyEvent.VK_UP) 
	        bobcat.moveUP();
	    if (key == KeyEvent.VK_DOWN)
	        bobcat.moveDown();
	    if (key == KeyEvent.VK_LEFT) 
	        bobcat.moveLeft();
	    if (key == KeyEvent.VK_RIGHT)
	        bobcat.moveRight();

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
					if(elements[i].contains("#") || elements[i].contains("=") || elements[i].contains(" "))
						createWarehouse(elements[i], x, y);
					else if(elements[i].contains("C") ||elements[i].contains("X") ||elements[i].contains("E") ||elements[i].contains("B") ||elements[i].contains("T") ||elements[i].contains("O") ||elements[i].contains("P") ||elements[i].contains("M") ||elements[i].contains("%"))
						createMoreStuff(elements[i], x, y);
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
		//System.out.println("Elemento: "+elements+" x="+x+" y="+y); //debug
		
		switch(element) {
			case "#":
				elementList.add(new Parede(new Point2D(x,y)));
				break;
			case "=":
				elementList.add(new Vazio(new Point2D(x, y)));
				break;
			case " ":
				elementList.add(new Chao(new Point2D(x, y)));
				break;
		}
		
		//Exemplo fornecido
		/*for (int y=0; y<GRID_HEIGHT; y++)
			for (int x=0; x<GRID_HEIGHT; x++)
				tileList.add(new Chao(new Point2D(x,y)));*/	
	}

	// Criacao de mais objetos - neste exemplo e' uma empilhadora e dois caixotes
	private void createMoreStuff(String element, int x, int y) {
		//System.out.println("Elemento: "+elements+" x="+x+" y="+y); //debug
		
		switch(element) {
			case "E":
				elementList.add(new Chao(new Point2D(x, y)));
				bobcat = new Empilhadora(new Point2D(x, y), INSTANCE);
				elementList.add(bobcat);
				break;
			case "C":
				elementList.add(new Chao(new Point2D(x, y)));
				elementList.add(new Caixote(new Point2D(x, y),INSTANCE));
				break;
			case "X":
				elementList.add(new Alvo(new Point2D(x, y)));
				break;
			case "B":
				elementList.add(new Bateria(new Point2D(x, y)));
				break;
			case "O":
				elementList.add(new Buraco(new Point2D(x, y)));
				break;
			case "P":
				elementList.add(new Palete(new Point2D(x, y)));
				break;
			case "M":
				elementList.add(new Martelo(new Point2D(x, y)));
				break;
			case "%":
				elementList.add(new ParedeRachada(new Point2D(x, y)));
				break;
			case "T":
				elementList.add(new Teleporte(new Point2D(x, y)));
				break;
		}
		
	}
	public void removeGameElement(Point2D point, int layer) {
		int ind = elementList.indexOf(getGameElement(point, layer));
		
		elementList.remove(ind);
		gui.removeImage(tileList.get(ind));

	}
	
	public GameElement getGameElement(Point2D point, int layer)  {
		for ( GameElement ele : elementList ) {
			if(ele.getPosition().equals(point) && ele.getLayer() == layer) {
				return ele;
			}
		}		
		return null; 
	}

	private void sendImagesToGUI() {
		
		tileList = new ArrayList<>(); 
		for ( int i = 0; i < elementList.size(); i++ ) {
			tileList.add( elementList.get( i ) );
		}
		gui.addImages(tileList);
	}
}
