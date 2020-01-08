package com.udacity.sandwichclub.utils;

// todo: remove unwanted libs
import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonUtils {

    final static String NAME = "name";
    final static String MAIN_NAME = "mainName";
    final static String Also_Known_As = "alsoKnownAs";
    final static String Description = "description";
    final static String Place_Of_Origin = "placeOfOrigin";
    final static String Image  = "image";
    final static String Ingredients  = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
       try {
           JSONObject jObj = new JSONObject(json);


           JSONObject name = jObj.getJSONObject(NAME);
           String mainName = name.getString(MAIN_NAME);
           sandwich.setMainName(mainName);


           JSONArray alsoKnownAsArr = name.getJSONArray(Also_Known_As);
           List<String> listAlsoKnownAs = new ArrayList<>();
           if(alsoKnownAsArr.length() > 0){
               for (int j = 0; j < alsoKnownAsArr.length(); j++) {
                   String alsoKnownAs = alsoKnownAsArr.getString(j);
                   listAlsoKnownAs.add(alsoKnownAs);
               }
           }
           sandwich.setAlsoKnownAs(listAlsoKnownAs);

           String place_of_origin = jObj.getString(Place_Of_Origin);
           sandwich.setPlaceOfOrigin(place_of_origin);

           String description = jObj.getString(Description);
           sandwich.setDescription(description);

           String imageSandwich = jObj.getString(Image);
           sandwich.setImage(imageSandwich);


           JSONArray jArr = jObj.getJSONArray(Ingredients);
           List<String> listIngredients = new ArrayList<>();
           if(jArr.length() > 0){
               for (int i = 0; i < jArr.length(); i++) {
                   String ingredients = jArr.getString(i);
                   listIngredients.add(ingredients);

               }
           }
           sandwich.setIngredients(listIngredients);

       }
       catch(JSONException e){
           e.printStackTrace();
       }
       return sandwich;

    }
}
