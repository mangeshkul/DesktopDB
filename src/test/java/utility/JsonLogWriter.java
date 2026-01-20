package utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonLogWriter {

    private static final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    public static void writeLastFourRecords(Map<String, String> newRecord,String jsonFilePath) throws IOException {

        File file = new File(jsonFilePath);
        List<Map<String, String>> records = new ArrayList<>();

        //  Read existing records if file exists
        if (file.exists() && file.length() > 0) {
        	
            records = mapper.readValue(
                    file,
                    new TypeReference<List<Map<String, String>>>() {}
            );
        }

        //  Add new record
        records.add(newRecord);

        // Keep only last 4 records
        if (records.size() > 4) {
            records = records.subList(records.size() - 4, records.size());
        }

        // Write back to JSON
        mapper.writeValue(file, records);
    }
}
