package utilities;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.windows.WindowsDriver;

public class DriverContext {

    private static WindowsDriver windowsDriver;
    private static WebDriver webDriver;

    public static void setWindowsDriver(WindowsDriver driver) {
        windowsDriver = driver;
    }

    public static WindowsDriver getWindowsDriver() {
        return windowsDriver;
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver = driver;
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }
}
