package utility;

import java.util.Arrays;
import java.util.List;

public class ModuleConfig {
 
    public static String clubName;
    public static String adminUrl;
    public static String ecubeAdminUserName;
    public static String ecubeAdminPassword;
    public static String ecubeAdminappPath;
    
   
    public static void loadModule() 
    {
    	clubName = ConfigReader.get("clubName");
    	 
    	 if (clubName == null || clubName.isEmpty()) {
             throw new RuntimeException("Active module is not defined! Provide -Dmodule=ModuleName or set in data.properties");
         }


         adminUrl = ConfigReader.get(clubName + ".adminUrl");
         ecubeAdminUserName = ConfigReader.get(clubName + ".ecubeAdminUserName");
         ecubeAdminPassword = ConfigReader.get(clubName + ".ecubeAdminPassword");
         ecubeAdminappPath = ConfigReader.get(clubName + ".appPath");
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
