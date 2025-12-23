package eCubeUI;

import io.appium.java_client.MobileElement;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import pageObjectsPages.ActivityPOSLocators;
import pageObjectsPages.AdminWeb;
import pageObjectsPages.CommonMethodsForActivityPOS;
import pageObjectsPages.LoginPage;
import utility.ConfigReader;
import utility.DataPaths;
import utility.JsonReader;
import utility.ModuleConfig;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
	public WebDriver webDriver = null;

	private LoginPage loginPage;
	private ActivityPOSLocators activityPoslocators;
	private CommonMethodsForActivityPOS commonMethods;
	private AdminWeb adminTests;

	LocalDate today = LocalDate.now();
	int dayOfMonth = today.getDayOfMonth();
	
	 String activeModule = ConfigReader.get("clubName");

	public eCubeActivityPOS(WindowsDriver driver,WebDriver webDriver) {
	    this.driver = driver;
	    this.commonMethods = new CommonMethodsForActivityPOS(driver);
	    this.loginPage = new LoginPage(driver);
	    this.activityPoslocators = new ActivityPOSLocators(driver);
	   
	}

	
	
	 public void gateEntryNSCIWithMember() throws InterruptedException, IOException, ParseException {

		 String memberCode = JsonReader.getValue(DataPaths.Amount_Data, "memberCode1");
	        loginPage.login(ModuleConfig.ecubeActivityPosUserName, ModuleConfig.ecubeActivityPosPassword);

	        Thread.sleep(4000);
//	        activityPoslocators.codeEntry().sendKeys(memberCode);
//	        activityPoslocators.submitCode().click();
//	        activityPoslocators.guestCount().click();
//	        activityPoslocators.guestName().sendKeys(guestName);
//	        activityPoslocators.addButton().click();
//	        String guestUserName = activityPoslocators.guestlistBox().getText();
//	        if (guestUserName.contains(guestName)) {
//	            System.out.println("✅ GuestName added Successfully (NSCI)");
//	        } else {
//	            System.out.println("❌ GuestName not found for NSCI!");
//	        }

//	        activityPoslocators.gateEntryForGuest().click();
//	        activityPoslocators.proceedButton().click();
//	        activityPoslocators.proceedButton().click();

	        Thread.sleep(2000);
//	        activityPoslocators.memberAccountOption().click();
	        Thread.sleep(3000);
//	        activityPoslocators.yesOption().click();
//     		activityPoslocators.okayOption().click();
	        Map<String, String> dbData = commonMethods.getDataFromDataBase(memberCode);
	        commonMethods.writeJsonSimple(dbData, DataPaths.GATE_ENTRY_JSON);
	        String JSON_FILE_PATH = DataPaths.GATE_ENTRY_JSON;
	        driver.closeApp();

	        WebDriverManager.chromedriver().setup();
	        ChromeOptions options = new ChromeOptions();
	        options.addArguments("start-maximized");
	        webDriver = new ChromeDriver(options);

	        adminTests = new AdminWeb(driver);
	        String serviceName1 = "Gate Entry For Member";
			String serviceName2 = "Gate Entry For Guest";
			String subServiceName1 = "Gate Entry For Member";
			String subServiceName2 = "Gate Entry For Guest";

	        adminTests.verifyReports(ModuleConfig.adminUrl, ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword, memberCode, JSON_FILE_PATH, serviceName1, serviceName2,
					subServiceName1, subServiceName2);
	    }


	    // ---------------------------------------------------------------------
	    // ✅ Function 2 – Karnavati Flow
	    // ---------------------------------------------------------------------
//	    public void gateEntryKarnavatiWithMember() throws InterruptedException, IOException, ParseException {
//
//	    	String memberCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode1");
//			loginPage.login(ModuleConfig.ecubeActivityPosUserName, ModuleConfig.ecubeActivityPosPassword);
//
//			Thread.sleep(4000);
//			activityPoslocators.codeEntry().sendKeys(memberCode);
//			activityPoslocators.submitCode().click();
//			activityPoslocators.guestCount().click();
//			activityPoslocators.guestName().sendKeys(guestName);
//			activityPoslocators.addButton().click();
//			String guestUserName = activityPoslocators.guestlistBox().getText();
//
//			if (guestUserName.contains(guestName)) {
//				System.out.println("✅ The GuestName added Successfully!");
//				Thread.sleep(2000);
//			} else {
//				System.out.println("❌ GuestName not found!");
//			}
//
////			activityPoslocators.gateEntryForGuest().click();
//			activityPoslocators.gateEntryForGuest2().click();;
//			activityPoslocators.proceedButton().click();
//			activityPoslocators.proceedButton().click();
//			Thread.sleep(2000);	
////			activityPoslocators.memberAccountOption().click();
//			// add  
//			activityPoslocators.clickOnOkButton();
//			
////			activityPoslocators.yesOption().click();
////			activityPoslocators.okayOption().click();
//			Thread.sleep(3000);
//			activityPoslocators.waitForSomeTime();
//			Map<String, String> dbData = commonMethods.getKarnavatiDataFromDataBase(memberCode);
//			commonMethods.writeJsonSimple(dbData, DataPaths.GATE_ENTRY_JSON);
////			commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
//			Thread.sleep(3000);
//			driver.closeApp();
////			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
//			WebDriverManager.chromedriver().setup();
//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("start-maximized");
//			webDriver = new ChromeDriver(options);
//
//			adminTests = new AdminWeb(webDriver);
//			String serviceName1 = "Gate Entry For Member";
//			String serviceName2 = "Gate Entry For Guest";
//			String subServiceName1 = "Gate Entry For Member";
//			String subServiceName2 = "Gate Entry For Guest";
//			adminTests.verifyReports(ModuleConfig.adminUrl, ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword, memberCode, JSON_FILE_PATH, serviceName1, serviceName2,
//					subServiceName1, subServiceName2);
//	    }


	    // ---------------------------------------------------------------------
	    // ✅ SWITCH-CASE FUNCTION
	    // ---------------------------------------------------------------------
	    public void handleClubGateEntry(String ClubName) throws InterruptedException, IOException, ParseException {

	        switch (ClubName.toLowerCase()) {

	            case "nsci":
	                System.out.println("➡ Running NSCI Gate Entry Flow");
	                gateEntryNSCIWithMember();
	                break;

	            case "karnavati":
	                System.out.println("➡ Running Karnavati Gate Entry Flow");
//	                gateEntryKarnavatiWithMember();
	                break;

	            default:
	                System.out.println("❌ Unknown club name: " + ClubName);
	        }
	    }

//	
//	public void gateEntryWithMember() throws InterruptedException, IOException, ParseException {
//		String memberCode = JsonReader.getValue(DataPaths.DATA_JSON, "memberCode1");
//		loginPage.login(ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword);
//
//		Thread.sleep(4000);
//		activityPoslocators.codeEntry().sendKeys(memberCode);
//		activityPoslocators.submitCode().click();
//		activityPoslocators.guestCount().click();
//		activityPoslocators.guestName().sendKeys(guestName);
//		activityPoslocators.addButton().click();
//		String guestUserName = activityPoslocators.guestlistBox().getText();
//
//		if (guestUserName.contains(guestName)) {
//			System.out.println("✅ The GuestName added Successfully!");
//			Thread.sleep(2000);
//		} else {
//			System.out.println("❌ GuestName not found!");
//		}
//
////		activityPoslocators.gateEntryForGuest().click();
//		activityPoslocators.gateEntryForGuest2().click();;
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.proceedButton().click();
//		Thread.sleep(2000);	
//		activityPoslocators.memberAccountOption().click();
//		// add  
//		activityPoslocators.clickOnOkButton();
//		
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(3000);
//		activityPoslocators.waitForSomeTime();
//		Map<String, String> dbData = commonMethods.getDataFromDataBase(memberCode);
//   	commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
//		commonMethods.writeJsonSimple(dbData, DataPaths.GATE_ENTRY_JSON);
//		Thread.sleep(3000);
//		driver.closeApp();
// 		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
//		WebDriverManager.chromedriver().setup();
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized");
//		webDriver = new ChromeDriver(options);
//
//		adminTests = new AdminWeb(webDriver);
//		String serviceName1 = "Gate Entry For Member";
//		String serviceName2 = "Gate Entry For Guest";
//		String subServiceName1 = "Gate Entry For Member";
//		String subServiceName2 = "Gate Entry For Guest";
//		adminTests.verifyReports(ModuleConfig.adminUrl, ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword, memberCode, JSON_FILE_PATH, serviceName1, serviceName2,
//				subServiceName1, subServiceName2);
//	}

//	public void entryForHealthClub() throws InterruptedException, IOException, ParseException {
//		String mCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode2");
//		String guestNameDetails = "CypressGuest";
//		String serviceNameInHealthClub = "Extra Virgin Olive Oil 50ml";
//
//		loginPage.login(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
//		Thread.sleep(4000);
//		activityPoslocators.codeEntry().sendKeys(mCode);
//		activityPoslocators.submitCode().click();
//		activityPoslocators.guestCount().click();
//		activityPoslocators.guestName().sendKeys(guestNameDetails);
//		activityPoslocators.addButton().click();
//		activityPoslocators.gateEntryForGuest().click();
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.proceedButton().click();
//		Thread.sleep(2000);
//		activityPoslocators.memberAccountOption().click();
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(4000);
//		activityPoslocators.healthClubForGents().click();
//		activityPoslocators.waitForSomeTime();
//		activityPoslocators.codeEntry().sendKeys(mCode);
//		activityPoslocators.submitCode().click();
//		activityPoslocators.guestCount().click();
//		activityPoslocators.guestName().sendKeys(guestNameDetails);
//		activityPoslocators.addButton().click();
//		String guestUserName = activityPoslocators.guestlistBox().getText();
//
//		if (guestUserName.contains(guestNameDetails)) {
//			System.out.println("✅ The GuestName added Successfully!");
//			Thread.sleep(2000);
//		} else {
//			System.out.println("❌ GuestName not found!");
//		}
//
//		activityPoslocators.otherServiceNameInHeathClub(serviceNameInHealthClub);
//		activityPoslocators.waitForSomeTime();
//		activityPoslocators.addedServiceName(serviceNameInHealthClub);
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.memberAccountOption().click();
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(3000);
//		activityPoslocators.waitForSomeTime();
//		Map<String, String> dbData = commonMethods.getDataFromDataBase(mCode);
//		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
//		Thread.sleep(3000);
//		driver.closeApp();
//		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
//		WebDriverManager.chromedriver().setup();
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized");
//		webDriver = new ChromeDriver(options);
//
//		adminTests = new AdminWeb(webDriver);
//		String serviceName1 = "HEALTH CLUB GENTS";
//		String serviceName2 = "HEALTH CLUB GENTS";
//		String subServiceName1 = "DAILY MEMBER’S CHARGES HCG";
//		String subServiceName2 = "EXTRA VIRGIN OLIVE OIL 50ML";
//
//		adminTests.verifyReports(ModuleConfig.adminUrl, ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword, mCode, JSON_FILE_PATH, serviceName1, serviceName2,
//				subServiceName1, subServiceName2);
//	}

//	public void entryForBadminton() throws InterruptedException, IOException, ParseException {
//		String mCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode3");
//		String guestNameDetails = "SportsUser";
//		String otherServiceNameInBadmintonSports = "Babolat Hybrid Shuttles";
//
//		loginPage.login(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
//		Thread.sleep(4000);
//		activityPoslocators.codeEntry().sendKeys(mCode);
//		activityPoslocators.submitCode().click();
//		activityPoslocators.guestCount().click();
//		activityPoslocators.guestName().sendKeys(guestNameDetails);
//		activityPoslocators.addButton().click();
//		activityPoslocators.gateEntryForGuest().click();
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.proceedButton().click();
//		Thread.sleep(2000);
//		activityPoslocators.memberAccountOption().click();
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(4000);
//		activityPoslocators.badmintonSports().click();
//		activityPoslocators.waitForSomeTime();
//		activityPoslocators.codeEntry().sendKeys(mCode);
//		activityPoslocators.submitCode().click();
//		activityPoslocators.guestCount().click();
//		activityPoslocators.guestName().sendKeys(guestNameDetails);
//		activityPoslocators.addButton().click();
//		String guestUserName = activityPoslocators.guestlistBox().getText();
//
//		if (guestUserName.contains(guestNameDetails)) {
//			System.out.println("✅ The GuestName added Successfully!");
//			Thread.sleep(2000);
//		} else {
//			System.out.println("❌ GuestName not found!");
//		}
//
//		activityPoslocators.otherServiceNameInHeathClub(otherServiceNameInBadmintonSports);
//		activityPoslocators.waitForSomeTime();
//		activityPoslocators.addedServiceName(otherServiceNameInBadmintonSports);
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.memberAccountOption().click();
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(3000);
//		activityPoslocators.waitForSomeTime();
//		Map<String, String> dbData = commonMethods.getDataFromDataBase(mCode);
//		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
//		Thread.sleep(3000);
//		driver.closeApp();
////		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
//		WebDriverManager.chromedriver().setup();
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized");
//		webDriver = new ChromeDriver(options);
//
//		adminTests = new AdminWeb(webDriver);
//		String serviceName1 = "BADMINTON SPORTS";
//		String serviceName2 = "BADMINTON SPORTS	";
//		String subServiceName1 = "Badminton Doubles";
//		String subServiceName2 = "BABOLAT HYBRID SHUTTLES";
//
//		adminTests.verifyReports(ModuleConfig.adminUrl, ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword, mCode, JSON_FILE_PATH, serviceName1, serviceName2,
//				subServiceName1, subServiceName2);
//	}
//	
//	public void entryForBadMintonWithMemberandSubMember() throws InterruptedException, IOException, ParseException {
//		String mCode = commonMethods.getMemberCode(DATA_JSON_FILE_PATH, "memberCode4");
//		String otherServiceNameInBadmintonSports = "Babolat Hybrid Shuttles";
//
//		loginPage.login(ModuleConfig.ecubeAdminUserName,ModuleConfig.ecubeAdminPassword);
//		Thread.sleep(4000);
//		activityPoslocators.codeEntry().sendKeys(mCode);
//		activityPoslocators.submitCode().click();
//		Thread.sleep(5000);
//		activityPoslocators.addSubMember();		
//		activityPoslocators.gateEntryForGuest().click();
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.proceedButton().click();
//		Thread.sleep(2000);
//		activityPoslocators.memberAccountOption().click();
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(4000);
//		activityPoslocators.badmintonSports().click();
//		activityPoslocators.waitForSomeTime();
//		activityPoslocators.codeEntry().sendKeys(mCode);
//		activityPoslocators.submitCode().click();
//		Thread.sleep(5000);
//		activityPoslocators.addSubMember();	
//		activityPoslocators.otherServiceNameInHeathClub(otherServiceNameInBadmintonSports);
//		activityPoslocators.waitForSomeTime();
//		activityPoslocators.addedServiceName(otherServiceNameInBadmintonSports);
//		activityPoslocators.proceedButton().click();
//		activityPoslocators.memberAccountOption().click();
//		activityPoslocators.yesOption().click();
//		activityPoslocators.okayOption().click();
//		Thread.sleep(3000);
//		activityPoslocators.waitForSomeTime();
//		Map<String, String> dbData = commonMethods.getDataFromDataBase(mCode);
//		commonMethods.writeJsonSimple(dbData, JSON_FILE_PATH);
//		Thread.sleep(3000);
//		driver.closeApp();
////		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
//		WebDriverManager.chromedriver().setup();
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized");
//		webDriver = new ChromeDriver(options);
//
//		adminTests = new AdminWeb(webDriver);
//		String serviceName1 = "BADMINTON SPORTS";
//		String serviceName2 = "BADMINTON SPORTS	";
//		String subServiceName1 = "Badminton Doubles";
//		String subServiceName2 = "BABOLAT HYBRID SHUTTLES";
//
//		adminTests.verifyReports(ModuleConfig.adminUrl, ModuleConfig.ecubeAdminUserName, ModuleConfig.ecubeAdminPassword, mCode, JSON_FILE_PATH, serviceName1, serviceName2,
//				subServiceName1, subServiceName2);
//	}
}

