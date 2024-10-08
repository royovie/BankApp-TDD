package testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class ManagerLogin extends TestBase{

	@Test
	public void managerLogin () throws InterruptedException, IOException {
		verifyEquals("abc", "zxy");
		 Thread.sleep(3000);
		log.debug("Inside Login Test");
		 click ("bmlBtn_Xpath");
		 Thread.sleep(3000);
		 Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("addCustBtn"))),"Login not successful");
		
		 log.debug("Login successfully executed");
		 Reporter.log("Login successfully executed");
		
		 Assert.fail("Login not successful");
	}
	
}
