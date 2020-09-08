import org.json.JSONArray;
import org.json.JSONObject;

public class ParseJson {

    //to process Json structure
    public String getJsonValue(JSONObject json, String jsonPath) {

        String[] subStr;
        subStr = jsonPath.split("/");

        JSONArray arr = json.getJSONArray(subStr[0]);

        for (int i = 1; i < subStr.length - 1; i++) {
            arr = getFutureArray(arr,subStr[i]);
        }

        String result = null;
        for (int i = 0; i < arr.length(); i++)
            result = arr.getJSONObject(i).getString(subStr[subStr.length - 1]);

        return result;

    }

    //to get future json array for next step
    public JSONArray getFutureArray(JSONArray arr, String arrayName) {
        JSONArray resArr = null;
        for (int i = 0; i < arr.length(); i++)
            resArr = arr.getJSONObject(i).getJSONArray(arrayName);
        return resArr;
    }
}
