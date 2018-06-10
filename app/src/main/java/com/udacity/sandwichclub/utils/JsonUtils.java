package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.sandwichclub.AppConstants.PLACE_OF_BIRTH_OF_SANDWICH;
import static com.udacity.sandwichclub.AppConstants.SANDWICH_ALIAS;
import static com.udacity.sandwichclub.AppConstants.SANDWICH_CONTENTS;
import static com.udacity.sandwichclub.AppConstants.SANDWICH_DESCRIPTION;
import static com.udacity.sandwichclub.AppConstants.SANDWICH_HIGHLIGHT_NAME;
import static com.udacity.sandwichclub.AppConstants.SANDWICH_IMAGE;
import static com.udacity.sandwichclub.AppConstants.SANDWICH_NAME;

public class JsonUtils {

    private static final String TAG = "JsonUtils";
    public static Sandwich parseSandwichJson(String sandwichJson) {
        try {
            // Creating the Main JSON Object from the available string
            JSONObject sandwichMainJsonObject = new JSONObject(sandwichJson);
            JSONObject sandwichNameJson = sandwichMainJsonObject.getJSONObject(SANDWICH_NAME);
            String sandwichHighlightName = retrieveHightlightName(sandwichNameJson);
            List<String> sandwichAliases = retrieveSandwichAlias(sandwichNameJson);
            String sandwichBirthPlace = sandwichMainJsonObject.getString(PLACE_OF_BIRTH_OF_SANDWICH);
            String description = sandwichMainJsonObject.getString(SANDWICH_DESCRIPTION);
            String image = sandwichMainJsonObject.getString(SANDWICH_IMAGE);
            List<String> sandwichContents = retreiveSandwichContents(sandwichMainJsonObject);
            return new Sandwich(sandwichHighlightName, sandwichAliases,
                    sandwichBirthPlace, description, image, sandwichContents);
        } catch (JSONException e) {
            Log.d(TAG, "Error occurred while parsing json");

            return null;
        }
    }


    // Utility Methods


    // Method to retrieve sandwich components from the corresponding JSON Object
    private static List<String> retreiveSandwichContents(JSONObject sandwichContentsJson) throws JSONException {
        JSONArray sandwichContentsArray = sandwichContentsJson.getJSONArray(SANDWICH_CONTENTS);
        return retrievelistFromArray(sandwichContentsArray);
    }

    // Method to retrieve Sandwich High-Light Name from the corresponding JSON Object

    private static String retrieveHightlightName(JSONObject highlightNameJson) throws JSONException {
        return highlightNameJson.getString(SANDWICH_HIGHLIGHT_NAME);
    }

    // Method to retrieve sandwich aliases from the corresponding JSON Object

    private static List<String> retrieveSandwichAlias (JSONObject sandwichAliasJson) throws JSONException {
        JSONArray alsoKnownAsArray = sandwichAliasJson.getJSONArray(SANDWICH_ALIAS);
        return retrievelistFromArray(alsoKnownAsArray);
    }

    // Method to retrieve create a List from the corresponding JSON Array

    private static List<String> retrievelistFromArray(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int pos = 0; pos < jsonArray.length(); pos++) {
            list.add(jsonArray.getString(pos));
        }
        return list;
    }
}
