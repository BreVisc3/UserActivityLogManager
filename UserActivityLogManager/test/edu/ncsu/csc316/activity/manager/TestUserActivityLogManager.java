package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.activity.dsa.DSAFactory;
import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;

public class TestUserActivityLogManager {
	
	private UserActivityLogManager def;
	private UserActivityLogManager type;
	private Map<String, List<LogEntry>> map;
	private Map<Integer, List<LogEntry>> hourMap;
	private List<String> list;
	
	@Before
	public void setUp() {
		def = new UserActivityLogManager("LOGIN1.TXT");
		type = new UserActivityLogManager("LOGIN2.TXT", DataStructure.UNORDEREDLINKEDMAP);
		
		map = DSAFactory.getMap(null);
		hourMap = DSAFactory.getMap(null);
		list = DSAFactory.getIndexedList();
	}
	
	@Test
	public void TestGetEntriesByDate() {
		
		map = def.getEntriesByDate();
		assertEquals(map.size(), 16);
		
		map = type.getEntriesByDate();
		assertEquals(map.size(), 0);
	}

	@Test
	public void TestGetEntriesByHour() {
		
		hourMap = def.getEntriesByHour();
		assertEquals(hourMap.size(), 14);
		
		hourMap = type.getEntriesByHour();
		assertEquals(hourMap.size(), 0);
		
	}
	
	@Test
	public void TestGetTopActivities() {
		
		list = def.getTopActivities(17);
		assertEquals(list.size(), 4);
		assertEquals(list.get(0), "13: sort HL7 Code 422");
		assertEquals(list.get(1), "2: print office visit OV02132");
		assertEquals(list.get(2), "1: unmerge notification NX1115");
		assertEquals(list.get(3), "1: view HL7 Code 422");
		
		list = def.getTopActivities(20);
		assertEquals(list.size(), 4);
		assertEquals(list.get(0), "13: sort HL7 Code 422");
		assertEquals(list.get(1), "2: print office visit OV02132");
		assertEquals(list.get(2), "1: unmerge notification NX1115");
		assertEquals(list.get(3), "1: view HL7 Code 422");
		
		list = def.getTopActivities(16);
		assertEquals(list.size(), 3);
		assertEquals(list.get(0), "13: sort HL7 Code 422");
		assertEquals(list.get(1), "2: print office visit OV02132");
		assertEquals(list.get(2), "1: unmerge notification NX1115");
		
		list = def.getTopActivities(14);
		assertEquals(list.size(), 2);
		assertEquals(list.get(0), "13: sort HL7 Code 422");
		assertEquals(list.get(1), "1: print office visit OV02132");
		
		list = def.getTopActivities(10);
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), "10: sort HL7 Code 422");
		
		
		list = type.getTopActivities(10);
		assertEquals(list.size(), 0);
		
		
	}
}
