package base;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.TestUtil;


public abstract class TestBase {

public static WebDriver driver;
public static Properties config = new Properties();
public static Properties OR = new Properties();	
public static FileInputStream fis ;
public static Logger log = Logger.getLogger("devpinoyLogger");
public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\test.xlsx");
public static WebDriverWait wait;
public static ExtentReports rep = ExtentManager.getInstance();
public static ExtentTest test;


@BeforeSuite
public void setUp() {
		
		if(driver==null) {
			
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("connected ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("connected ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (config.getProperty("browser").equals("firefox")) {

				driver = new FirefoxDriver();

		} else if (config.getProperty("browser").equals("chrome")) {
					System.setProperty("webdriver.chomre.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
					driver = new ChromeDriver();
					log.debug("chrome ");
		} else if (config.getProperty("browser").equals("ie")) {

			driver = new InternetExplorerDriver();

		}
			driver.get(config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		}
	}
	
	public void click (String locator) {
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		test.log(Status.INFO,"Clicking on:"+ locator);
	}
	public void type (String locator, String value) {
		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		test.log(Status.INFO,"Typing in :"+ locator+"entered value as " +value);
	}
	static WebElement dropdown;
	public void select (String locator, String value) {
		dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(Status.INFO,"Select from dropdown :"+ locator+"entered value as " +value);
	}
public boolean isElementPresent (By by) {
	
	try {
		driver.findElement(by);
		return true;
	}catch (NoSuchElementException e) {
		return false;
	}
}
	public static void verifyEquals(String expected, String actual) throws IOException {
	try {
			Assert.assertEquals(actual, expected);
	}catch(Throwable t) {
		TestUtil.captureScreenshot();
		//ReportNG
		  Reporter.log("<br>"+ "Verification failure :"+t.getMessage()+"<br>");
	        Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName +"><img scr ="+ TestUtil.screenshotName + " height = 200 width= 200><img</a>");
	        Reporter.log("<br>");
	        Reporter.log("<br>");
	        //Extent Report
	        test.log(Status.FAIL, "Test failed with exception: " + t.getMessage());
	        test.addScreenCaptureFromPath(TestUtil.screenshotName);
	}
}
	@AfterSuite
	public void tearDown () {
		
		if(driver != null) {
			driver.quit();
		}
	
	}
}
