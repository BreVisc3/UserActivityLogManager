package edu.ncsu.csc316.activity.manager;

import java.time.format.DateTimeFormatter;

import edu.ncsu.csc316.activity.data.LogEntry;
import edu.ncsu.csc316.activity.dsa.Algorithm;
import edu.ncsu.csc316.activity.dsa.DSAFactory;
import edu.ncsu.csc316.activity.dsa.DataStructure;
import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.dsa.map.Map;

/**
 * ReportManager class 
 */
public class ReportManager {

	/**
	 * manager
	 */
	private UserActivityLogManager activityLogManager;
	/**
	 * format
	 */
    private final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ssa");
    /**
     * INDENT
     */
    private static final String INDENT = "   ";
    
	
	/**
	 * Constructor
	 * @param logFile to read from
	 */
	public ReportManager(String logFile) {
		this(logFile, DataStructure.UNORDEREDLINKEDMAP);
	}
	
	/**
	 * Constructor
	 * @param logFile to read from
	 * @param mapType to store file data in
	 */
	public ReportManager(String logFile, DataStructure mapType) {
        DSAFactory.setListType(mapType);
        DSAFactory.setComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setNonComparisonSorterType(Algorithm.MERGESORT);
        DSAFactory.setMapType(mapType);
        activityLogManager = new UserActivityLogManager(logFile, mapType);
	}
	
	/**
	 * Get reports on given date
	 * @param date to get
	 * @return String of all logs on date parameter
	 */
	public String getDateReport(String date) {
		try {
			
			//ERROR CHECK THE STRING DATE
			String[] dateNums = date.split("/");
			if(dateNums[0].length() != 2 || dateNums[1].length() != 2 || dateNums[2].length() > 4 ||
					Integer.parseInt(dateNums[0]) < 1 || Integer.parseInt(dateNums[0]) > 12 || 
					Integer.parseInt(dateNums[1]) < 1 || Integer.parseInt(dateNums[1]) > 31 ||
					Integer.parseInt(dateNums[2]) < 0 || Integer.parseInt(dateNums[2]) > 2024 ||
					dateNums.length != 3) {
				throw new NumberFormatException();
			}
            
            Map<String, List<LogEntry>> map = DSAFactory.getMap(null);
            map = activityLogManager.getEntriesByDate();

            List<LogEntry> list = DSAFactory.getIndexedList();
            list = map.get(date);
            
            StringBuilder report = new StringBuilder();
            
            if(list != null) {
            	report.append("Activities recorded on " + date + " [\n");
            	for(LogEntry entry : list) {
            		report.append(INDENT + entry.getUsername() + ", " + entry.getTimestamp().format(dateTimeFormat) + 
            				", " + entry.getAction() + ", " + entry.getResource() + "\n");
            	}
            	
            	report.append("]");
            }
            else {
            	return "No activities were recorded on " + date;
            }
            
            return report.toString();
        } catch(NumberFormatException e) {
            return "Please enter a valid date in the format MM/DD/YYYY";
        }
	}
	
	/**
	 * Get reports on given date
	 * @param hour to get
	 * @return String of all logs on hour parameter
	 */
	public String getHourReport(int hour) {
		try {
			
			if(hour < 0 || hour > 23) {
				throw new NumberFormatException();
			}
			
			Map<Integer, List<LogEntry>> map = DSAFactory.getMap(null);
            map = activityLogManager.getEntriesByHour();
			
			List<LogEntry> list = DSAFactory.getIndexedList();
			list = map.get(hour);
			
			StringBuilder report = new StringBuilder();
			
			if(list != null) {
				report.append("Activities recorded during hour " + hour + " [\n");
				
				for(LogEntry entry : list) {
					report.append(INDENT + entry.getUsername() + ", " + entry.getTimestamp().format(dateTimeFormat) + 
            				", " + entry.getAction() + ", " + entry.getResource() + "\n");
				}
				
				report.append("]");
			}
			else {
				return "No activities were recorded during hour " + hour;
			}
			
			return report.toString();
		} catch (NumberFormatException e) {
			return "Please enter a valid hour between 0 (12AM) and 23 (11PM)";
		}
	}
	
	/**
	 * Get top log activities
	 * @param number of logs to get
	 * @return String with most frequented log activities
	 */
	public String getTopUserActivitiesReport(int number) {
		
		List<String> list = DSAFactory.getIndexedList();
		list = activityLogManager.getTopActivities(number);
		
		StringBuilder report = new StringBuilder();
		
		if(number <= 0) {
			return  "Please enter a number > 0";
		}
		
		if(list.isEmpty()) {
			return "The log file does not contain any user activities";
		}
		else { 
			
			report.append("Top User Activities Report [\n");
			
			for(String entry : list) {
				report.append(INDENT + entry + "\n");
			}
			
			report.append("]");
		}
		return report.toString();
	}
}
