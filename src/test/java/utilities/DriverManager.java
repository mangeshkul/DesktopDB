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

    // =========================
    // WINDOWS DRIVER
    // =========================
    public static void setWindowsDriver(WindowsDriver<WindowsElement> driver) {
        windowsDriver.set(driver);
    }

    public static WindowsDriver<WindowsElement> getWindowsDriver() {
        return windowsDriver.get();
    }

    // =========================
    // WEB DRIVER
    // =========================
    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static WebDriver getWebDriver() {
        return webDriver.get();
    }

    // =========================
    // CREATE CHROME DRIVER
    // (optional helper)
    // =========================
    public static void initChromeDriver() {
        if (webDriver.get() == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--incognito");

            WebDriver driver = new ChromeDriver(options);
            webDriver.set(driver);
        }
    }

    // =========================
    // QUIT ALL DRIVERS
    // =========================
    public static void quitAll() {

        try {
            if (windowsDriver.get() != null) {
                windowsDriver.get().quit();
                windowsDriver.remove();
            }

            if (webDriver.get() != null) {
                webDriver.get().quit();
                webDriver.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
