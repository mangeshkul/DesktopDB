package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties prop;
    private static Properties secureProp;

    private static void loadProperties() {
        try {

            if (prop == null) {
                prop = new Properties();
                prop.load(new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\data.properties"
                ));
                System.out.println("data.properties Loaded Successfully");
            }

            if (secureProp == null) {
                secureProp = new Properties();
                secureProp.load(new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\envCreds.properties"
                ));
                System.out.println("secure.properties Loaded Successfully");
            }

        } catch (IOException e) {
            throw new RuntimeException("Unable to load properties!", e);
        }
    }

    public static String get(String key) {
        loadProperties();

        String value = prop.getProperty(key);

        // If value is placeholder like ${KEY}
        if (value != null && value.startsWith("${") && value.endsWith("}")) {
            String secureKey = value.substring(2, value.length() - 1);
            return secureProp.getProperty(secureKey);
        }

        return value;
    }

}
