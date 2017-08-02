package com.h.chad.bakingapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.util.SystemClock;
import com.h.chad.bakingapp.model.Recipe;
import com.h.chad.bakingapp.userinterface.recipe.RecipeActivity;
import com.h.chad.bakingapp.userinterface.recipe.RecipeAdapter;


import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by chad on 8/1/2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeToStepsClickTest {

    private RecipeAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;

    @Rule
    ActivityTestRule<RecipeActivity> mRecipeActivityTestRule =
        new ActivityTestRule<>(RecipeActivity.class);

   @Test
    public void RecyclerViewClickTest(){
       sleep(1000);

       onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(0, click()));

       onView(withId(R.id.tv_recipe_name)).check(matches(withText("Nutella Pie")));

   }


}
