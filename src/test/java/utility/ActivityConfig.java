package utility;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

public class ActivityConfig {

    private static JSONObject activityJson;

    // Load JSON once
    static {
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/activities.json";
            String content = new String(Files.readAllBytes(Paths.get(path)));
            activityJson = new JSONObject(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load activities.json", e);
        }
    }

    // Get service dynamically
    public String getService(String club, String activity) {
        return activityJson.getJSONObject(club)
                           .getJSONObject(activity)
                           .getString("service");
    }

    // Get sub-service dynamically
    public String getSubService(String club, String activity) {
        return activityJson.getJSONObject(club)
                           .getJSONObject(activity)
                           .getString("subservice");
    }
}
