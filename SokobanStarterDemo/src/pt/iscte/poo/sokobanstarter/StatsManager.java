package pt.iscte.poo.sokobanstarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class StatsManager {
	   private static final String FILE_PATH_BOARD = "leaderBoard.txt";
	   private static final String FILE_PATH_LAST_LEVEL = "saveUserLevel.txt";
	   
	   //SCORES

	   public static void saveScore(String user, int cScore) {
	       List<Stats> leaderBoard = getLeaderBoard();

	    // Check if user exists
	       for (Stats score : leaderBoard) {
	           if (score.getUser().equals(user)) {
	               // If user exists, only update score if score is higher
	               if (cScore > score.getScore()) {
	                   score.setScore(cScore);
	               }
	               saveScoreInTextFile(leaderBoard); // update file
	               return;
	           }
	       }
	       
	       leaderBoard.add(new Stats(user, cScore));

	       // update file
	       saveScoreInTextFile(leaderBoard);
	   }
	    
	    private static void saveScoreInTextFile(List<Stats> leaderBoard) {
	    	try (PrintWriter writer = new PrintWriter(new File(FILE_PATH_BOARD))) {
	    		for (Stats score : leaderBoard) {
	                writer.println(score.getUser() + "-" + score.getScore());
	            }
	        } catch (IOException e) {
	            System.err.println("Erro ao guardar dados no ficheiro");
	            e.printStackTrace();
	        }
	    }
	    
	    public static void showLeaderBoard() {
	        List<Stats> leaderBoard = getLeaderBoard();
	        
	        Collections.sort(leaderBoard, Comparator.reverseOrder()); //Descending order
	        
	        String content = "Leaderboard \n";
	        
	        int position = 1;

	        for (Stats score : leaderBoard) {
	            content += position+": "+score.getUser()+" - "+score.getScore()+" Points\n";
	            position++;
	        }
	        if(position == 1)content+="No records found";
	        
	        JOptionPane.showMessageDialog(null, content, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
	    }

	    public static List<Stats> getLeaderBoard() {
	        List<Stats> leaderBoard = new ArrayList<>();

	        try (Scanner scanner = new Scanner(new File(FILE_PATH_BOARD))) {
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                String[] parts = line.split("-");
	                if (parts.length == 2) {
	                    String user = parts[0];
	                    int score = Integer.parseInt(parts[1]);
	                    leaderBoard.add(new Stats(user, score));
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Erro ao ler o ficheiro de texto.");
	        }
	        return leaderBoard;
	    }
	    
	    public static int getScoreFromFile(String user) {
	    	List<Stats> scores = getLeaderBoard();
	    	
	    	for(Stats result: scores) {
	    		if(result.getUser().equals(user))
	    			return result.getScore();
	    	}
	    	return 0;
	    }
	    
	    public static void viewLeaderBoard() {
	    	List<Stats> leaderBoard = getLeaderBoard();
	    	Collections.sort(leaderBoard);
	    	int position = 1;
	    	
	    	String content = "Leaderboard: \n";
	    	for(Stats results: leaderBoard) {
	    		content+= position+"º "+results.getUser()+" : "+results.getScore()+" Points\n";
	    		position++;
	    	}
	    	if(position==1) {
	    		content+="Still empty, try beat it :)";
	    	}
	    	
	    	JOptionPane.showMessageDialog(null, content, "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
	    }
	    
	    //LEVELS
	    
	    public static void saveLevel(String user, int level) {
	        List<Stats> saveLevel = getLevel();

	     // Check if user exists
	        for (Stats score : saveLevel) {
	            if (score.getUser().equals(user)) {
	            	if(level > score.getLevel())
	            		score.setLevel(level);
	                saveLevelInTextFile(saveLevel); // update file
	                return;
	            }
	        }
	        
	        saveLevel.add(new Stats(level, user));

	        // update file
	        saveLevelInTextFile(saveLevel);
	    }
	    
	    private static void saveLevelInTextFile(List<Stats> usersLevels) {
	    	try (PrintWriter writer = new PrintWriter(new File(FILE_PATH_LAST_LEVEL))) {
	    		for (Stats score : usersLevels) {
	                writer.println(score.getUser() + "-" + score.getLevel());
	            }
	        } catch (IOException e) {
	            System.err.println("Erro ao guardar dados no ficheiro");
	        }
	    }
	    
	    public static List<Stats> getLevel() {
	        List<Stats> leaderBoard = new ArrayList<>();

	        try (Scanner scanner = new Scanner(new File(FILE_PATH_LAST_LEVEL))) {
	            while (scanner.hasNextLine()) {
	                String line = scanner.nextLine();
	                String[] parts = line.split("-");
	                if (parts.length == 2) {
	                    String user = parts[0];
	                    int level = Integer.parseInt(parts[1]);
	                    leaderBoard.add(new Stats(level, user));
	                }
	            }
	        } catch (IOException e) {
	            System.err.println("Erro ao ler o ficheiro de texto.");
	        }
	        return leaderBoard;
	    }
	    
	    public static int getLevelFromFile(String user) {
	    	List<Stats> levels = getLevel();
	    	
	    	for(Stats result: levels) {
	    		if(result.getUser().equals(user))
	    			return result.getLevel();
	    	}
	    	return 0;
	    }
	    
	    public static void resetAll() {
	    	int selection = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete all data?", "Delete Data",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    	if(selection == JOptionPane.NO_OPTION)
	    		return;
	    	try {
				PrintWriter writer = new PrintWriter(new File(FILE_PATH_LAST_LEVEL));
				writer.println("");
				writer = new PrintWriter(new File(FILE_PATH_BOARD));
				writer.println("");
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    }
	    
	    public static void checkFiles() {
	    	File f = new File(FILE_PATH_BOARD);
	    	try {
				if(f.createNewFile()) {
					f = new File(FILE_PATH_LAST_LEVEL);
					if(f.createNewFile())
						return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
}
