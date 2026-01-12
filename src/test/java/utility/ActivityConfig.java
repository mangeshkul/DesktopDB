package utility;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class ActivityConfig {

    private static JSONObject activityJson;

    // Load JSON once
    static {
        try {
            String path = DataPaths.CLUB_DATA;
            String content = new String(Files.readAllBytes(Paths.get(path)));
            activityJson = new JSONObject(content);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load activities.json", e);
        }
    }

    // Get service dynamically
//    public String getService(String club, String activity) {
//        return activityJson.getJSONObject(club)
//                           .getJSONObject(activity)
//                           .getString("service");
//    }
    
    public String getService(String club, String activity) {
        JSONArray activities = activityJson
                .getJSONObject(club)
                .getJSONArray("activities");

        for (int i = 0; i < activities.length(); i++) {
            JSONObject obj = activities.getJSONObject(i);
            if (obj.getString("name").equalsIgnoreCase(activity)) {
                return obj.getString("service");
            }
        }
        throw new RuntimeException("Service not found for activity: " + activity);
    }
    
//    public String getServiceForCalicut(String club, String activity) {
//        return activityJson.getJSONObject(club)
//                           .getJSONObject(activity)
//                           .getString("service");
//    }
    
    public String getServiceForCalicut(String club, String activity) {
        JSONArray activities = activityJson
                .getJSONObject(club)
                .getJSONArray("activities");

        for (int i = 0; i < activities.length(); i++) {
            JSONObject obj = activities.getJSONObject(i);
            if (obj.getString("name").equalsIgnoreCase(activity)) {
                return obj.getString("service");
            }
        }
        throw new RuntimeException("Service not found for activity: " + activity);
    }

    // Get sub-service dynamically
//    public String getSubService(String club, String activity) {
//        return activityJson.getJSONObject(club)
//                           .getJSONObject(activity)
//                           .getString("subservice");
//    }
    
    
 // Get sub-service dynamically
    public String getSubService(String club, String activity) {
        JSONArray activities = activityJson
                .getJSONObject(club)
                .getJSONArray("activities");

        for (int i = 0; i < activities.length(); i++) {
            JSONObject obj = activities.getJSONObject(i);
            if (obj.getString("name").equalsIgnoreCase(activity)) {
                return obj.getString("subservice");
            }
        }
        throw new RuntimeException("SubService not found for activity: " + activity);
    }

    
//    public String selectServicesForActivity(String activity,String club) {
//        return activityJson.getJSONObject(club)
//        		           .getJSONObject(activity)
//                           .getString("service");
//    }
    
    public String selectServicesForActivity(String activity, String club) {
    	
        JSONArray activities = activityJson
                .getJSONObject(club.toUpperCase())
                .getJSONArray("activities");

        for (int i = 0; i < activities.length(); i++) {
            JSONObject obj = activities.getJSONObject(i);
            if (obj.getString("name").equalsIgnoreCase(activity)) {
                return obj.getString("service");
            }
        }
        throw new RuntimeException("Service not found for activity: " + activity);
    }

    
//    public List<String> getActivitiesForClub(String club) {
//
//        JSONObject clubObj = activityJson.getJSONObject(club.toUpperCase());
//
//        List<String> activities = new ArrayList<>();
//
//        Iterator<String> keys = clubObj.keys(); // preserves file order
//        while (keys.hasNext()) {
//            activities.add(keys.next());
//        }
//
//        return activities;
//    }
    
    public List<String> getActivitiesForClub(String club) {

        JSONArray activitiesArray = activityJson
                .getJSONObject(club.toUpperCase())
                .getJSONArray("activities");

        List<String> activities = new ArrayList<>();

        for (int i = 0; i < activitiesArray.length(); i++) {
            activities.add(
                activitiesArray
                    .getJSONObject(i)
                    .getString("name")
            );
        }

        return activities;
    }

}
