import java.util.ArrayList;

public class Event {
	
	private String eventName;
	private int attempts;
	private ArrayList<Result> eventResults = new ArrayList<>();
	
	public Event(String eventName, int attempts){
		this.eventName = eventName;
		this.attempts = attempts;
	}
	
	public String getEventName(){
		return eventName;
	}
	
	public int getAttemptsAllowed(){
		return attempts;
	}
	
	public void addEventResult(Result r){
		eventResults.add(r);
	}
	
	public ArrayList<Result> getResultList(){
		return eventResults;    
	}
	
	public Result[] selectionSort(){
		Result[] resultsInEvent = eventResults.toArray(new Result[eventResults.size()]);
		
		for(int i = 0; i < resultsInEvent.length -1 ; i++){
			double currentMax = resultsInEvent[i].getResult();
			int currentMaxIndex = i;
			
			for(int j = i + 1; j < resultsInEvent.length ; j++){
				if(currentMax < resultsInEvent[j].getResult()){
					currentMax = resultsInEvent[j].getResult();
					currentMaxIndex = j;
				}
			}
			if(currentMaxIndex != i){
				Result temp = resultsInEvent[currentMaxIndex];
				resultsInEvent[currentMaxIndex] = resultsInEvent[i];
				resultsInEvent[i] = temp;
			}
		}
		return resultsInEvent;
	}

}
