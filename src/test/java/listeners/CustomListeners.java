package listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import base.TestBase;
import utilities.TestConfig;
import utilities.TestUtil;
import utilities. MonitoringMail;


public class CustomListeners extends TestBase implements ITestListener,ISuiteListener {

	public String messageBody;
    public void onTestStart(ITestResult result) {
    
            test = rep.createTest(result.getName().toUpperCase());
            //runmodes - Y
            if(!TestUtil.isTestRunnable(result.getName(), excel)) {
            	 throw new SkipException ("Skipping the test"+result.getName().toUpperCase()+" as the run mode is No");
            }

        
    }

    public void onTestSuccess(ITestResult result) {
   System.setProperty("org.uncommons.reportng.escape-output", "false");
        test.log(Status.PASS, result.getName().toUpperCase() + " PASS");  Reporter.log("Capturing Screenshot");
      rep.flush();
    }

    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        
        try {
        	 // Capture the screenshot
            TestUtil.captureScreenshot();
            
            // Log the failure message
            test.log(Status.FAIL, result.getName().toUpperCase() + " failed. Screenshot attached.");
            
            // Attach the screenshot
            test.addScreenCaptureFromPath(TestUtil.screenshotName);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
      //  test.addScreenCaptureFromPath(TestUtil.screenshotName);
        test.log(Status.FAIL, "Test failed with exception: " + result.getThrowable());
        
        Reporter.log("Click to see  screenshot");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "> Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName +"><img scr ="+ TestUtil.screenshotName + " height = 200 width= 200><img</a>");
        rep.flush();
    }

    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, result.getName().toUpperCase() + " SKIPPED");
        rep.flush();
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Optional: Implement if needed
    }

    public void onStart(ITestContext context) {
        // Optional: Add any startup logic if needed
    }

    public void onFinish(ITestContext context) {
        rep.flush();
    }

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		MonitoringMail mail = new MonitoringMail();
		 
		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/DataDrivenLiveProject/Extent_Reports/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(messageBody);
		}
	
		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
