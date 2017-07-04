
import java.util.Scanner;
import java.util.ArrayList;



public class CompetitionProgram {
	
	private Scanner scan;
	private Controler controler;
	private String message;
	private Event event;
	
	public CompetitionProgram(){
		scan = new Scanner(System.in);
		controler = new Controler();
	}
	
	public void runProgram(){
		while(true){
			System.out.println("Command> ");
			String input = normalizeInput(scan.nextLine()).trim();
			
			while (input.length() == 0) {
				System.out.println("The command name can't be empty!");
				System.out.println("Command> ");
				input = scan.nextLine();
			}
			
			String cmd = interpretCommand(input);
			handleCommand(cmd);
		}
	}
	
	public String normalizeInput(String input){
		return input.length() == 0 ? ""  : input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}
	

	
	public String interpretCommand(String input){
		
		ArrayList<Event> eventList = controler.eventList;
		String finalInput = input;
		boolean match = eventList.stream().anyMatch(x -> x.getEventName().equalsIgnoreCase(finalInput));
		 if (match) {
			setEvent(input);
			input = "Event";			
			}
		 
		 if(input.length() < 7){
			 return input;
		 }

		 if(input.substring(0, 7).matches("Message") && input.length() > 7){
			 setMessage(input);
			 input = "Message";
		 }
			 else if(input.substring(0, 7).matches("Message") && input.length() == 7){
				 input = "No message";
		}

		return input;
	}
	
	
	
	public Event setEvent(String eventName){
		event = null;
		
		for(Event e : controler.eventList){
			if(e.getEventName().equalsIgnoreCase(eventName)){
				event = e;
				break;
			}
		}
		return event;
	}
	
	
	//This method takes a string and sets a new string with everything coming after "Message"
	public void setMessage(String msg){	
		int length = msg.length();
		message = msg.substring(7, length).toUpperCase();
		
		if(message.length() > 56){
			message = message.substring(0, 56);
		}
	}
	
	public void printHashtag60(){
		for(int i = 0; i < 60 ; i++){
			System.out.print("#");
		}
		System.out.println("");
	}
	
	public void printHashtagWithSpace(){
		
		char blankrow[] = new char[60];
		blankrow[0] = '#';
		blankrow[59] = '#';
		
		for(int y = 0; y < blankrow.length; y++){
			char c = blankrow[y];
			System.out.print(c);
		}
		
		System.out.println("");
	}
	
	public void printMessage(){
		System.out.print("# ");
		System.out.print(message);
		int msglength = 56 - message.length();
		for(int i = 0; i < msglength; i++){
			System.out.print(' ');
		}
		System.out.println(" #");
	}
	

	
	public void handleCommand(String cmd){
		switch(cmd){
		
		case "Add participant":
			controler.addParticipant(scan);
			break;
			
		case "Remove participant":
			controler.removeParticipant(scan);
			break;

		case "Add event":
			controler.addEvent(scan);
			
			break;
			
		case "Add result":
			controler.addResult(scan);
			break;
			
		case "Participant":
			controler.displayParticipantResults(scan);
			break;
			
		case "Event":
			controler.displayEventResults(event);
			break;
			
		case "Teams":
			controler.displayTeamMedals();
			break;
			
		case "Message": 
			printHashtag60();
			printHashtagWithSpace();
			printMessage();
			printHashtagWithSpace();
			printHashtag60();
			break;
			
		case "No message": 
			System.out.println("There was no message");
			break;
			
		case "Exit":
			System.out.println("Bye bye!");
			System.exit(0);
			break;
			
		default: System.out.println("Wrong command");
		
		}
		
	}
	
	public static void main(String[] args){
		CompetitionProgram cp = new CompetitionProgram();
		cp.runProgram();
	}

}
