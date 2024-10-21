package edu.ncsu.csc316.activity.manager;

import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.activity.io.LogEntryReader;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;
import edu.ncsu.csc316.dsa.map.Map.Entry;
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
	 * @param DataStructure to be used for storage
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
	public Map<String, List<LogEntry>> getEntriesByHour() {
		
		Map<String, List<LogEntry>> hourMap = DSAFactory.getMap(null);
		
		for (LogEntry entry : logList) {
	        // Format timestamp to extract the hour portion
	        String[] hour = entry.getTimestamp().format(timeFormat).split(":");  // e.g., "14:00"
	        
	        String milHour = Integer.parseInt(hour[0]) > 12 
	                ? Integer.toString(Integer.parseInt(hour[0]) + 12) 
	                : hour[0];
	        
	        // Check if the hour is already in the map
	        List<LogEntry> entriesForHour = hourMap.get(milHour);
	        
	        if (entriesForHour == null) {
	            // If no entries exist for this hour, create a new list
	            entriesForHour = DSAFactory.getIndexedList();
	            hourMap.put(milHour, entriesForHour);
	        }
	        
	        // Add the current log entry to the list for that hour
	        entriesForHour.addLast(entry);
	    }
		
		//////////////////////////////////////
		
		Map<String, List<LogEntry>> sortedHourMap = DSAFactory.getMap(null);

	    // Sort the log entries within each hour by their timestamp
	    for (List<LogEntry> entries : hourMap.values()) {
	    	
	    	String[] hour = entries.first().getTimestamp().format(timeFormat).split(":");
	    	String milHour = Integer.parseInt(hour[0]) > 12 
	                ? Integer.toString(Integer.parseInt(hour[0]) + 12) 
	                : hour[0];
	    	
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
	    	
	    	sortedHourMap.put(milHour, sorted);
	    }

	    return sortedHourMap;
	}
	
	/**
	 * Get most frequented activities
	 * @param number of activities to read
	 * @return List of most frequented activities
	 */
	public List<String> getTopActivities(int number) {
		Map<String, List<LogEntry>> activityFrequency = DSAFactory.getMap(null);
	    
	    // Count occurrences of each activity
	    for (LogEntry entry : logList) {
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
	    
	    //SORT BY LARGEST SIZE
	    
	    /* int count = 0
	     * 		while count 0 < number
	     * 			
	    */
	    Map<String, List<LogEntry>> activityList = DSAFactory.getMap(null);
	    
	    for(int i = 0; i < Math.min(activityFrequency.size(), number); i++) {
	    	
	    }
	    
	    for (Map<String, List<LogEntry>> entry : activityFrequency.entrySet()) {
	        activityList.addLast(entry); 
	    }
	    
	    Entry<String, Integer>[] array = new Entry[activityList.size()];

        for (int i = 0; i < activityList.size(); i++) {
            array[i] = activityList.get(i); 
        }
	    
	    //DSAFactory.getComparisonSorter(null).sort(array);
	    
	    List<String> topActivities = DSAFactory.getIndexedList();
	    for (int i = 0; i < Math.min(number, activityList.size()); i++) {
	        topActivities.addLast(activityList.get(i).getKey());
	    }
	    
	    return topActivities;
	}
}
