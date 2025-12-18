package utility;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {

    private static JSONObject jsonObject;

    public static String getValue(String filePath, String key) {
        try {
            if (jsonObject == null) {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                JSONParser parser = new JSONParser();
                jsonObject = (JSONObject) parser.parse(content);
            }
            return (String) jsonObject.get(key);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read JSON key: " + key, e);
        }
    }
}
