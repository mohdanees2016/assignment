package com.assignment.pages;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assignment.base.TestBase;

public class FullScreenCalculatorPage extends TestBase {
	
	@FindBy(id="canvas")
	WebElement entireCal;
	
	@FindBy(id="fullframe")
	WebElement frame;
	
	WebDriver driver;
	 public FullScreenCalculatorPage() {
		initialization();
		this.driver=getDriver();
		PageFactory.initElements(driver, this);
	}
	 
	 public void addition(String arg1, String arg2, String operator) throws IOException {
		 test = reports.startTest(operator + " of two numbers");
		 switchToFrame(frame, driver);
		 executeJS(createJsForCalc(arg1),arg1);
		 executeJS(createJsForCalc(operator),operator);
		 executeJS(createJsForCalc(arg2),arg2);
		 executeJS(createJsForCalc("="),"=");
		 String val=fetchOutput();
		 System.out.println("val " + val);	 
		 //closeBrowser();	
	 
	 }
	 
	 public void verifyResult(String expectedResult) throws IOException {
		 String result = fetchOutput();
		 validateResult(result, expectedResult);
		 closeBrowser();	
	 }
	 
}
