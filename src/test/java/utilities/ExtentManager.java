package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.IOException;


public class ExtentManager {

	private static ExtentReports extent;
	
	public static ExtentReports getInstance () {
		if(extent ==null) {
			
			// Initialize the Spark reporter 
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\extent.html");
			
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            try {
            	sparkReporter.loadXMLConfig(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\extentconfig\\ReportsConfig.xml"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return extent;
	}
}
