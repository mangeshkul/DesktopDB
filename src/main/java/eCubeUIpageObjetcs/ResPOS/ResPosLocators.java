package eCubeUIpageObjetcs.ResPOS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.windows.WindowsDriver;

public class ResPosLocators {
	
	WindowsDriver driver;
	
	
	public ResPosLocators(WindowsDriver driver)
	{
		this.driver = driver;
	}

	WebElement OutletButtonOnSelectOutletPage = driver.findElementByXPath("//*[contains(@AutomationId, 'btnOutlet1014')]");
	WebElement outletTableNumber = driver.findElementByXPath("//*[contains(@Name, '004')]"); 
	WebElement loginButtonLocator = driver.findElementByName("Login");
	
	
	public WebElement selectOutlet(){
	  return OutletButtonOnSelectOutletPage;	
	}
	
	public WebElement outletTable(){
		return outletTableNumber;	
	}
	
	public WebElement resPosLoginButton(){
		return loginButtonLocator;	
	}
}
