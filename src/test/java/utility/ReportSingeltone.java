package utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportSingeltone {
	
	
	private static ExtentReports reports;
	
	private ReportSingeltone() {
		
	}
	
	public static ExtentReports getReport() {
		if(reports==null) {
			ExtentSparkReporter reporter = new ExtentSparkReporter("");
			
			reports = new ExtentReports();
			
			reports.attachReporter(reporter);
			reports.flush();
		}
		return reports;
	}

	//  “I configure reporting globally in a ReportManager.
//	I create a single ExtentReports instance using Singleton,
	//initialize it in @BeforeSuite, and flush it in @AfterSuite. 
	//For parallel execution, I use ThreadLocal<ExtentTest> so each thread maintains its own test node and avoids reporting conflicts.”
}
