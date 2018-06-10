package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static com.udacity.sandwichclub.AppConstants.EMPTY_STRING;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView sandwichImageView;
    @BindView(R.id.sandwich_name_tv)
    TextView sandwichNameTextView;
    @BindView(R.id.alias_label_tv)
    TextView sandwichAliasLabelTextView;
    @BindView(R.id.sandwich_alias_value_tv)
    TextView sandwichAliasValue;
    @BindView(R.id.sandwich_birthplace_label_tv)
    TextView sandwichBirthPlaceLabelTextView;
    @BindView(R.id.sandwich_birthplace_origin_value_tv)
    TextView sandwichBirthPlaceValue;
    @BindView(R.id.sandwich_description_label_tv)
    TextView sandwichDescriptionLabelTextView;
    @BindView(R.id.sandwich_description_value_tv)
    TextView sandwichDescriptionValue;
    @BindView(R.id.sandwich_contents_label_tv)
    TextView sandwichContentsLabelTextView;
    @BindView(R.id.sandwich_contents_value_tv)
    TextView sandwichContentsValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position;
        position = intent != null ? intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION) : 0;
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
                .placeholder(R.drawable.placeholder)
                .resize(0, 250)
                .into(sandwichImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getMainName() != null){
            if (!sandwich.getMainName().equalsIgnoreCase(EMPTY_STRING)){
                sandwichNameTextView.setText(sandwich.getMainName());
            }
        }
        if (sandwich.getAlsoKnownAs() != null)
        {
            if (sandwich.getAlsoKnownAs().size() != 0){
                StringBuilder sandwichAliasBuilder = new StringBuilder();
                for (int counter = 0 ; counter<sandwich.getAlsoKnownAs().size();counter++){
                    if (counter != sandwich.getAlsoKnownAs().size()-1)
                        sandwichAliasBuilder.append(sandwich.getAlsoKnownAs().get(counter)).append(", ");
                    else
                        sandwichAliasBuilder.append(sandwich.getAlsoKnownAs().get(counter));
                }
                sandwichAliasValue.setText(sandwichAliasBuilder.toString());
            }
            else{
                sandwichAliasLabelTextView.setVisibility(GONE);
                sandwichAliasValue.setVisibility(GONE);
            }
        }

        if (sandwich.getPlaceOfOrigin() != null && !sandwich.getPlaceOfOrigin().equalsIgnoreCase(EMPTY_STRING)){
            sandwichBirthPlaceValue.setText(sandwich.getPlaceOfOrigin());
        }
        else{
            sandwichBirthPlaceLabelTextView.setVisibility(GONE);
            sandwichBirthPlaceValue.setVisibility(GONE);
        }
        if (sandwich.getDescription() != null && !sandwich.getDescription().equalsIgnoreCase(EMPTY_STRING)) {
            sandwichDescriptionValue.setText(sandwich.getDescription());
        }
        else{
            sandwichDescriptionLabelTextView.setVisibility(GONE);
            sandwichDescriptionValue.setVisibility(GONE);
        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() != 0){
            StringBuilder sandwichContentsBuilder = new StringBuilder();
            for (int counter = 0 ; counter<sandwich.getIngredients().size();counter++){
                if (counter != sandwich.getIngredients().size()-1)
                    sandwichContentsBuilder.append(sandwich.getIngredients().get(counter)).append(",\n");
                else
                    sandwichContentsBuilder.append(sandwich.getIngredients().get(counter)).append(".");
            }
            sandwichContentsValue.setText(sandwichContentsBuilder.toString());
        }
        else{
            sandwichContentsLabelTextView.setVisibility(GONE);
            sandwichContentsValue.setVisibility(GONE);
        }
    }
}
