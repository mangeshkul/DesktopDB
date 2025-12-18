package utilities;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {


    private static ThreadLocal<WindowsDriver<WindowsElement>> windowsDriver = new ThreadLocal<>();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    
    public static void setWindowsDriver(WindowsDriver<WindowsElement> driver) {
        windowsDriver.set(driver);
    }

    public static WindowsDriver<WindowsElement> getWindowsDriver() {
        return windowsDriver.get();
    }
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

