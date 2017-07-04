
public class Team {
	
	private String teamName;
	private int gold = 0;
	private int silver = 0;
	private int bronze = 0;
	
	public Team(String teamName){
		this.teamName = teamName;
	}
	
	public void setGold(){
		gold++;
	}
	
	public void setSilver(){
		silver++;
	}
	
	public void setBronze(){
		bronze++;
	}
	
	public void resetMedals(){
		gold = 0;
		silver = 0;
		bronze = 0;
	}
	
	public int getGold(){
		return gold;
	}
	
	public int getSilver(){
		return silver;
	}
	
	public int getBronze(){
		return bronze;
	}
	
	public String getTeamName(){
		return teamName;
	}
	
	
	
	
	
	

}
