package edu.ncsu.csc316.activity.manager;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc316.activity.dsa.DataStructure;

/**
 * Tests functionality of ReportManager
 */
public class ReportManagerTest {
	
	/**
	 * writer
	 */
	private FileWriter writer;
	/**
	 * manager
	 */
	private ReportManager reportManager;
	/**
	 * manager
	 */
	private ReportManager report2Manager;
	/**
	 * manager
	 */
	private ReportManager report3Manager;
	/**
	 * manager
	 */
	private ReportManager report4Manager;
    
    /**
     * Sets up the ReportManager with LOGIN1.txt before each test.
     */
    @Before
    public void setUp() {
        // Initialize the ReportManager with LOGIN1.txt
        reportManager = new ReportManager("LOGIN1.TXT");
        report2Manager = new ReportManager("LOGIN2.TXT");
        report3Manager = new ReportManager("LOGIN3.TXT", DataStructure.SINGLYLINKEDLIST);
        report4Manager = new ReportManager("LOGIN4.TXT", DataStructure.UNORDEREDLINKEDMAP);        
    }

    /**
     * Tests the getDateReport method for a valid date.
     */
    @Test
    public void testGetDateReportValid() {
        String report = reportManager.getDateReport("10/02/2015");
        
        try {
			writer = new FileWriter("result/DATERESULTS1.TXT", false);
			
			writer.write(report);
			writer.close();

			assertFilesEqual("output/DATEOUT1.TXT", "result/DATERESULTS1.TXT");
			
			report = reportManager.getDateReport("02/19/2017");
	        
	        assertEquals("Activities recorded on 02/19/2017 [\n"
	        		+ "   hqcooney, 02/19/2017 06:16:58PM, sort, HL7 Code 422\n"
	        		+ "]", report);
	        
	        assertTrue("The date report should contain the correct log entry for 02/19/2017", 
	        		report.contains("hqcooney, 02/19/2017 06:16:58PM, sort, HL7 Code 422"));
	        
	        report = report3Manager.getDateReport("09/21/2024");
	        assertEquals("Activities recorded on 09/21/2024 [\n"
	        		+ "   bpviscou, 09/21/2024 10:17:42PM, watch, training video\n"
	        		+ "]", report);
	        
	        report = report4Manager.getDateReport("09/21/2024");
	        assertEquals("Activities recorded on 09/21/2024 [\n"
	        		+ "   jkquinn, 09/21/2024 03:33:33PM, watch, training video\n"
	        		+ "   rjphili, 09/21/2024 08:17:42PM, watch, training video\n"
	        		+ "   bpviscou, 09/21/2024 10:17:42PM, watch, training video\n"
	        		+ "]", report);
	        
	        report = report4Manager.getDateReport("03/21/2024");
	        assertEquals("Activities recorded on 03/21/2024 [\n"
	        		+ "   bpviscou, 03/21/2024 04:20:14PM, read, progress report\n"
	        		+ "   bpviscou, 03/21/2024 05:27:14PM, read, progress report\n"
	        		+ "   bpviscou, 03/21/2024 06:20:36PM, read, progress report\n"
	        		+ "]", report);
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
        
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
        
        report = report3Manager.getHourReport(22);
        assertEquals("Activities recorded during hour 22 [\n"
        		+ "   bpviscou, 09/21/2024 10:17:42PM, watch, training video\n"
        		+ "]", report);
        
        report = report4Manager.getHourReport(20);
        assertEquals("Activities recorded during hour 20 [\n"
        		+ "   jkquinn, 03/26/2024 08:20:40PM, read, progress report\n"
        		+ "   rjphili, 09/21/2024 08:17:42PM, watch, training video\n"
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
        
        report = report3Manager.getHourReport(14);
        assertEquals("No activities were recorded during hour 14", report);
        
        report = report3Manager.getHourReport(21);
        assertEquals("No activities were recorded during hour 21", report);
        
        report = report3Manager.getHourReport(23);
        assertEquals("No activities were recorded during hour 23", report);
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
        		report.contains("2: sort HL7 Code 422"));
        
        report = report3Manager.getTopUserActivitiesReport(2);
        assertTrue("The top user activities should contain the correct frequency of 'sort' activities",
        		report.contains("1: watch training video"));
        
        assertEquals(report4Manager.getTopUserActivitiesReport(20),
        		"Top User Activities Report [\n"
        		+ "   6: read progress report\n"
        		+ "   6: watch training video\n"
        		+ "   2: sort related files\n"
        		+ "   1: complete assigned task\n"
        		+ "   1: research new strategies\n"
        		+ "]");
    }
    
    /**
     * Helper method to assert File contents
     * @param expectedFilePath valid output
     * @param actualFilePath testing output
     */
    private void assertFilesEqual(String expectedFilePath, String actualFilePath) {
        try (BufferedReader expectedReader = new BufferedReader(new FileReader(expectedFilePath));
             BufferedReader actualReader = new BufferedReader(new FileReader(actualFilePath))) {

            String expectedLine;
            String actualLine;

            while ((expectedLine = expectedReader.readLine()) != null) {
                actualLine = actualReader.readLine();

                // Assert each line is the same
                assertEquals(expectedLine, actualLine, actualLine);
            }

            // Check if the actual file has extra lines
            if (actualReader.readLine() != null) {
                fail("Actual file has extra lines beyond expected file");
            }

        } catch (IOException e) {
            fail("An error occurred while reading files: " + e.getMessage());
        }
    }
}
