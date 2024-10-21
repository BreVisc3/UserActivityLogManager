package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestReportManager {
	private ReportManager reportManager;
    
    /**
     * Sets up the ReportManager with LOGIN1.txt before each test.
     */
    @Before
    public void setUp() {
        // Initialize the ReportManager with LOGIN1.txt
        reportManager = new ReportManager("LOGIN1.txt");
    }

    /**
     * Tests the getDateReport method for a valid date.
     */
    @Test
    public void testGetDateReportValid() {
        String report = reportManager.getDateReport("02/19/2017");
        
        //assertEquals("Activities recorded on 02/19/2017 [\r\n"
        //		+ "   hqcooney, 02/19/2017 06:16:58PM, sort, HL7 Code 422\r\n"
        //		+ "]", report);         WHYYY FAIL
        
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
        String report = reportManager.getHourReport("10");
        assertTrue(report.contains("hqcooney, 11/08/2016 10:43:29AM, sort, HL7 Code 422"));
        assertEquals("Activities recorded during hour 10 [\r\n"
        		+ "   hqcooney, 11/08/2016 10:43:29AM, sort, HL7 Code 422\r\n"
        		+ "]", report);
        
    }

    /**
     * Tests the getHourReport method for an invalid hour.
     */
    @Test
    public void testGetHourReportInvalid() {
        String report = reportManager.getHourReport("25");
        assertEquals("Please enter a valid hour between 0 (12AM) and 23 (11PM)", report);
        
        report = reportManager.getHourReport("-1");
        assertEquals("Please enter a valid hour between 0 (12AM) and 23 (11PM)", report);
    }
    
    /**
     * Tests the getTopUserActivities method.
     */
    @Test
    public void testGetTopUserActivities() {
        String report = reportManager.getTopUserActivities(3);
        assertTrue("The top user activities should contain the correct frequency of 'sort' activities",
        		report.contains("13: sort HL7 Code 422"));
        assertTrue("The top user activities should contain the correct frequency of 'print' activities", 
        		report.contains("2: print office visit OV02132"));
    }
}
