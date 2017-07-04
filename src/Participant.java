import java.util.ArrayList;
import java.util.HashMap;

public class Participant {
	

	private HashMap<String, ArrayList<Result>> participantResults = new HashMap<>();
	private String firstName;
	private String lastName;
	private String team;
	private int number;
	
	public Participant(String firstName, String lastName, String team, int number){	
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		this.number = number;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getTeam(){
		return team;
	}
	
	public int getId(){
		return number;
	}
	
	public void addResult(String event, Result result){
		ArrayList<Result> resultsInEvent = participantResults.get(event);
		if(resultsInEvent == null){
			resultsInEvent = new ArrayList<>();
			participantResults.put(event, resultsInEvent);
		}
		resultsInEvent.add(result);
	}
	
	public HashMap<String, ArrayList<Result>> getParticipantResults(){
		return participantResults;
	}
	


}
