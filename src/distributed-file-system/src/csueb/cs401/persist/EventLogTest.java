
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Main.EventLog;

class EventLogTest {

	@Test
	void testLoadData() {
		EventLog test = new EventLog();
		test.addEvent("Event1", "User1");
		assertNotEquals(null, test.findEvents("", ""));
		
	}

	@Test
	void testAddEvent() {
		EventLog test = new EventLog();
		test.addEvent("Event1", "User1");
		assertNotEquals(null, test.findEvents("", ""));
	}

}
