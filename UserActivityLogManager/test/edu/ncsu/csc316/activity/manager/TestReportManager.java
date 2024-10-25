package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class TestReportManager {
	private ReportManager reportManager;
	private ReportManager report2Manager;
    
    /**
     * Sets up the ReportManager with LOGIN1.txt before each test.
     */
    @Before
    public void setUp() {
        // Initialize the ReportManager with LOGIN1.txt
        reportManager = new ReportManager("LOGIN1.txt");
        report2Manager = new ReportManager("LOGIN2.txt");
    }

    /**
     * Tests the getDateReport method for a valid date.
     */
    @Test
    public void testGetDateReportValid() {
        String report = reportManager.getDateReport("02/19/2017");
        
        assertEquals("Activities recorded on 02/19/2017 [\n"
        		+ "   hqcooney, 02/19/2017 06:16:58PM, sort, HL7 Code 422\n"
        		+ "]", report);
        /*
        try {
			report = new String (Files.readAllBytes(Paths.get("output/DATEOUT1.TXT")));
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		assertEquals(report.trim() , reportManager.getDateReport("10/02/2015"));		    ///HOW DO I GET THESE TO MATCH UP ESCAPE SEQUENCE-WISE
        */
        
        assertTrue("The date report should contain the correct log entry for 02/19/2017", 
        		report.contains("hqcooney, 02/19/2017 06:16:58PM, sort, HL7 Code 422"));
    }

    /**
     * Tests the getDateReport method for an invalid date.
     */
    @Test
    public void testGetDateReportInvalid() {
    	String report = reportManager.getDateReport("02/30/2017");
        assertEquals("No activities were recorded on 02/30/2017", report);
        
        report = reportManager.getDateReport("2/30/2017");
        assertEquals("Please enter a valid date in the format MM/DD/YYYY", report);
    }
    
    /**
     * Tests the getHourReport method for a valid hour.
     */
    @Test
    public void testGetHourReportValid() {
        String report = reportManager.getHourReport(10);
        assertTrue(report.contains("hqcooney, 11/08/2016 10:43:29AM, sort, HL7 Code 422"));
        assertEquals("Activities recorded during hour 10 [\n"
        		+ "   hqcooney, 11/08/2016 10:43:29AM, sort, HL7 Code 422\n"
        		+ "]", report);
        
    }

    /**
     * Tests the getHourReport method for an invalid hour.
     */
    @Test
    public void testGetHourReportInvalid() {
        String report = reportManager.getHourReport(25);
        assertEquals("Please enter a valid hour between 0 (12AM) and 23 (11PM)", report);
        
        report = reportManager.getHourReport(-1);
        assertEquals("Please enter a valid hour between 0 (12AM) and 23 (11PM)", report);
        
        report = reportManager.getHourReport(14);
        assertEquals("No activities were recorded during hour 14", report);
    }
    
    /**
     * Tests the getTopUserActivities method.
     */
    @Test
    public void testGetTopUserActivities() {
        String report = reportManager.getTopUserActivitiesReport(15);
        assertTrue("The top user activities should contain the correct frequency of 'sort' activities",
        		report.contains("13: sort HL7 Code 422"));
        assertTrue("The top user activities should contain the correct frequency of 'print' activities", 
        		report.contains("2: print office visit OV02132"));
        
        assertEquals(reportManager.getTopUserActivitiesReport(-1), "Please enter a number > 0");
        
        assertEquals(report2Manager.getTopUserActivitiesReport(10), "The log file does not contain any user activities");
        
        assertEquals(reportManager.getTopUserActivitiesReport(17),
        		"Top User Activities Report [\n"
        		+ "   13: sort HL7 Code 422\n"
        		+ "   2: print office visit OV02132\n"
        		+ "   1: unmerge notification NX1115\n"
        		+ "   1: view HL7 Code 422\n"
        		+ "]");
        
        assertEquals(reportManager.getTopUserActivitiesReport(20),
        		"Top User Activities Report [\n"
        		+ "   13: sort HL7 Code 422\n"
        		+ "   2: print office visit OV02132\n"
        		+ "   1: unmerge notification NX1115\n"
        		+ "   1: view HL7 Code 422\n"
        		+ "]");
        
        
        report = reportManager.getTopUserActivitiesReport(2);
        assertTrue("The top user activities should contain the correct frequency of 'sort' activities",
        		report.contains("13: sort HL7 Code 422"));
    }
}
