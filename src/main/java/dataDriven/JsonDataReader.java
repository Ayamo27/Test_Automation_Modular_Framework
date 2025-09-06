package dataDriven;

import com.google.gson.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Utility class to read data from JSON files located in the resources folder.
 * Uses Gson library to parse JSON and java.util.logging.Logger for logging.
 */
public class JsonDataReader {
    private static final Logger logger = Logger.getLogger(JsonDataReader.class.getName());

    /** Base path where JSON test data files are stored. */
    public final static String testDataFilePath = "src/test/resources/";

    /**
     * Retrieves the value of a specific field from a JSON file.
     * Example usage:
     * <pre>
     * JsonElement element = JsonDataReader.getJsonData("testData", "bingUrl");
     * String url = element.getAsString();
     * </pre>
     *
     * @param jsonFileName the name of the JSON file (without .json extension)
     * @param field the top-level field to retrieve
     * @return the JsonElement representing the value of the field
     * @throws IOException if the file cannot be read or has invalid JSON syntax
     * @throws IllegalArgumentException if the specified field is not found
     */

    public static JsonElement getJsonData(String jsonFileName, String field) throws IOException {
        try (FileReader reader = new FileReader(testDataFilePath + jsonFileName + ".json")) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject.has(field)) {
                JsonElement value = jsonObject.get(field);
                logger.info(String.format("Retrieved JSON field '%s' from '%s.json': %s", field, jsonFileName, value));
                return value;
            } else {
                String msg = String.format("Field '%s' not found in '%s.json'", field, jsonFileName);
                logger.warning(msg);
                throw new IllegalArgumentException(msg);
            }
        } catch (JsonSyntaxException e) {
            String msg = String.format("Invalid JSON syntax in '%s.json': %s", jsonFileName, e.getMessage());
            logger.severe(msg);
            throw new IOException(msg, e);
        }
    }

}
