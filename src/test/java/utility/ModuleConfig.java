package utility;

import java.util.Arrays;
import java.util.List;

public class ModuleConfig {
 
    public static String clubName;
    public static String adminUrl;
    public static String ecubeAdminUserName;
    public static String ecubeAdminPassword;
    public static String ecubeAppPath;
    public static String ecubeActivityPosUserName;
    public static String ecubeActivityPosPassword;
    public static String ecubeMemberCode1;
    public static String ecubeMemberCode2;
    public static String ecubeMemberCode3;
    public static String ecubeMemberCode4;
    public static String ecubeGuestName;
    public static String serviceName;
    public static String dbUrl;
    public static String dbUsername;
    public static String dbPassword;
    public static String reportSection;
    public static String financesTab;
    public static String financeMemberReport;
    
    
    //  Get all available clubName and its respective data from data.properties
    
    public static void loadModule() 
    {
    	clubName = ConfigReader.get("clubName");
    	 
    	 if (clubName == null || clubName.isEmpty()) {
             throw new RuntimeException("Active module is not defined! Provide -Dmodule=ModuleName or set in data.properties");
         }


         adminUrl = ConfigReader.get(clubName + ".adminUrl");
         ecubeAdminUserName = ConfigReader.get(clubName + ".ecubeAdminUserName");
         ecubeAdminPassword = ConfigReader.get(clubName + ".ecubeAdminPassword");
         ecubeAppPath = ConfigReader.get(clubName + ".ecubeAppPath");
         ecubeActivityPosUserName = ConfigReader.get(clubName + ".activityPosUserName");
         ecubeActivityPosPassword = ConfigReader.get(clubName + ".activityPosPassword");
         ecubeMemberCode1 = ConfigReader.get(clubName + ".memberCode1");
         ecubeMemberCode2 = ConfigReader.get(clubName + ".memberCode2");
         ecubeMemberCode3 = ConfigReader.get(clubName + ".memberCode3");
         ecubeMemberCode4 = ConfigReader.get(clubName + ".memberCode4");
         ecubeGuestName       = ConfigReader.get(clubName + ".clubGuestName");
         serviceName =ConfigReader.get(clubName + ".serviceName");
         dbUrl = ConfigReader.get(clubName + ".dbUrl");
         dbUsername = ConfigReader.get(clubName + ".dbUsername");
         dbPassword = ConfigReader.get(clubName + ".dbPassword");
         reportSection = ConfigReader.get(clubName + ".reportSection");
         financesTab = ConfigReader.get(clubName + ".financesTab");
         financeMemberReport = ConfigReader.get(clubName + ".financeMemberReport");
    }
    
    
    
    
    
    
    /**
     * Optional: Get all available modules from data.properties
     * Useful if you want to loop through all modules in future pipeline runs.
     * Example in data.properties: modules=ActivityPOS,HealthClub,Badminton
     *
     * @return List of module names
     */
    public static List<String> getAllModules() {
        String modules = ConfigReader.get("modules"); // comma-separated module names
        if (modules != null && !modules.isEmpty()) {
            return Arrays.asList(modules.split(","));
        } else {
            throw new RuntimeException("❌ No modules defined in data.properties for 'modules' key!");
        }
    }

	
}
