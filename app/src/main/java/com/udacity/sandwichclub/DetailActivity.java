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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_as_tv)
    TextView mAlsoKnownAsTV;
    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTV;
    @BindView(R.id.place_of_origin_tv)
    TextView mPlaceOfOriginTV;
    @BindView(R.id.description_tv)
    TextView mDescriptionTV;

    @BindView(R.id.main_name_tv)
    TextView mMainName;
    @BindView(R.id.also_known_as)
    TextView mAlsoKnownAs;
    @BindView(R.id.place_of_origin)
    TextView mPlaceOfOrigin;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.ingredients)
    TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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
        ArrayList<Sandwich> sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.get(0).getImage())
                .into(ingredientsIv);

        setTitle(sandwich.get(0).getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(ArrayList<Sandwich> sandwich) {

        String mainName = sandwich.get(0).getMainName();
        if (mainName.isEmpty()){
            mMainName.setVisibility(View.GONE);
        } else {
            mMainName.setText(mainName);
        }

        List<String> alsoKnowAsList= sandwich.get(0).getAlsoKnownAs();
        if (alsoKnowAsList.size() == 0) {
            mAlsoKnownAsTV.setVisibility(View.GONE);
        } else {
            StringBuilder alsoKnowAs = new StringBuilder();
            for (String alsoknownas : alsoKnowAsList) {
                alsoKnowAs.append(alsoknownas).append("\n");
            }
            mAlsoKnownAs.setText(alsoKnowAs);
        }

        String placeOfOrigin = sandwich.get(0).getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            mPlaceOfOriginTV.setVisibility(View.GONE);
        } else {
            mPlaceOfOrigin.setText(placeOfOrigin);
        }

        String description = sandwich.get(0).getDescription();
        if (description.isEmpty()) {
            mDescriptionTV.setVisibility(View.GONE);
        } else {
            mDescription.setText(description);
        }

        List<String> ingredientsList= sandwich.get(0).getIngredients();
        if (alsoKnowAsList.size() == 0) {
            mIngredientsTV.setVisibility(View.GONE);
        } else {
            StringBuilder ingredients = new StringBuilder();
            for (String ingredient : ingredientsList) {
                ingredients.append(ingredient).append("\n");
            }
            mIngredients.setText(ingredients);
        }

    }
}
