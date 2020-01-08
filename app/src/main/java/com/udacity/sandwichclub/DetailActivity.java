package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView ingredientsIv;
    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = (ImageView)findViewById(R.id.image_iv);
        mOriginTextView = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView)findViewById(R.id.description_tv);
        mIngredientsTextView = (TextView)findViewById(R.id.ingredients_tv);
        mAlsoKnownTextView = (TextView)findViewById(R.id.also_known_tv);

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

    private void populateUI(Sandwich sandwich) {

        TextView alsoKnownAsTitle = (TextView) findViewById(R.id.also_known_as_title);
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if(alsoKnownAsList.size() == 0){
           alsoKnownAsTitle.setVisibility(View.GONE);
           mAlsoKnownTextView.setVisibility(View.GONE);
        }
        if(alsoKnownAsList.size() != 0){
            for(int i = 0; i < alsoKnownAsList.size(); i++) {
                mAlsoKnownTextView.append(alsoKnownAsList.get(i) + "\n");
            }
        }

       mOriginTextView.setText(sandwich.getPlaceOfOrigin());
       mDescriptionTextView.setText(sandwich.getDescription());
       List<String> ingredientsList = sandwich.getIngredients();
       for(int j = 0; j < ingredientsList.size(); j++){
           mIngredientsTextView.append(ingredientsList.get(j) + "\n");
       }
    }
}
