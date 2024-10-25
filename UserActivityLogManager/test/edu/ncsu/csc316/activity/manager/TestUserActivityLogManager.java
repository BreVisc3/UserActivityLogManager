package edu.ncsu.csc316.activity.manager;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.activity.dsa.DataStructure;

public class TestUserActivityLogManager {
	
	private UserActivityLogManager def;
	private UserActivityLogManager type;
	
	@Before
	public void setUp() {
		def = new UserActivityLogManager("LOGIN1.TXT");
		type = new UserActivityLogManager("LOGIN2.TXT", DataStructure.UNORDEREDLINKEDMAP);
	}
	
	@Test
	public void TestGetEntriesByDate() {
		
	}

	@Test
	public void TestGetEntriesByHour() {
		
	}
	
	@Test
	public void TestGetTopActivities() {
		
	}
	
	@Test
	public void TestGetActionList() {
		
	}
	
	@Test
	public void TestGetReturnArray() {
		
	}
	
	@Test
	public void TestGetLargest() {
		
	}
}
