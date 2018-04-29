package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        String mainName;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        JSONObject sandwichInfo = new JSONObject(json);
        JSONObject sandiwchNameInfo = sandwichInfo.getJSONObject("name");
        mainName = sandiwchNameInfo.getString("mainName");
        // gets the JSON array for aka names and parses that array to create List of strings
        JSONArray akaJsonArray = sandiwchNameInfo.getJSONArray("alsoKnownAs");
        alsoKnownAs = parseJsonArray(akaJsonArray);
        placeOfOrigin = sandwichInfo.getString("placeOfOrigin");
        description = sandwichInfo.getString("description");
        image = sandwichInfo.getString("image");
        JSONArray ingredientsJsonArray = sandwichInfo.getJSONArray("ingredients");
        ingredients = parseJsonArray(ingredientsJsonArray);


        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> parseJsonArray (JSONArray jsonArray) throws JSONException {
        List<String> finishedList = new ArrayList<>();

        // checks if JSON array has any content before parsing
        if(jsonArray.length() == 0){
            finishedList.add(0, "No data found");
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                finishedList.add( i, jsonArray.getString(i));
            }
        }
        return finishedList;
    }
}
