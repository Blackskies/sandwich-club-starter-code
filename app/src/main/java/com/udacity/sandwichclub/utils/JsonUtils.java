package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            //Getting the required Values
            JSONObject sandwich_details = new JSONObject(json);
            JSONObject name = sandwich_details.getJSONObject("name");

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");

            List<String> alsoKnownArray = new ArrayList<String>();
            if (alsoKnownAs != null){
                for (int i=0;i<alsoKnownAs.length();i++){
                    alsoKnownArray.add(alsoKnownAs.get(i).toString());
                }
            }

            JSONArray ingredientsJson = sandwich_details.getJSONArray("ingredients");

            List <String> ingredientsListTemp = new ArrayList<String>();
            if (ingredientsJson != null){
                for (int j=0;j<ingredientsJson.length();j++){
                    ingredientsListTemp.add(ingredientsJson.get(j).toString());
                }
            }

            //Populating the Sandwich
            sandwich.setMainName(name.getString("mainName"));
            sandwich.setAlsoKnownAs(alsoKnownArray);
            sandwich.setPlaceOfOrigin(sandwich_details.getString("placeOfOrigin"));
            sandwich.setDescription(sandwich_details.getString("description"));
            sandwich.setImage(sandwich_details.getString("image"));
            sandwich.setIngredients(ingredientsListTemp);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return sandwich;
    }
}
