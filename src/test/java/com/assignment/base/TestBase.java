package com.assignment.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static String timeStamp;
	public static String updatedReportFile;
	public static String browser="";
	public static String url="";
	public static DesiredCapabilities caps = new DesiredCapabilities();
	public static ExtentReports reports;
	public static ExtentTest test;


	public TestBase() {
		Properties prop = new Properties();
		try {
			 FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\config.properties");
			prop.load(ip);
			browser=prop.getProperty("browser");
			url=prop.getProperty("url");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * This method is to launch web browser
	 * 
	 * @return
	 * @return
	 * @return
	 * @throws InterruptedException
	 */
	// initialization
	public static void initialization() {
		if (browser.equalsIgnoreCase("Ch")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			// option.addArguments("--headless");
			options.addArguments("start-maximized");
			caps.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("ff")) {
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-private");
			caps.setCapability("moz:firefoxOptions", options);
			driver = new FirefoxDriver(options);
		}
		driver.manage().deleteAllCookies();
		driver.get(url);
		reports = new ExtentReports(System.getProperty("user.dir")+"\\Reports\\report.html", false);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	/**
	 * Method used to return web driver object
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {

		return driver;
	}

	/**
	 * Method to switch the frame based element
	 * @param ele
	 * @param driver
	 */
	public void switchToFrame(WebElement ele, WebDriver driver) {
		driver.switchTo().frame(ele);
	}
	
	/**
	 * Method to switch the frame based on name or id
	 * @param nameOrId
	 * @param driver
	 */
	public void switchToFrame(String nameOrId, WebDriver driver) {
		driver.switchTo().frame(nameOrId);
	}
	
	/**
	 * Method to switch to default frame
	 * @param driver
	 */
	public void switchToDefaultFrame(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	

	
	/**
	 * 
	 * @param args
	 * @param value
	 * @throws IOException
	 */
	public static void executeJS(String args, String value) throws IOException {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript(args + ";");
		test.log(LogStatus.INFO, test.addScreenCapture(captureScreen(driver))+"Click on " + value);

	}
	
	/**
	 * 
	 * @return
	 */
	public String fetchOutput() {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		return je.executeScript("return exportRoot.showscreen_txt.text").toString();
	}
	
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	 public String createJsForCalc(String str) {
		 //test.log(LogStatus.INFO, str);
		 String res = "\""+ str + "\".split('').forEach(function(item,index){"
		 		+ "var keycode = item.charCodeAt(0);document.getElementById(\"canvas\").dispatchEvent(new KeyboardEvent(\"keypress\",{which:keycode, keyCode:keycode,bubbles:true}))})";
		 return res;
	 }


	 /**
	  * 
	  */
	public void closeBrowser() {
		Set<String> allWindows = driver.getWindowHandles();
		for (String window : allWindows) {
			driver.switchTo().window(window);
			driver.close();
		}
		reports.endTest(test);
		reports.flush();
	}
	
	/**
	 * 
	 * @param driver
	 * @return
	 * @throws IOException
	 */
	public static String captureScreen(WebDriver driver) throws IOException {
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destinationFile=new File("src/../images"+System.currentTimeMillis()+".png");
		String absolute_screen = destinationFile.getAbsolutePath();
		FileUtils.copyFile(srcFile, destinationFile);
		return absolute_screen;
		
	}
	
	/**
	 * 
	 * @param actualVal
	 * @param expectedVal
	 * @throws IOException
	 */
	public void validateResult(String actualVal, String expectedVal) throws IOException {
		SoftAssert softAssert=new SoftAssert();
		if(actualVal.equals(expectedVal)) {
			test.log(LogStatus.PASS, test.addScreenCapture(captureScreen(driver)) + actualVal + " is equal to " + expectedVal);
			softAssert.assertEquals(actualVal, expectedVal);
		}
		
		if(!actualVal.equals(expectedVal)){
			test.log(LogStatus.FAIL, test.addScreenCapture(captureScreen(driver)) + actualVal + " is not equal to " + expectedVal);
			softAssert.assertTrue((actualVal.equals(expectedVal)), actualVal + " is not equal to " + expectedVal);
		}
		
	}

}
