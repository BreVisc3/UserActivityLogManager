package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.activity.dsa.DSAFactory;
import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;

/**
 * Tests UserActivityLogManager
 */
public class UserActivityLogManagerTest {
	
	/**
	 * manager
	 */
	private UserActivityLogManager def;
	/**
	 * manager
	 */
	private UserActivityLogManager type;
	/**
	 * manager
	 */
	private UserActivityLogManager uni;
	/**
	 * map
	 */
	private Map<String, List<LogEntry>> map;
	/**
	 * map
	 */
	private Map<Integer, List<LogEntry>> hourMap;
	/**
	 * list
	 */
	private List<String> list;
	
	/**
	 * set up for testing
	 */
	@Before
	public void setUp() {
		def = new UserActivityLogManager("input/LOGIN1.TXT");
		type = new UserActivityLogManager("input/LOGIN2.TXT", DataStructure.UNORDEREDLINKEDMAP);
		uni = new UserActivityLogManager("input/LOGIN4.TXT", DataStructure.SEARCHTABLE);
		
		map = DSAFactory.getMap(null);
		hourMap = DSAFactory.getMap(null);
		list = DSAFactory.getIndexedList();
	}
	
	/**
	 * tests the functionality of getEntriesByDate
	 */
	@Test
	public void testGetEntriesByDate() {
		
		map = def.getEntriesByDate();
		assertEquals(map.size(), 16);
		
		map = type.getEntriesByDate();
		assertEquals(map.size(), 0);
		
		map = uni.getEntriesByDate();
		assertEquals(map.size(), 11);
	}

	/**
	 * tests the functionality of getEntriesByHour
	 */
	@Test
	public void testGetEntriesByHour() {
		
		hourMap = def.getEntriesByHour();
		assertEquals(hourMap.size(), 14);
		
		hourMap = type.getEntriesByHour();
		assertEquals(hourMap.size(), 0);
		
		hourMap = uni.getEntriesByHour();
		assertEquals(hourMap.size(), 14);
		
	}
	
	/**
	 * tests the functionality of getTopActivities
	 */
	@Test
	public void testGetTopActivities() {
		
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
		
		list = uni.getTopActivities(10);
		assertEquals(list.get(0), "6: read progress report");
		assertEquals(list.get(1), "4: watch training video");
		
		list = uni.getTopActivities(12);
		assertEquals(list.get(0), "6: read progress report");
		assertEquals(list.get(1), "6: watch training video");
		
		
	}
}
