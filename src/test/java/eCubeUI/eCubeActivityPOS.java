package eCubeUI;

import io.appium.java_client.MobileElement;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjectsPages.ActivityPOSLocators;
import pageObjectsPages.CommonMethodsForActivityPOS;
import pageObjectsPages.LoginPage;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.time.LocalDate;

public class eCubeActivityPOS {

	public WindowsDriver driver = null;
	public ChromeDriver webDriver = null;
	

	private LoginPage loginPage;
	private ActivityPOSLocators activityPoslocators;
	private CommonMethodsForActivityPOS commonMethods;
	private AdminWeb adminTests;

	String guestName = "AutoGuest1";
	String appPath = "D:\\eCubePOS\\net8.0-windows\\eCubePOS.exe";
	LocalDate today = LocalDate.now();
	int dayOfMonth = today.getDayOfMonth();
	String adminUrl = "http://ecubeadminwebapp.qa.kcspl.in:9090/login";
	String userName = "Admin";
	String password = "123";
	String JSON_FILE_PATH = System.getProperty("user.dir") + "/src/test/java/DataManagment/GateEntry.json";
	String DATA_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/test/java/DataManagment/data.json";
	String chromeDriverPath = "D:\\chromedriver\\chromedriver.exe";

	@BeforeMethod
	public void setUp() throws Exception {

		commonMethods = new CommonMethodsForActivityPOS(null);

		commonMethods.launchApplication(appPath);
		Thread.sleep(4000);

		String windowHandle = commonMethods.getWindowHandleHex("eCubePOS");
		if (windowHandle == null || windowHandle.isEmpty()) {
			throw new RuntimeException("❌ Could not get MainWindowHandle for eCubePOS");
		}
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("appTopLevelWindow", "0x" + windowHandle);
		caps.setCapability("platformName", "Windows");
		caps.setCapability("deviceName", "WindowsPC");

		driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), caps);
		commonMethods = new CommonMethodsForActivityPOS(driver);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		loginPage = new LoginPage(driver);
		activityPoslocators = new ActivityPOSLocators(driver);
		System.out.println("✅ Attached to eCubePOS with handle 0x" + windowHandle);
	}

	@Test
	public void gateEntryWithMember() throws InterruptedException, IOException, ParseException {
		String memberCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode1");
		loginPage.login("Admin", "123");

		Thread.sleep(4000);
		activityPoslocators.codeEntry().sendKeys(memberCode);
		activityPoslocators.submitCode().click();
		activityPoslocators.guestCount().click();
		activityPoslocators.guestName().sendKeys(guestName);
		activityPoslocators.addButton().click();
		String guestUserName = activityPoslocators.guestlistBox().getText();

		if (guestUserName.contains(guestName)) {
			System.out.println("✅ The GuestName added Successfully!");
			Thread.sleep(2000);
		} else {
			System.out.println("❌ GuestName not found!");
		}

		activityPoslocators.gateEntryForGuest().click();
		activityPoslocators.proceedButton().click();
		activityPoslocators.proceedButton().click();
		Thread.sleep(2000);
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(3000);
		activityPoslocators.waitForSomeTime();
		Map<String, String> dbData = commonMethods.getDataFromDataBase(memberCode);
		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
		Thread.sleep(3000);
		driver.closeApp();
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		webDriver = new ChromeDriver(options);

		adminTests = new AdminWeb(webDriver);
		String serviceName1 = "Gate Entry For Member";
		String serviceName2 = "Gate Entry For Guest";
		String subServiceName1 = "Gate Entry For Member";
		String subServiceName2 = "Gate Entry For Guest";
		adminTests.verifyReports(adminUrl, userName, password, memberCode, JSON_FILE_PATH, serviceName1, serviceName2,
				subServiceName1, subServiceName2);
	}

	@Test
	public void entryForHealthClub() throws InterruptedException, IOException, ParseException {
		String mCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode2");
		String guestNameDetails = "CypressGuest";
		String serviceNameInHealthClub = "Extra Virgin Olive Oil 50ml";

		loginPage.login("Admin", "123");
		Thread.sleep(4000);
		activityPoslocators.codeEntry().sendKeys(mCode);
		activityPoslocators.submitCode().click();
		activityPoslocators.guestCount().click();
		activityPoslocators.guestName().sendKeys(guestNameDetails);
		activityPoslocators.addButton().click();
		activityPoslocators.gateEntryForGuest().click();
		activityPoslocators.proceedButton().click();
		activityPoslocators.proceedButton().click();
		Thread.sleep(2000);
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(4000);
		activityPoslocators.healthClubForGents().click();
		activityPoslocators.waitForSomeTime();
		activityPoslocators.codeEntry().sendKeys(mCode);
		activityPoslocators.submitCode().click();
		activityPoslocators.guestCount().click();
		activityPoslocators.guestName().sendKeys(guestNameDetails);
		activityPoslocators.addButton().click();
		String guestUserName = activityPoslocators.guestlistBox().getText();

		if (guestUserName.contains(guestNameDetails)) {
			System.out.println("✅ The GuestName added Successfully!");
			Thread.sleep(2000);
		} else {
			System.out.println("❌ GuestName not found!");
		}

		activityPoslocators.otherServiceNameInHeathClub(serviceNameInHealthClub);
		activityPoslocators.waitForSomeTime();
		activityPoslocators.addedServiceName(serviceNameInHealthClub);
		activityPoslocators.proceedButton().click();
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(3000);
		activityPoslocators.waitForSomeTime();
		Map<String, String> dbData = commonMethods.getDataFromDataBase(mCode);
		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
		Thread.sleep(3000);
		driver.closeApp();
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		webDriver = new ChromeDriver(options);

		adminTests = new AdminWeb(webDriver);
		String serviceName1 = "HEALTH CLUB GENTS";
		String serviceName2 = "HEALTH CLUB GENTS";
		String subServiceName1 = "DAILY MEMBER’S CHARGES HCG";
		String subServiceName2 = "EXTRA VIRGIN OLIVE OIL 50ML";

		adminTests.verifyReports(adminUrl, userName, password, mCode, JSON_FILE_PATH, serviceName1, serviceName2,
				subServiceName1, subServiceName2);
	}

	@Test
	public void entryForBadminton() throws InterruptedException, IOException, ParseException {
		String mCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode3");
		String guestNameDetails = "SportsUser";
		String otherServiceNameInBadmintonSports = "Babolat Hybrid Shuttles";

		loginPage.login("Admin", "123");
		Thread.sleep(4000);
		activityPoslocators.codeEntry().sendKeys(mCode);
		activityPoslocators.submitCode().click();
		activityPoslocators.guestCount().click();
		activityPoslocators.guestName().sendKeys(guestNameDetails);
		activityPoslocators.addButton().click();
		activityPoslocators.gateEntryForGuest().click();
		activityPoslocators.proceedButton().click();
		activityPoslocators.proceedButton().click();
		Thread.sleep(2000);
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(4000);
		activityPoslocators.badmintonSports().click();
		activityPoslocators.waitForSomeTime();
		activityPoslocators.codeEntry().sendKeys(mCode);
		activityPoslocators.submitCode().click();
		activityPoslocators.guestCount().click();
		activityPoslocators.guestName().sendKeys(guestNameDetails);
		activityPoslocators.addButton().click();
		String guestUserName = activityPoslocators.guestlistBox().getText();

		if (guestUserName.contains(guestNameDetails)) {
			System.out.println("✅ The GuestName added Successfully!");
			Thread.sleep(2000);
		} else {
			System.out.println("❌ GuestName not found!");
		}

		activityPoslocators.otherServiceNameInHeathClub(otherServiceNameInBadmintonSports);
		activityPoslocators.waitForSomeTime();
		activityPoslocators.addedServiceName(otherServiceNameInBadmintonSports);
		activityPoslocators.proceedButton().click();
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(3000);
		activityPoslocators.waitForSomeTime();
		Map<String, String> dbData = commonMethods.getDataFromDataBase(mCode);
		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
		Thread.sleep(3000);
		driver.closeApp();
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		webDriver = new ChromeDriver(options);

		adminTests = new AdminWeb(webDriver);
		String serviceName1 = "BADMINTON SPORTS";
		String serviceName2 = "BADMINTON SPORTS	";
		String subServiceName1 = "Badminton Doubles";
		String subServiceName2 = "BABOLAT HYBRID SHUTTLES";

		adminTests.verifyReports(adminUrl, userName, password, mCode, JSON_FILE_PATH, serviceName1, serviceName2,
				subServiceName1, subServiceName2);
	}
	@Test
	public void entryForBadMintonWithMemberandSubMember() throws InterruptedException, IOException, ParseException {
		String mCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode4");
		String otherServiceNameInBadmintonSports = "Babolat Hybrid Shuttles";

		loginPage.login("Admin", "123");
		Thread.sleep(4000);
		activityPoslocators.codeEntry().sendKeys(mCode);
		activityPoslocators.submitCode().click();
		Thread.sleep(5000);
		activityPoslocators.addSubMember();		
		activityPoslocators.gateEntryForGuest().click();
		activityPoslocators.proceedButton().click();
		activityPoslocators.proceedButton().click();
		Thread.sleep(2000);
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(4000);
		activityPoslocators.badmintonSports().click();
		activityPoslocators.waitForSomeTime();
		activityPoslocators.codeEntry().sendKeys(mCode);
		activityPoslocators.submitCode().click();
		Thread.sleep(5000);
		activityPoslocators.addSubMember();	
		activityPoslocators.otherServiceNameInHeathClub(otherServiceNameInBadmintonSports);
		activityPoslocators.waitForSomeTime();
		activityPoslocators.addedServiceName(otherServiceNameInBadmintonSports);
		activityPoslocators.proceedButton().click();
		activityPoslocators.memberAccountOption().click();
		activityPoslocators.yesOption().click();
		activityPoslocators.okayOption().click();
		Thread.sleep(3000);
		activityPoslocators.waitForSomeTime();
		Map<String, String> dbData = commonMethods.getDataFromDataBase(mCode);
		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
		Thread.sleep(3000);
		driver.closeApp();
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		webDriver = new ChromeDriver(options);

		adminTests = new AdminWeb(webDriver);
		String serviceName1 = "BADMINTON SPORTS";
		String serviceName2 = "BADMINTON SPORTS	";
		String subServiceName1 = "Badminton Doubles";
		String subServiceName2 = "BABOLAT HYBRID SHUTTLES";

		adminTests.verifyReports(adminUrl, userName, password, mCode, JSON_FILE_PATH, serviceName1, serviceName2,
				subServiceName1, subServiceName2);
	}
}

