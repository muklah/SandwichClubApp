package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonUtils {

    public static ArrayList<Sandwich> parseSandwichJson(String json) throws JSONException {

        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS= "alsoKnownAs";
        final String PLACE_OF_ORIGIN= "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS= "ingredients";

        ArrayList<Sandwich> parsedSandwichData = new ArrayList<Sandwich>();

        JSONObject sandwichObject = new JSONObject(json);
        JSONObject sandwichNameObject = sandwichObject.getJSONObject(NAME);

        JSONArray alsoKnownAsArray = sandwichNameObject.getJSONArray(ALSO_KNOWN_AS);
        JSONArray ingredientsArray = sandwichObject.getJSONArray(INGREDIENTS);

        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();

        mainName = sandwichNameObject.getString(MAIN_NAME);
        for (int i = 0; i < alsoKnownAsArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsArray.getString(i));
        }
        placeOfOrigin = sandwichObject.getString(PLACE_OF_ORIGIN);
        description = sandwichObject.getString(DESCRIPTION);
        image = sandwichObject.getString(IMAGE);
        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }

        parsedSandwichData.add(new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients));

        return parsedSandwichData;
    }

}
