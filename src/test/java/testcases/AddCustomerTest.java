package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.devtools.v127.database.Database;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	
	@Test (dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String>data) throws InterruptedException {
if(!data.get("runmode").equals("Y")){
			
			throw new SkipException("Skipping the test case as the Run mode for data set is NO");
		}
		click("addCustBtn");
		 Thread.sleep(3000);
		 type("firstname",data.get("firstname"));
		 type("lastname",data.get("lastname"));
		 type("postcode",data.get("postcode"));
		 click("addbtn");
		 Thread.sleep(3000);
		 
		 Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			Assert.assertTrue( alert.getText().contains(data.get("alerttext")));
			Thread.sleep(3000);
			alert.accept();

		
	}
	
	
}
