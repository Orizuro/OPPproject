package pt.iscte.poo.sokobanstarter;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	private String currentUser = " ";
	
	Stats stats;
	
	private GameEngine() {
		elementList = new ArrayList<>();  
		elementUpdate = new ArrayList<>();
	}


	// Implementacao do singleton para o GameEngine
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
	public void start() {
		StatsManager.checkFiles();
		startNewGameDialog();
	}
	
	void startNewGameDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Welcome to Java Sokoban!");

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Main Menu");
        titlePanel.add(titleLabel);

        dialog.add(titlePanel, BorderLayout.NORTH);

        JButton button1 = new JButton("Start!");
        JButton button2 = new JButton("Leaderboard");
        JButton button3 = new JButton("Reset Data");
        JButton button4 = new JButton("Exit Game");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dialog.dispose();
            	String userName = "";
            	while(userName.trim().equals("")) {
        			userName = JOptionPane.showInputDialog("Type your username");
        			
        			if(userName == null) { //Cancel button
        				startNewGameDialog();
        				return;
        			}
        			
        			if(userName.trim().equals(""))
        				JOptionPane.showMessageDialog(null, "You have to enter a valid username!", "Wrong input", JOptionPane.ERROR_MESSAGE);
        			else {
        				createUser(userName);
        				if(currentLevel == 0)rules();
        				startGame();
        				return;
        			}
        		}
            }
        });
        
        button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StatsManager.showLeaderBoard();
			}
        });
        
        button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StatsManager.resetAll();
			}
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    	    	if(selection == JOptionPane.YES_OPTION)
    	    		System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Espaçamento entre os botões
        buttonPanel.add(button1, gbc);

        gbc.gridy = 1;
        buttonPanel.add(button2, gbc);
        
        gbc.gridy = 2;
        buttonPanel.add(button3, gbc);
        
        gbc.gridy = 3;
        buttonPanel.add(button4, gbc);

        dialog.add(buttonPanel, BorderLayout.CENTER);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(null); // Centers dialog
        dialog.setVisible(true);
        dialog.setDefaultCloseOperation(0);
	}
	
	void createUser(String user) {
		currentUser = user;
		if(StatsManager.getLevelFromFile(user) != 0) {
			int score = StatsManager.getScoreFromFile(user);
			currentLevel = StatsManager.getLevelFromFile(user)-1;
			stats = new Stats(user, score);
		} else 
			stats = new Stats(user, 0);
	}
	
	void startGame() {
		gui = ImageMatrixGUI.getInstance();    // 1. obter instancia ativa de ImageMatrixGUI	
		gui.setSize(GRID_HEIGHT, GRID_WIDTH);  // 2. configurar as dimensoes 
		gui.registerObserver(this);
		// Criar o cenario de jogo
		readLevelFromFile();
		sendImagesToGUI();
		gui.setStatusMessage("Sokoban Starter | Level: "+(currentLevel+1)+" | Battery: "+ bobcat.getBateryLevel()+"% | Score: " + stats.getScore());
		gui.update();	// 3. registar o objeto ativo GameEngine como observador da GUI
		
		gui.go();                              // 4. lancar a GUI
	}
	
	private void checkIfWon() {
		for(GameElement element : elementUpdate) {
			if(element instanceof Alvo) {
				if(!((Alvo) element).getBoxOnTop()) return;
			}	
		}
		
		if(currentLevel == 6) {
			JOptionPane.showMessageDialog(null, "Congratulations! You complete the game!", "Level Complete", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		JOptionPane.showMessageDialog(null, "Congratulations! You passed the level!", "Level Complete", JOptionPane.INFORMATION_MESSAGE);
		currentLevel ++;
		updateScore();
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
			JOptionPane.showMessageDialog(null, "You droped a box!", "Out of Boxes", JOptionPane.ERROR_MESSAGE);
			restart();
		}
		if(bobcat.getBateryLevel()==0) {
			JOptionPane.showMessageDialog(null, "You ran ou of battery!", "Out of Battery", JOptionPane.ERROR_MESSAGE);
			restart();
		}
	}
	
	public void updateScore() {
		//System.out.println(currentUser);
		stats.scorePontuation(bobcat.getBateryLevel(), currentLevel);
		StatsManager.saveScore(currentUser, stats.getScore());
		StatsManager.saveLevel(currentUser, currentLevel+1);
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
		
		
		if(key == KeyEvent.VK_R) {
				if(stats.getScore()>0)stats.setScore(stats.getScore()-30);
	    		restart();
		}
		
		gui.setStatusMessage("Sokoban Starter | Level: "+(currentLevel+1)+" | Battery: "+ bobcat.getBateryLevel()+"% | Score: " + stats.getScore());
		
		gui.update();
		checkIfWon();
		checkIfLost();
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
	
	private void rules() {
		String content="Guide:\n\n"
				+ "You need to get all the boxes on the target 'x'\n"
				+ "Your Forklift has battery, use it efficiently\n"
				+ "Watch out for the holes in the ground. If you drop one box into the hole you lose\n"
				+ "Some levels have addons to pick up:\n"
				+ "-Battery: Recharge the Forklift\n"
				+ "-Hammer: Can break cracked walls\n"
				+ "-Pallet: can fill holes\n"
				+ "-Teleport: takes you or your boxes to the location of the other teleporter\n"
				+ "You can restart the current level that yo are playing by pressing the letter 'R' (but you might loose points)\n\n"
				+ "And most importantly... Have FUN!!";
		JOptionPane.showMessageDialog(null, content, "The Basics", JOptionPane.INFORMATION_MESSAGE);
	}
}
