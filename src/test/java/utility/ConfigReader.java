package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;

    // Load ONCE when first time someone calls get()
    private static void loadProperties() {
        if (prop == null) {   // prevent reloading
            try {
                prop = new Properties();
                FileInputStream fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\data.properties"
                );
                prop.load(fis);
                System.out.println("Properties Loaded Successfully");
            } catch (IOException e) {
                throw new RuntimeException("Unable to load data.properties file!", e);
            }
        }
    }

    public static String get(String key) {
        loadProperties();                 // <-- Important!
        return prop.getProperty(key);     // No more NullPointerException
    }
    
    
    
    
    
    
    
    
    // In future //
    
    public static String get2(String key) {
        loadProperties();   // Load properties if not already loaded

        String value = prop.getProperty(key);

        // Check for placeholders like ${SOME_ENV} and replace with system property / environment variable
        if (value != null && value.startsWith("${") && value.endsWith("}")) {
            String envKey = value.substring(2, value.length() - 1); // remove ${}
            String envValue = System.getProperty(envKey, System.getenv(envKey));
            if (envValue != null) {
                return envValue;
            } else {
                System.out.println("⚠ Warning: Environment variable or system property '" + envKey + "' not found!");
            }
        }

        return value;
    }

    /**
     * Get active module.
     * This is useful when you want to run tests for a specific module dynamically.
     *
     * @return Active module name from system property (-Dmodule=ModuleName) or from data.properties
     */
    public static String getActiveModule() {
        // System property has higher priority for local/pipeline execution
        String moduleFromSystem = System.getProperty("module");
        if (moduleFromSystem != null && !moduleFromSystem.isEmpty()) {
            return moduleFromSystem;
        }
        // fallback to config file
        return get("activeModule");
    }

    
}

