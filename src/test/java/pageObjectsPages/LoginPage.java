package pageObjectsPages;

import org.openqa.selenium.By;
import io.appium.java_client.windows.WindowsDriver;
import utility.ConfigReader;
import utility.ModuleConfig;

import org.openqa.selenium.WebElement;

public class LoginPage {
    private WindowsDriver driver;

    // Locators
    private By usernameField = By.xpath("//*[contains(@HelpText, 'Username')]");
    private By passwordField = By.className("PasswordBox");
    private By loginButton = By.name("LOGIN");

    // Constructor
    public LoginPage(WindowsDriver driver) {
        this.driver = driver;
    }

    // Methods to perform actions
    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }
    
    //  User should be log in as per Club 
    
    public void loginBasedOnClub() {
       switch  (ModuleConfig.clubName.toLowerCase()) {

            case "nsci":
            	 login(ModuleConfig.ecubeActivityPosUserName, ModuleConfig.ecubeActivityPosPassword);
                break;

            case "karnavati":
            	 login(ModuleConfig.ecubeActivityPosUserName, ModuleConfig.ecubeActivityPosPassword);
                break;

            case "calicut":
           	 login(ModuleConfig.ecubeActivityPosUserName, ModuleConfig.ecubeActivityPosPassword);
               break;

            default:
                throw new RuntimeException("❌ Unsupported club: " + ModuleConfig.clubName);
        }


    }

    
}
