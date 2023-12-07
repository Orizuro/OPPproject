package pt.iscte.poo.sokobanstarter;

public class Stats implements Comparable<Stats>{
	
	private String user;
	private int score;
	private int level;
	
	public Stats(String user, int score) {
		this.user = user;
		this.score = score;
	}
	
	public Stats(int level, String user) {
		this.user=user;
		this.level=level;
	}

	public String getUser() {
		return user;
	}

	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int scorePontuation(int energy, int level) {
		score+=(energy*5) + (level*10);
		return score;
	}
	
    @Override
    public int compareTo(Stats score2) {
        return Integer.compare(score2.score, this.score);
    }
}
