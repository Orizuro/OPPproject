package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

public class LevelLoader {
	List<GameElement> elementList;
	private List<GameElement> alvoList;
	 public  List<GameElement> loadLevelFromFile(int levelNumber) {
		File levelsFile = new File("levels/level"+levelNumber+".txt");
		
		int x=0, y=0; //Coordenadas dos objetos
		
		try (Scanner scanner = new Scanner(levelsFile)){
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] elements = line.split("");
				for(int i = 0; i<elements.length; i++) {
					
					if(createWarehouse(elements[i],x,y))elementList.add(new Chao(new Point2D(x, y)));
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
		return elementList;
	}

	boolean createWarehouse(String element, int x, int y) {
		//System.out.println("Elemento: "+elements+" x="+x+" y="+y); //debug
		boolean result = true;
		switch(element) {
			case "X":
				GameElement alvo = new Alvo(new Point2D(x, y));
				elementList.add(alvo);
				alvoList.add(alvo);
				result = false;
				break;
			case "#":
				elementList.add(new Parede(new Point2D(x,y)));
				result = false;
				break;
			case "=":
				elementList.add(new Vazio(new Point2D(x, y)));
				result = false;
				break;
		}
		return result;

	}

	// Criacao de mais objetos - neste exemplo e' uma empilhadora e dois caixotes
	private void createMoreStuff(String element, int x, int y) {
		Empilhadora bobcat;
		//System.out.println("Elemento: "+elements+" x="+x+" y="+y); //debug
		switch(element) {
			case "E":
				bobcat = new Empilhadora(new Point2D(x, y));
				elementList.add(bobcat);
				break;
			case "C":
				elementList.add( new Caixote(new Point2D(x, y)));
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
				GameElement temp = new Teleporte(new Point2D(x, y));
				List<GameElement> teleportList = new ArrayList<GameElement>();
				teleportList.add( temp);
				elementList.add(temp);
				break;
		}
		
	}
}
