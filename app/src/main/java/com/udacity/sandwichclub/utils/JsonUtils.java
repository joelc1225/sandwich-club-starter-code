package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.content.res.Resources;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static Resources resources;

    public static Sandwich parseSandwichJson(String json, Context context) throws JSONException {

        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;
        resources = context.getResources();

        // creates base JSON object to retrieve all info
        JSONObject sandwichInfo = new JSONObject(json);

        // gets sandwich name info
        JSONObject sandwichNameInfo = sandwichInfo.getJSONObject("name");
        mainName = sandwichNameInfo.getString("mainName");
        checkString(mainName);

        // gets the JSON array for aka names and parses that array to create List of strings
        JSONArray akaJsonArray = sandwichNameInfo.getJSONArray("alsoKnownAs");
        alsoKnownAs = parseJsonArray(akaJsonArray);

        // gets place of origin and checks if valid
        placeOfOrigin = sandwichInfo.getString("placeOfOrigin");
        checkString(placeOfOrigin);

        // gets description and checks if valid
        description = sandwichInfo.getString("description");
        checkString(description);

        // gets image url. Picasso will handle null case, so i wont need to here
        image = sandwichInfo.getString("image");

        // gets ingredients string array and parses into a List
        JSONArray ingredientsJsonArray = sandwichInfo.getJSONArray("ingredients");
        ingredients = parseJsonArray(ingredientsJsonArray);

        // returns complete sandwich object
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> parseJsonArray (JSONArray jsonArray) throws JSONException {
        List<String> finishedList = new ArrayList<>();

        // checks if JSON array has any content before parsing
        if(jsonArray.length() == 0){
            finishedList.add(0, resources.getString(R.string.no_data_found));
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                finishedList.add( i, jsonArray.getString(i));
            }
        }
        return finishedList;
    }

    // check if valid string is present, if not changes string to error message
    private static void checkString(String stringToCheck) {
        if (stringToCheck.isEmpty()) {
            stringToCheck = resources.getString(R.string.no_data_found);
        }
    }
}
