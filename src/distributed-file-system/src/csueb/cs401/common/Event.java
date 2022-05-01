

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {

	private Date eventDate;
	private String eventDescription;
	private String userID;
	
	
	
	//******************************************************
	// Default constructor
	//*******************************************************
	public Event() {
		eventDate = new Date();
		eventDescription = new String("");
		userID = new String("");
	}
	
	
	
	
	//******************************************************
	// Creates an event with the description and user given
	// Input: string description of event and string userID 
	// Output: an event set to current date and time with
	// values specified.
	//*******************************************************
	public Event(String inDescription, String inUser) {
		eventDate = new Date();
		this.eventDescription = inDescription;
		this.userID = inUser;
	}
	
	
	
	//******************************************************
	// Creates an event with the description and user given
	// Input: string description of event and string userID 
	// Output: bool weather event was created and an event 
	// set to current date and time with values specified.
	//*******************************************************
	public boolean createEvent(String inDescription, String inUser) {
		eventDate = new Date();
		this.eventDescription = inDescription;
		this.userID = inUser;
		
		return true;
	}
	
	
	
	//******************************************************
	// Returns event in string format
	// Input: none 
	// Output: string Description, User, Date and Time
	//*******************************************************
	public String getEvent() {
		String output = new String();
		
        // format to date object
        DateFormat df = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        // Convert the date into a
        // string using format() method
        String dateToString = df.format(eventDate);
        
		output = "Desc:" + this.eventDescription + ", User:" +
				userID + ", Date:" + dateToString;
		
		return output;
	}
	
	
	//******************************************************
	// Returns event in string format
	// Input: none 
	// Output: string Description, User, Date and Time
	//*******************************************************
	public String getEventData() {
		String output = new String();
        
		output = this.eventDescription + "," +
				this.userID + "," + this.eventDate.toInstant();
		
		return output;
	}
	
	
	
	//******************************************************
	// Deletes event information
	// Input: none 
	// Output: boolean true if info was cleared, date to time
	// of clearing
	//*******************************************************
	public boolean deleteEvent() {
		eventDate = new Date();
		eventDescription = new String("");
		userID = new String("");
		
		return true;
	}
	
	
	
	//******************************************************
	// overwrites and event event information
	// Input: none 
	// Output: boolean true if info was cleared, date to time
	// of clearing
	//*******************************************************
	public boolean overwriteEvent(String newDescription, String newUser, Date newDate) {
		eventDate = newDate;
		this.eventDescription = newDescription;
		this.userID = newUser;
		
		return true;
	}
	
	
	//******************************************************
	// overwrites and event event information
	// Input: none 
	// Output: boolean true if info was cleared, date to time
	// of clearing
	//*******************************************************
	public String getDate() {
		String date = new String();
		
		return date;
	}
}
