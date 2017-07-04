import java.util.*;

public class Controler {

    //todo: nullpointerexception när man försöker ta bort när det inte finns några kvar i listan ...

	private int participantID = 99;

	private ArrayList<Participant> participantList = new ArrayList<>();
	public ArrayList<Event> eventList = new ArrayList<>();
	private ArrayList<Team> teams = new ArrayList<>();
	private ArrayList<Result> resultList = new ArrayList<>();
	private HashMap<String, ArrayList<Participant>> teamList = new HashMap<>();

	public void addParticipant(Scanner scan) {
		String firstName = readInput(scan, "First name: ");
		String lastName = readInput(scan, "Last name: ");
		String team = readInput(scan, "Team: ");

		Participant p = new Participant(firstName, lastName, team, createParticipantID());

		participantList.add(p);
		addToTeam(team, p);
		System.out.println(firstName + " " + lastName + " from " + team + " with number " + p.getId() + " added");
	}

	public void addToTeam(String team, Participant p) {

		ArrayList<Participant> teamMembers = teamList.get(team);
		if (teamMembers == null) {
			teamMembers = new ArrayList<>();
			teamMembers.add(p);
			Team t = new Team(team);
			teams.add(t);
		}
		teamList.put(team, teamMembers);
	}

	public int createParticipantID() {
		participantID++;
		return participantID;
	}

	public Participant findParticipant(int participant) {
		for (Participant p : participantList) {
			if (p.getId() == participant) {
				return p;
			}
		}
		return null;
	}

	public Event findEvent(String event) {
		for (Event e : eventList) {
			if (e.getEventName().equals(event)) {
				return e;
			}
		}
		return null;
	}

	public void addEvent(Scanner scan) {
		String eventName = readInput(scan, "Event name: ");
		int attempts = scanInteger(scan, "Attempts allowed: ");
		Event e = new Event(eventName, attempts);
		eventList.add(e);
		System.out.println(eventName + " added");
	}

	public void addResult(Scanner scan) {
		int participant = scanInteger(scan, "Number: ");
		Participant p = findParticipant(participant);
		while (p == null) {
			participant = scanInteger(scan, "Number: ");
			p = findParticipant(participant);
		}

		String event = readInput(scan, "Event: ");
		Event e = findEvent(event);
		while (e == null) {
			event = readInput(scan, "Event: ");
			e = findEvent(event);
		}

		List<Result> ls = p.getParticipantResults().get(event);
		if(ls != null){
            if(ls.size() == e.getAttemptsAllowed()){
                System.out.println(p.getFirstName() + " " + p.getLastName() + " from " + p.getTeam() + " had already made " + e.getAttemptsAllowed() + " in " + e.getEventName());
                return;
            }
		}

		double results = scanDouble(scan, "Results for " + p.getFirstName() + " " + p.getLastName() + " from "
				+ p.getTeam() + " in " + event + ": ");
		Result r = new Result(results, p, e);
		resultList.add(r);
		e.addEventResult(r); // add result to Event class
		p.addResult(event, r); // Add result to resultList in Participant class
	}

	public void displayParticipantResults(Scanner scan) {
		int participant = scanInteger(scan, "Number: ");
		Participant p = findParticipant(participant);
		while (p == null) {
			participant = scanInteger(scan, "Number: ");
			p = findParticipant(participant);
		}

		HashMap<String, ArrayList<Result>> map = p.getParticipantResults();

		for (Map.Entry<String, ArrayList<Result>> entry : map.entrySet()) {
			String eventName = entry.getKey();
			ArrayList<Result> result = entry.getValue();
			System.out.print("Results for " + p.getFirstName() + " " + p.getLastName() + " in " + eventName + ": ");
			for (Result r : result) {
				System.out.print(r.getResult() + " ");
			}
			System.out.println("");
		}
	}

	public void displayEventResults(Event eventName) {

		if(eventName.getResultList().size() == 0){
			System.out.println("No results registered for this event.");
			return;
		}

		List<Result> sortedList = Arrays.asList(eventName.selectionSort());

		System.out.println("Results for " + eventName.getEventName());

		double currentMax = sortedList.get(0).getResult();
		int currentPos = 1;
		int numberOf = 0;
		for(int i = 0; i < sortedList.size(); i++){
			numberOf++;
			if(sortedList.get(i).getResult() == currentMax){
				System.out.println(currentPos + " " + currentMax + " " + sortedList.get(i).getParticipant().getFirstName() + " " +
				sortedList.get(i).getParticipant().getLastName());
			}else{
				currentPos = numberOf;
				currentMax = sortedList.get(i).getResult();
				System.out.println(currentPos + " " + currentMax + " " + sortedList.get(i).getParticipant().getFirstName() + " " +
						sortedList.get(i).getParticipant().getLastName());

			}

		}
	}

	public void removeParticipant(Scanner scan) {
		//tar bort laget då den första lagregistrerade tas bort även om det finns fler lagdeltagare

		Participant pnt = null;
		int participant = scanInteger(scan, "Number: ");
		for (Participant p : participantList) {
			if (p.getId() == participant) {
				pnt = p;
			}
		}

		if (pnt == null) {
			System.out.println("No participant with number " + participant + " exists");
		} else {
			participantList.remove(pnt);

			ArrayList<Participant> toRemove = new ArrayList<>();
			for (Participant p : teamList.get(pnt.getTeam())) {
				if (p.getId() == pnt.getId()) {
					toRemove.add(p);
				}
			}
			teamList.get(pnt.getTeam()).removeAll(toRemove);



			if (teamList.get(pnt.getTeam()).size() == 0) {
				teamList.remove(pnt.getTeam());
				Team team = null;
				for(Team t : teams){
				    if(pnt.getTeam().equalsIgnoreCase(t.getTeamName())){
				        team = t;
                    }
                }
				teams.remove(team);
			}

			ArrayList<Result> res = new ArrayList<>();
			for(Result r : resultList){
			    if(pnt == r.getParticipant()){
			        res.add(r);
                }
            }
            resultList.removeAll(res);

            ArrayList<Result> res2 = new ArrayList<>();
            for(Event e: eventList){
                for(Result r : e.getResultList()){
                    if(pnt == r.getParticipant()){
                        res2.add(r);
                    }
                }
                e.getResultList().removeAll(res2);
                res2.clear();
            }
		}

		System.out.println("Removed");
	}

	public boolean setMedal(String team) {
		for (Team t : teams) {
			if (team.equalsIgnoreCase(t.getTeamName())) {
				return true;
			}
		}
		return false;
	}

	public void setGold(String team) {

		for (Team t : teams) {
			if (team.equalsIgnoreCase(t.getTeamName())) {
				t.setGold();
			}
		}
	}

	public void setSilver(String team) {

		for (Team t : teams) {
			if (team.equalsIgnoreCase(t.getTeamName())) {
				t.setSilver();
			}
		}
	}

	public void setBronze(String team) {

		for (Team t : teams) {
			if (team.equalsIgnoreCase(t.getTeamName())) {
				t.setBronze();
			}
		}
	}

	public void decideMedals(List<Result> list) {
		if(list.size() == 0){
			return;
		}
		String team;
		double currentMax = list.get(0).getResult();
		int currentLoop = 1;

		for (int i = 0; i < list.size(); i++) {
			team = list.get(i).getParticipant().getTeam();
			if (list.get(i).getResult() == currentMax) {
				if (currentLoop == 1) {
					setGold(team);
				} else if (currentLoop == 2) {
					setSilver(team);
				} else if (currentLoop == 3) {
					setBronze(team);
				}
			} else {
				currentMax = list.get(i).getResult();
				currentLoop++;
				if (currentLoop == 2) {
					setSilver(team);
				} else if (currentLoop == 3) {
					setBronze(team);
				} else if (currentLoop == 4) {
					return;
				}
			}
		}
	}

	public ArrayList<Team> putTeamsInMedalList() {

		for (Event e : eventList) {
			List<Result> sortedList = Arrays.asList(e.selectionSort());
			decideMedals(sortedList);

			for(Result r : sortedList){
				System.out.println(r.getResult());
			}
		}

		ArrayList<Team> goldList = new ArrayList<>();
		ArrayList<Team> silverList = new ArrayList<>();
		ArrayList<Team> bronzeList = new ArrayList<>();
		ArrayList<Team> alphaList = new ArrayList<>();

		for (Team t : teams) {
			int gold = t.getGold();
			int silver = t.getSilver();
			int bronze = t.getBronze();

			if (gold > 0) {
				goldList.add(t);
			} else if (silver > 0) {
				silverList.add(t);
			} else if (bronze > 0) {
				bronzeList.add(t);
			} else
				alphaList.add(t);
		}

		ArrayList<Team> sortedList = new ArrayList<>();

		goldList.sort(Comparator.comparingInt(Team::getGold));
		silverList.sort(Comparator.comparingInt(Team::getSilver));
		bronzeList.sort(Comparator.comparingInt(Team::getBronze));

		alphaList.sort(Comparator.comparing(Team::getTeamName));

		sortedList.addAll(goldList);
		sortedList.addAll(silverList);
		sortedList.addAll(bronzeList);
		sortedList.addAll(alphaList);

		return sortedList;
	}

	public void displayTeamMedals() {
		for (Team t : teams) {
			t.resetMedals();
		}

		ArrayList<Team> medalList = putTeamsInMedalList();
		System.out.println("1st" + "    2nd" + "    3rd" + "    Team name");
		System.out.println("************************************");
		System.out.println("");
		for (Team t : medalList) {
			String teamName = t.getTeamName();
			int gold = t.getGold();
			int silver = t.getSilver();
			int bronze = t.getBronze();
			System.out.println(gold + "      " + silver + "      " + bronze + "      " + teamName);
		}
	}

	public String readInput(Scanner scan, String question) {

		String str;

		while (true) {
			System.out.println(question);
			try {
				str = normalizeInput(scan.nextLine()).trim();
				if (str.isEmpty()) {
					System.out.println("Names can't be empty");
				} else
					break;

			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Names can't be empty");
			}
		}
		return str;
	}

	public int scanInteger(Scanner scan, String output) {
		int attempts;

		while (true) {
			System.out.println(output);
			String number = scan.nextLine();
			try {
				attempts = Integer.parseInt(number);
				if (attempts > 0) {
					break;
				}
			} catch (NumberFormatException nfe) {
				System.out.println("Not a valid number!");
			}
		}
		return attempts;
	}

	public double scanDouble(Scanner scan, String output) {
		double number;

		while (true) {
			System.out.println(output);
			String numberString = scan.nextLine();
			try {
				number = Double.parseDouble(numberString);
				if (number < 0) {
					System.out.println("Must be greater than or equal to zero!");
				} else
					break;
			} catch (NumberFormatException nfe) {
				System.out.println("Not a valid number!");
			}
		}
		return number;
	}

	public String normalizeInput(String input) {
		return input == null || input.length()== 0 ? "" : input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
	}

}
