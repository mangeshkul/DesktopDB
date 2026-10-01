package utility;

import org.junit.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class singeltone {
	
//     ===========================================================================================	
	
//	"Singleton is a creational design pattern that
//	ensures a class has only one instance throughout the application and 
//	provides a global access point to that instance."
	
//	========================================================================================================
	
	
	// SIngeltone class is kind of design pattern where class which has only one instance through out the application, is created, not able to create object of same class from outside of the class
	
	//for this 
	
	// create private static variable and declarre instance
	// private constructor 
	// public method to access tthe class 
	
//	private static singeltone instance = new singeltone(); // early initialization, thread safe, more memory consumed , not preferable
	private static singeltone instance;  // lazy initialization, not thread safe, use synchronized to handel thread safe, less memory consumed ,  preferable
	private singeltone() {};
	
	public static synchronized singeltone getinstance() {

	    if (instance == null) {
	        instance = new singeltone();
	    }

	    return instance;
	}
	
	public String getBrowser(String browser) {
		System.out.println(browser);
		return browser;
	}
public static void main(String[] args) {
	
	singeltone obj1 =singeltone.getinstance();
	singeltone obj2 = singeltone.getinstance();
	
	
	System.out.println(obj1 == obj2);
	
	singletone_practical.getdriver().get(null);
	
	ExtentReports report = ReportSingeltone.getReport();
	ExtentTest test =report.createTest("login test");
	test.pass("login successfull");
	}



}
