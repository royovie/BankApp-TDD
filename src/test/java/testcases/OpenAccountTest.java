package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	
	@Test (dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccountTest(Hashtable<String,String>data) throws InterruptedException {
		click("openaccount");
		select("customerBtn",data.get("customer"));
		select("currencyBtn",data.get("currency"));
		click("process");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue( alert.getText().contains(data.get("alerttext")));
		Thread.sleep(3000);
		alert.accept();
	}
	
	
}
