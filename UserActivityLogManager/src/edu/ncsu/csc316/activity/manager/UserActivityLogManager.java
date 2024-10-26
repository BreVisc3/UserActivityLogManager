package edu.ncsu.csc316.activity.manager;

import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.activity.io.LogEntryReader;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.sorter.Sorter;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.activity.dsa.Algorithm;
import edu.ncsu.csc316.activity.dsa.DSAFactory;

/**
 * LogManager for user activity
 */
public class UserActivityLogManager {
	
	private List<LogEntry> logList;
	private final String path = "input/";
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ssa");
    Scanner scanner;

	/**
	 * Constructor
	 * @param logFile to read from
	 */
	public UserActivityLogManager(String logFile) {
		this(logFile, DataStructure.UNORDEREDLINKEDMAP);
	}
	
	/**
	 * Constructor
	 * @param logFile to read from
	 * @param mapType to be used for storage
	 */
	public UserActivityLogManager(String logFile, DataStructure mapType) {
		DSAFactory.setListType(DataStructure.ARRAYBASEDLIST);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.COUNTING_SORT);
        DSAFactory.setMapType(mapType);
        
        try {
        	logList = LogEntryReader.loadLogEntries(path + logFile);
        } catch(Exception e) {
        	
        }
	}
	
	
/////CREATE LISTS SEPARATING LOGS BY DATE OR HOUR, MAKE THE KEY THE DATE OR HOUR  INNNNNFORMAT
	
	/**
	 * Get LogEntries by date
	 * @return Map of logEntries
	 */
	public Map<String, List<LogEntry>> getEntriesByDate() {
        
		Map<String, List<LogEntry>> dateMap = DSAFactory.getMap(null);
		
		for (LogEntry entry : logList) {
	        String date = entry.getTimestamp().format(dateFormat);  // Format timestamp to a String date
	        
	        List<LogEntry> entriesForDate = dateMap.get(date);
	        
	        if (entriesForDate == null) {
	            // Create a new list for entries of that date if none exists
	            entriesForDate = DSAFactory.getIndexedList();
	            dateMap.put(date, entriesForDate);
	        }
	        
	        // Add the current log entry to the list
	        entriesForDate.addLast(entry);
	    }

		/////////////////////
		
		Map<String, List<LogEntry>> sortedDateMap = DSAFactory.getMap(null);

	    // Sort the log entries within each date by their timestamps or other criteria
	    for (List<LogEntry> entries : dateMap.values()) {
	    	
	    	String date = entries.first().getTimestamp().format(dateFormat);
	    	List<LogEntry> sorted = DSAFactory.getIndexedList();
	    	
	    	LogEntry[] arr = new LogEntry[entries.size()];
	    	for(int i = 0; i < entries.size(); i++) {
	    		arr[i] = entries.get(i);
	    	}
	    	
	    	Sorter<LogEntry> comp = DSAFactory.getComparisonSorter(null);
	        comp.sort(arr);
	        
	        for(int i = 0; i < entries.size(); i++) {
	        	sorted.addLast(arr[i]);
	        }
	        
	        sortedDateMap.put(date, sorted);
	    }

	    return sortedDateMap;
	}
	
	/**
	 * Get LogEntries by hour
	 * @return Map of logEntries
	 */
	public Map<Integer, List<LogEntry>> getEntriesByHour() {  //REFACTOR
		
		Map<Integer, List<LogEntry>> hourMap = DSAFactory.getMap(null);
		
		for (LogEntry entry : logList) {
	        // Format timestamp to extract the hour portion
	        String[] hour = entry.getTimestamp().format(timeFormat).split(":");  // e.g., "14:00"
	        
	        int numHour = Integer.parseInt(hour[0]);
	        // Check if the hour is already in the map
	        List<LogEntry> entriesForHour = hourMap.get(numHour);
	        
	        if (entriesForHour == null) {
	            // If no entries exist for this hour, create a new list
	            entriesForHour = DSAFactory.getIndexedList();
	            hourMap.put(numHour, entriesForHour);
	        }
	        
	        // Add the current log entry to the list for that hour
	        entriesForHour.addLast(entry);
	    }
		
		//////////////////////////////////////
		
		Map<Integer, List<LogEntry>> sortedHourMap = DSAFactory.getMap(null);

	    // Sort the log entries within each hour by their timestamp
	    for (List<LogEntry> entries : hourMap.values()) {
	    	
	    	String[] hour = entries.first().getTimestamp().format(timeFormat).split(":");  // e.g., "14:00"
	        
	        int numHour = Integer.parseInt(hour[0]);
	    	
	    	List<LogEntry> sorted = DSAFactory.getIndexedList();
	    	
	    	LogEntry[] arr = new LogEntry[entries.size()];
	    	for(int i = 0; i < entries.size(); i++) {
	    		arr[i] = entries.get(i);
	    	}
	    	
	    	Sorter<LogEntry> comp = DSAFactory.getComparisonSorter(null);
	    	comp.sort(arr);
	    	
	    	
	    	for(int i = 0; i < entries.size(); i++) {
	    		sorted.addLast(arr[i]);
	    	}
	    	
	    	sortedHourMap.put(numHour, sorted);
	    }

	    return sortedHourMap;
	}
	
	
	/**
	 * Get most frequented activities
	 * @param number of activities to read
	 * @return List of most frequented activities
	 */
	public List<String> getTopActivities(int number) {
		Map<String, List<LogEntry>> activityFrequency = getActionList(logList);
	    //Map of log sorted by action is acquired *VERIFIED*
	    
	    //Sort by longest list
	    Map<String, List<LogEntry>> activityList = DSAFactory.getMap(null);
	    
	    List<LogEntry> big = null;
	    int[] sizes = new int[activityFrequency.size()];


	    for(int i = 0; i < sizes.length; i++) {
	    	int largest = 0;
		    for(List<LogEntry> find : activityFrequency.values()) {
		    	
		    	if(find.size() > largest) {
		    		largest = find.size();				
		    		big = find;
		    	}
		    	else if(find.size() == largest) {   //////TODO: FIX LOGIC HERE
		    		char[] findLet = find.first().getAction().toCharArray();
		    		char[] bigLet = big.first().getAction().toCharArray();
		    		for(int j = 0; j < Math.min(bigLet.length, findLet.length); j++) {
			    		if(findLet[j] > bigLet[j]) {
			    			big = find;
			    			break;
			    		}
		    		}
		    	}
		    }
		    sizes[i] = largest;
		    activityFrequency.remove(big.first().getAction()); //To not get rechosen as largest
		    activityList.put(big.first().getAction(), big); //Add into array with frequency key    //CANNOT HAVE 2 SAME KEYS
	    }
	    
	    //Activity list holds sortedByAscending frequency lists
	    
	    //SORT BY LARGEST SIZE
	    List<String> topActivities = getReturnArray(sizes, activityList, number);
	    
	    return topActivities;
	}
	
	
	/**
	 * Returns list sorted into sublists by action
	 * @param list of logs
	 * @return logs sorted into action lists
	 */
	private Map<String, List<LogEntry>> getActionList(List<LogEntry> list) {
		Map<String, List<LogEntry>> activityFrequency = DSAFactory.getMap(null);
	    
	    // Count occurrences of each activity
	    for (LogEntry entry : list) {
	        String activity = entry.getAction(); 
	        
	        List<LogEntry> isActivity = activityFrequency.get(activity);
	        
	        if(isActivity != null) {
	        	activityFrequency.get(activity).addLast(entry);
	        }
	        else {
	        	List<LogEntry> newList = DSAFactory.getIndexedList();
	        	newList.addLast(entry);
	        	activityFrequency.put(activity, newList);
	        }
	    }
	    
	    return activityFrequency;
	}
	
	/**
	 * Gets the array to be returned for topActivities
	 * @param sizes of each list sorted
	 * @param activityList list of logs sorted by action
	 * @param number of most frequented logs requested
	 * @return List of string representations of the log frequency
	 */
	private List<String> getReturnArray(int[] sizes, Map<String, List<LogEntry>> activityList, int number) {
		List<String> topActivities = DSAFactory.getIndexedList();
	    int count = 0;
	    
	    //For each action list in descending order (getting by correctly sorted size[])
	    for(int i = 0; i < sizes.length; i++) {
	    	
	    	List<LogEntry> big = getLargest(activityList); //acquire list
	    	int freq = 0;					  //set new frequency for action list
	    	for(@SuppressWarnings("unused") LogEntry log : big) {
	    		
	    		//if the number of entries requested hasn't been reached
	    		if(count < number) {
	    			
	    			count++;
	    			freq++;
	    		}
	    		//if the request number has been reached leave the loop
	    		else {
	    			break;
	    		}
	    	}
	    	
	    	//Add string to return list
	    	String entry = Integer.toString(freq) + ": " + big.first().getAction() + " " + big.first().getResource();
	    	topActivities.addLast(entry);
	    	
	    	activityList.remove(big.first().getAction());
	    	//if the request number was reached break this loop as well
	    	if(count >= number) {
	    		break;
	    	}
	    }
	    
	    return topActivities;
	}
	
	/**
	 * Gets the most frequented log action list
	 * @param activityList logs sorted into lists by action
	 * @return the most frequented action list
	 */
	private List<LogEntry> getLargest(Map<String, List<LogEntry>> activityList) {
		List<LogEntry> large = DSAFactory.getIndexedList();
		int size = 0;
		for(List<LogEntry> list : activityList.values()) {
			if(list.size() > size) {
				size = list.size();
				large = list;
			}
			else if(list.size() == size) {
				if(list.first().getAction().compareTo(large.first().getAction()) == -1) {
					large = list;
				}
			}
		}
		
		return large;
	}
}
