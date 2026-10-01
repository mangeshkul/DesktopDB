package utility;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import lombok.Synchronized;

public class singletone_practical {
	
	private static WebDriver driver;
	
	
	// for thread safely purpoes we will use threadlocal - use to keep seperate object for different threads  hence prefern below
	
	private static ThreadLocal<WebDriver> driver1 = new ThreadLocal<>();
	
	// thread - execution path 
	
	private singletone_practical() {
		
	}
	
	// Synchronized is also work but it will not solve the bowser related problem
//	thread 1 acess browser-> get it-> hold wait for finishes -> use existance driver for  thread 2
	
	public static synchronized WebDriver getdrivernormal() {
		if(driver ==null) {
			driver = new ChromeDriver();
			
		}
		return driver;
	}
	
	
	///  ---------------------------------------  BELOW is with thread safety using ThreadLocal<>
	public static WebDriver getdriver() {
		if(driver1.get() == null) {
			driver1.set(new ChromeDriver());
		}
		return driver1.get();
	}
	
	
	
	

	}
	
	
	
	
	


