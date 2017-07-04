
public class Result {
	
	private double result;
	private Participant participant;
	private Event event;
	
	public Result(double result, Participant participant, Event event){
		this.result = result;
		this.participant = participant;
		this.event = event;
	}
	
	public double getResult(){
		return result;
	}
	
	public Participant getParticipant(){
		return participant;
	}
	
	public Event getEvent(){
		return event;
	}
	

}
