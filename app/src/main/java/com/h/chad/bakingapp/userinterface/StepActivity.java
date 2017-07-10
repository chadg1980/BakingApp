package com.h.chad.bakingapp.userinterface;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.h.chad.bakingapp.R;
import com.h.chad.bakingapp.model.Ingredients;
import com.h.chad.bakingapp.model.Recipe;
import com.h.chad.bakingapp.model.Steps;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part._DATA;

public class StepActivity extends AppCompatActivity {

    public final static String TAG = StepActivity.class.getName();

    public final static String INGREDIENT_DATA = "INGREDIENT_DATA";
    public final static String STEP_DATA = "STEP_DATA";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        ArrayList<Ingredients> ingredients =
                this.getIntent().getExtras().getParcelableArrayList(INGREDIENT_DATA);
        ArrayList<Steps> steps =
                this.getIntent().getExtras().getParcelableArrayList(STEP_DATA);
        for(Steps s : steps){
            Log.e(TAG, "step " + s.getShortDescription().toString());
        }



    }
}
