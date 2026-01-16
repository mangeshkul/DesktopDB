package pageObjectsPages;

import org.openqa.selenium.By;
import io.appium.java_client.windows.WindowsDriver;
import utility.ConfigReader;
import utility.ModuleConfig;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WindowsDriver driver;

    
    @FindBy(xpath = "//*[contains(@HelpText, 'Username')]")private 
    WebElement usernameField;
    
    @FindBy(className  = "PasswordBox")private 
    WebElement passwordField;
    
    @FindBy(name = "LOGIN")private 
    WebElement loginButton;

    // Constructor
    public LoginPage(WindowsDriver driver) {
    
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Methods to perform actions
    public void login(String username, String password) {
    	usernameField.sendKeys(username);
    	passwordField.sendKeys(password);
    	loginButton.click();
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
