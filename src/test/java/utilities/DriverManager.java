package utilities;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

//    private static WindowsDriver windowsDriver;
//    private static WebDriver webDriver;

    private static ThreadLocal<WindowsDriver<WindowsElement>> windowsDriver = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    // ----------------------
    // WindowsDriver
    // ----------------------
//    public static void setWindowsDriver(WindowsDriver driver) {
//        windowsDriver = driver;
//    }
//
//    public static WindowsDriver getWindowsDriver() {
//        return windowsDriver;
//    }
    
    public static void setWindowsDriver(WindowsDriver<WindowsElement> driver) {
        windowsDriver.set(driver);
    }
    
    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }
  

    public static WindowsDriver<WindowsElement> getWindowsDriver() {
        return windowsDriver.get();
    }

//    public static void quitWindowsDriver() {
//        if (windowsDriver != null) {
//            windowsDriver.quit();
//            windowsDriver = null;
//        }
//    }

//    // ----------------------
//    // Chrome WebDriver
//    // ----------------------
//    public static WebDriver getWebDriver() {
//        if (webDriver == null) {
//            WebDriverManager.chromedriver().setup();
//            webDriver = new ChromeDriver();
//            webDriver.manage().window().maximize();
//        }
//        return webDriver;
//    }
//
//    public static void quitWebDriver() {
//        if (webDriver != null) {
//            webDriver.quit();
//            webDriver = null;
//        }
//    }
//
//    // ----------------------
//    // Detect active driver
//    // ----------------------
//    public static boolean isWindowsDriverActive() {
//        return windowsDriver != null;
//    }
//
//    public static boolean isWebDriverActive() {
//        return webDriver != null;
//    }

    // ===== WEB DRIVER =====
    public static WebDriver getWebDriver() {
        if (webDriver.get() == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            webDriver.set(new ChromeDriver(options));
        }
        return webDriver.get();
    }

    public static void quitAll() {
        if (windowsDriver.get() != null) {
            windowsDriver.get().quit();
            windowsDriver.remove();
        }
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
    }
}

