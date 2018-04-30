package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        mPosition = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (mPosition == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[mPosition];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json, this);
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
                .load(sandwich.getImage())
                .error(R.drawable.placeholder2)
                .placeholder(R.drawable.placeholder2)
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        TextView akaTv = findViewById(R.id.akaTv);
        TextView ingredientsTv = findViewById(R.id.ingredientsTv);
        TextView placeOfOriginTv = findViewById(R.id.placeOfOriginTv);
        TextView descriptionTv = findViewById(R.id.descriptionTv);

        akaTv.setText(makeListIntoReadableString(sandwich.getAlsoKnownAs()));
        ingredientsTv.setText(makeListIntoReadableString(sandwich.getIngredients()));
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        descriptionTv.setText(sandwich.getDescription());
    }

    private String makeListIntoReadableString (List<String> list) {

        StringBuilder finalString = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            // checks if its the last position in list, so we dont add a comma
            if (i == list.size() - 1) {
                finalString.append(list.get(i));
            } else {
                finalString.append(list.get(i));
                finalString.append(", ");
            }
        }

        return finalString.toString();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(getString(R.string.position_key), mPosition);
    }
}
