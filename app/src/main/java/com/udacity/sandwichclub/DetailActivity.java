package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView descriptionTextView ;
    TextView ingredientsTextView;
    TextView placeOfOriginTextView;
    TextView alsoKnownAsTextView;
    ImageView imageSetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private String stringGenerator(List<String> list){
        String final_string = "";

        for (String temp : list){
            final_string = final_string + "," + temp ;
        }

        return final_string.substring(1) ;
    }

    private void populateUI(Sandwich sandwich) {

        //Setting Image
        Uri imageUri = Uri.parse(sandwich.getImage());
        imageSetView = (ImageView) findViewById(R.id.image_iv);
        imageSetView.setImageURI(imageUri);


        //Setting Description
        descriptionTextView = (TextView) findViewById(R.id.description_tv);
        if (sandwich.getDescription().isEmpty() || sandwich.getDescription() != null ) {
            descriptionTextView.setText(sandwich.getDescription());
        } else {
            descriptionTextView.setText("Oops !! not available");
        }

        //Setting Ingredients
        ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        if (sandwich.getIngredients().size() != 0) {
            ingredientsTextView.setText(stringGenerator(sandwich.getIngredients()));
        } else {
            ingredientsTextView.setText("Ingredients are not available");
        }

        //Setting Place Of Origin
        placeOfOriginTextView = (TextView) findViewById(R.id.origin_tv);
        if (sandwich.getPlaceOfOrigin() != null || sandwich.getPlaceOfOrigin().isEmpty() ){
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        } else {
            placeOfOriginTextView.setText("Not Available");
        }


        //Setting also known as
        alsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);
        if (sandwich.getAlsoKnownAs().size() != 0) {
            alsoKnownAsTextView.setText(stringGenerator(sandwich.getAlsoKnownAs()));
        } else {
            alsoKnownAsTextView.setText("Oops we don't have the info");
        }
    }
}
