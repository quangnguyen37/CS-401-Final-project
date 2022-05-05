
import Main.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EventTest {

	@Test
	void testCreateEvent() {
		Event test = new Event("New Event","Bryan");
		String out = test.getEvent();
		String[] check = out.split(",");
		assertEquals("Desc:New Event", check[0]);
		assertEquals(" User:Bryan", check[1]);	
		assertNotEquals(null, check[2]);
	}

	@Test
	void testGetEvent() {
		Event test = new Event("New Event","Bryan");
		String out = test.getEvent();
		String[] check = out.split(",");
		assertEquals("Desc:New Event", check[0]);
		assertEquals(" User:Bryan", check[1]);	
		assertNotEquals(null, check[2]);
	}

	@Test
	void testGetEventData() {
		Event test = new Event("New Event","Bryan");
		String out = test.getEvent();
		String[] check = out.split(",");
		assertEquals("Desc:New Event", check[0]);
		assertEquals(" User:Bryan", check[1]);	
		assertNotEquals(null, check[2]);
	}

	@Test
	void testDeleteEvent() {
		Event test = new Event("New Event","Bryan");
		test.deleteEvent();
		String out = test.getEvent();
		String[] check = out.split(",");
		assertEquals("Desc:", check[0]);
		assertEquals(" User:", check[1]);	
		assertNotEquals(null, check[2]);
	}

	@Test
	void testOverwriteEvent() {
		Event test = new Event("New Event","Bryan");
		Date d = new Date();
		test.overwriteEvent("New Name","Judy",d);
		String out = test.getEvent();
		String[] check = out.split(",");
		assertEquals("Desc:New Name", check[0]);
		assertEquals(" User:Judy", check[1]);	
		assertNotEquals(null, check[2]);
	}

	@Test
	void testGetDate() {
		Event test = new Event("New Event","Bryan");
		assertNotEquals(null, test.getDate());
	}

}
