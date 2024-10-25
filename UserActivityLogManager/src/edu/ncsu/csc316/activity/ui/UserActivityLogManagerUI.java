package edu.ncsu.csc316.activity.ui;

import java.util.Scanner;

import edu.ncsu.csc316.activity.manager.ReportManager;

/**
 * User Interface for Activity Project
 */
public class UserActivityLogManagerUI {
	
	/**
	 * Project main method
	 * @param args command line
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ReportManager manager = null;
        boolean exit = false;

        while (!exit) {
            // Display the menu options
            System.out.println("===== User Activity Log Manager =====");
            System.out.println("1. Load Log Data from File");
            System.out.println("2. View Most Frequent User Activities");
            System.out.println("3. View Logs for a Specific Date");
            System.out.println("4. View Logs for a Specific Hour");
            System.out.println("5. Exit");
            System.out.print("Please choose an option (1-5): ");
            String choice = scanner.nextLine();

            // Handle the user's menu choice
            switch (choice) {
                case "1":
                    System.out.print("Enter the log data file path: ");
                    String filePath = scanner.nextLine();
                    
                    try {
                    	manager = new ReportManager(filePath);
                    	System.out.println("Log data loaded successfully.");
                    } catch (Exception e) {
                        System.out.println("File " + filePath + " does not exist, please try again.\n\n");
                    }
                    
                    break;

                case "2":
                    System.out.print("Enter the number of most frequent activities to view: ");
                    int numActivities = Integer.parseInt(scanner.nextLine());
                    try {
                    	String frequentActivities = manager.getTopUserActivitiesReport(numActivities);
                    	System.out.println(frequentActivities);
                    } catch(NullPointerException e) {
                    	System.out.println("A file has not been loaded in to the manager yet, use choice [1], enter a valid file, and try again.\n\n");
                    }
                    //Display
                    
                    
                    break;

                case "3":
                    System.out.print("Enter the date (yyyy-mm-dd) to view logs: ");
                    String date = scanner.nextLine();
                    try {
                    	String dateReport = manager.getDateReport(date);
                    	System.out.println(dateReport);
                    } catch(NullPointerException e) {
                    	System.out.println("A file has not been loaded in to the manager yet, use choice [1], enter a valid file, and try again.\n\n");
                    }
                    
                    //Display
                    
                    break;

                case "4":
                    System.out.print("Enter the hour (hh) to view logs: ");
                    String dateHour = scanner.nextLine();
                    try {
                    	String hourReport = manager.getHourReport(dateHour);
                    	System.out.println(hourReport);
                    } catch(NullPointerException e) {
                    	System.out.println("A file has not been loaded in to the manager yet, use choice [1], enter a valid file, and try again.\n\n");
                    }
                    
                    break;

                case "5":
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
		
	}
}