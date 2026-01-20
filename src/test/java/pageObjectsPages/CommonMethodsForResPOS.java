package pageObjectsPages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.DesktopWindow;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CommonMethodsForResPOS {
	private WindowsDriver driver;

	String url = "jdbc:sqlserver://RYSUN-MSSQL-02.rysun.com;" + "instanceName=SQL2016;" + "databaseName=NSCI_QA;"
			+ "encrypt=true;" + "trustServerCertificate=true;";

	private String user = "kishor";
	private String pass = "xraxjsSL9P1z";

	// Constructor
	public CommonMethodsForResPOS(WindowsDriver driver) {
		this.driver = driver;
	}
	
	public void waitForSomeTime() throws InterruptedException {
        Thread.sleep(3000); 
    }

	public String getWindowHandleHex(String processName) throws Exception {
        String command = "powershell -command \"(Get-Process " + processName + ").MainWindowHandle.ToString('X')\"";
        Process p = Runtime.getRuntime().exec(command);
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        return reader.readLine();
    }
    
    public void waitForWindowAndSwitch() {
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            driver.switchTo().window(handle);
        }
    }
    
    private void waitForNewWindowAndSwitch(int previousCount, int timeoutSeconds) throws InterruptedException {
        int elapsed = 0;
        while (elapsed < timeoutSeconds) {
            Set<String> handles = driver.getWindowHandles();
            if (handles.size() > previousCount) {
                for (String handle : handles) {
                	driver.switchTo().window(handle);
                }
                System.out.println("✅ Switched to new dialog window.");
                return;
            }
            Thread.sleep(1000);
            elapsed++;
        }
        throw new RuntimeException("❌ New window did not appear within timeout.");
    }
    
    public WindowsDriver attachToMainPOS(String previousHandle) throws Exception {

        DesiredCapabilities rootCap = new DesiredCapabilities();
        rootCap.setCapability("app", "Root");

        WindowsDriver root = new WindowsDriver(
            new URL("http://127.0.0.1:4723"), rootCap);

        WebElement main = null;
        String newHandle = null;

        // Wait for window with NEW handle
        for (int i = 0; i < 30; i++) {
            try {
                main = root.findElement(By.name("eHSM Restaurant POS"));
                String nativeHandle = main.getAttribute("NativeWindowHandle");
                newHandle = Integer.toHexString(Integer.parseInt(nativeHandle));

                if (!newHandle.equalsIgnoreCase(previousHandle)) {
                    System.out.println("🟢 New POS Window Detected: " + newHandle);
                    break;
                }

            } catch (Exception e) {
                // ignore
            }

            Thread.sleep(500);
        }

        if (newHandle == null || newHandle.equals(previousHandle)) {
            throw new RuntimeException("Main POS window did not refresh with new handle");
        }

        DesiredCapabilities appCap = new DesiredCapabilities();
        appCap.setCapability("appTopLevelWindow", newHandle);

        return new WindowsDriver(
            new URL("http://127.0.0.1:4723"), appCap);
    }
    
    public void writeJsonSimple(Map<String, String> data, String filePath) {
		try (FileWriter writer = new FileWriter(filePath)) {

			writer.write("{\n");

			int count = 0;
			int size = data.size();

			for (Map.Entry<String, String> entry : data.entrySet()) {
				writer.write("  \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
				count++;
				if (count < size)
					writer.write(",");
				writer.write("\n");
			}

			writer.write("}");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("❌ Failed to write JSON!");
		}
	}


}