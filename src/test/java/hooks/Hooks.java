package hooks;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import utilities.DriverManager;
import utility.ModuleConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hooks {

    public static WindowsDriver driver;
    public static WebDriver webDriver;

    // =========================
    // CONFIG LOAD
    // =========================
    @Before(order = 0)
    public void initConfig() {
        ModuleConfig.loadModule();
        System.out.println("Running for Active Module -> " + ModuleConfig.clubName);
    }

    // =========================
    // WINDOWS UTILITIES (UNCHANGED)
    // =========================
    public void printAllWindows() {
        List<DesktopWindow> windows = WindowUtils.getAllWindows(true);
        for (DesktopWindow w : windows) {
            if (w.getTitle() != null && !w.getTitle().trim().isEmpty()) {
                // debug only
            }
        }
    }

    public String getWindowHandleHex(String processName) throws Exception {
        String command = "powershell -command \"(Get-Process " + processName +
                ").MainWindowHandle.ToString('X')\"";
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        return reader.readLine();
    }
    
    
    public void launchApplication(String exePath) throws Exception {
        File exe = new File(exePath);
        if (!exe.exists()) {
            throw new RuntimeException("❌ EXE Not found: " + exePath);
        }

        ProcessBuilder pb = new ProcessBuilder(exe.getAbsolutePath());
        pb.directory(exe.getParentFile());
        pb.start();
        

      Thread.sleep(4000);
        printAllWindows();
    }

    // =========================
    // 🔹 WINDOWS DRIVER LAUNCH
    // =========================

   
    public void setupDrivers() throws Exception {
    	if (DriverManager.getWindowsDriver() != null) {
            return;
        }
      
    }


    
    @Before
    public void launchWindowsApp() throws Exception {
        if (DriverManager.getWindowsDriver() != null) {
            return;
        }

        launchApplication(ModuleConfig.ecubeAppPath);

        String windowHandle = getWindowHandleHex("eCubePOS");
        if (windowHandle == null || windowHandle.isEmpty()) {
            throw new RuntimeException("Could not get MainWindowHandle for eCubePOS");
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("appTopLevelWindow", "0x" + windowHandle);
        caps.setCapability("platformName", "Windows");
        caps.setCapability("deviceName", "WindowsPC");

        driver = new WindowsDriver(
                new URL("http://127.0.0.1:4723"), caps);

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        DriverManager.setWindowsDriver(driver);

        System.out.println("✅ Windows POS launched");
    }
    
    private void waitForWindowsToLaunch(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver,timeoutSeconds);
        wait.until(driver -> driver.getWindowHandles().size() > 0);
    }
    // =========================
    // 🔹 WEB DRIVER LAUNCH
    // =========================
    
    public WebDriver launchBrowser() {

        WebDriver driver = DriverManager.getWebDriver();

        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);

            // ✅ REGISTER DRIVER
            DriverManager.setWebDriver(driver);

            System.out.println("✅ Web browser launched successfully");
        }

        return driver;
    }

    // =========================
    // TEARDOWN
    // =========================
 @After
  public void tearDown(Scenario scenario) {

        try {
            if (scenario.isFailed()&&driver != null) {
                byte[] src = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(src, "image/png", scenario.getName());
                driver.close() ;
            }
  
    if (scenario.isFailed()&&webDriver != null) {
        byte[] src = ((TakesScreenshot) webDriver)
                .getScreenshotAs(OutputType.BYTES);
        scenario.attach(src, "image/png", "Web - " + scenario.getName());
        webDriver.close() ;
    }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DriverManager.quitAll();
            System.out.println("All drivers closed");
        }
    }

 @BeforeSuite
 public void setDynamicPdfReportName() {
     String clubName = ModuleConfig.clubName; // e.g. CALICUT
     String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

     String pdfPath = "test-output-extent/PdfReport/EcubePOS_"
             + clubName + "_" + timestamp + ".pdf";

     System.setProperty("extent.reporter.pdf.out", pdfPath);
 }

  
    }

  
