import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import csueb.cs401.common.Event;

 public class EventLog {
	
		//private Event event = new Event();;
		private List<Event> events = new LinkedList<>();
		private String filename = "Events.txt";
		
		
		
		//******************************************************
		// Default constructor
		//*******************************************************
		public void EventLog() {
			initList();
		}
		
		
		
		//******************************************************
		// Author: Travis Cassell
		// Saves the event log to a system file
		// Input: none
		// Output: file filename with all events written to OS
		//*******************************************************
		private void Save() {
			if(this.events == null) {
				this.events = new LinkedList<Event>();
			}
			
			else {
				doSaveToFile();
			}

		}
		
		
		
		//******************************************************
		// Author: Travis Cassell
		// Saves the event log to a system file
		// Input: an event
		// Output: file filename with all events written to OS
		//*******************************************************
		private void doSaveToFile() {
			try {
				
				PrintWriter outputFile = new PrintWriter(filename);
					
				for (int i = 0; i < events.size(); i++) {
					
					String temp = i + "," + events.get(i).getEventData() + "\n";
					
					outputFile.print(temp);
				}
				
				outputFile.close();
					
				} catch (Exception e) {
					
					e.printStackTrace();
							            
				}
		}

		
		
		//******************************************************
		// Author: Travis Cassell
		// Reads in events from a system file
		// Input: none
		// Output: events read in from file to arraylist
		//*******************************************************
		public ArrayList<String> loadData() {

			ArrayList<String> readEvents = new ArrayList<String>();
			String sourceName = filename;
			File file = new File(sourceName);
			
			if (!file.exists()) {
				
				System.out.println("Error!");
				
			}
			
			try {
				
				Scanner inputFile = new Scanner(file);		
				
				while (inputFile.hasNextLine()) {
					
					String line = inputFile.nextLine();
					readEvents.add(line);

				}
				
				inputFile.close();
				
			} catch (Exception e) {
				
				e.printStackTrace();
	            
			}
			
			return readEvents;
			
		}

		

		//******************************************************
		// Author: Bryan Graves
		// Adds an event to the event log
		// Input: string event description and string userID
		// Output: eventlog adds a listing
		//*******************************************************
		public void addEvent(String inDescription, String inUser) {
			Event temp = new Event(inDescription,inUser);
			if(this.events.isEmpty()) this.initList();
			if(!this.events.add(temp)) System.out.println("Cannot add event\n");
			else {
				//this.events.sort(null);
				this.Save();
			}
		}
		
		
		
		//******************************************************
		// Author: Bryan Graves
		// Finds events in the log within the range
		// Input: string start date and end date null null 
		// retrieves all events
		// Output: string all events between those dates
		//*******************************************************
		public String findEvents(String startDate, String endDate) {
			String foundEvents = new String();
			for(int i=0; i<this.events.size();i++) {
			//	if(this.events.get(i).) {
			//		Date
			//	}
			}
			
			return foundEvents;
		}
		
		
		
		//******************************************************
		// Author: Bryan Graves
		// Loads event log with previous events
		// Input: file with saved events
		// Output: loaded event log
		//*******************************************************
		private void initList() {
			ArrayList<String> inList = new ArrayList<>();
			inList = loadData();
			
			for(int i = 0; i < inList.size(); i++) {
				 String[] temp = inList.get(i).split(",");
				// String[] inDesc = temp[1];
				// String[] inUser = temp[2];
				 Date inDate = toDate(temp[3]);
			
			}
		}

		
		
		//******************************************************
		// Author: Bryan Graves
		// Loads event log with previous events
		// Input: file with saved events
		// Output: loaded event log
		//*******************************************************
		private Date toDate(String inDate) {
			
			 SimpleDateFormat sdt = new SimpleDateFormat("dd-MM-YYYY");
			 
			 String[] inDateStr = inDate.split(" ");
			
			Date outDate = new Date();
			return outDate;
		}
}
