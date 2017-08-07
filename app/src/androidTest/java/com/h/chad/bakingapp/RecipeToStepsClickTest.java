package com.h.chad.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.h.chad.bakingapp.userinterface.recipe.RecipeActivity;
import com.h.chad.bakingapp.userinterface.recipe.RecipeAdapter;


import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.not;

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
    private SimpleIdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<RecipeActivity> mRecipeActivityTestRule =
        new ActivityTestRule<>(RecipeActivity.class);


   @Test
    public void RecyclerViewClickTest(){
       //When the app starts, there should be a list of items recipe names from the JSON data
       //Test the following exist
       onView(withId(R.id.rv_recipes))
               .check(matches(hasDescendant(withText("Nutella Pie"))));
       onView(withId(R.id.rv_recipes))
               .check(matches(hasDescendant(withText("Brownies"))));
       onView(withId(R.id.rv_recipes))
               .check(matches(hasDescendant(withText("Yellow Cake"))));
       onView(withId(R.id.rv_recipes))
               .check(matches(hasDescendant(withText("Cheesecake"))));

       //Test the following do not exist
       onView(withId(R.id.rv_recipes))
               .check(matches(not(hasDescendant(withText("xxxaaa111")))));
       onView(withId(R.id.rv_recipes))
               .check(matches(not(hasDescendant(withText("")))));
       onView(withId(R.id.rv_recipes))
               .check(matches(not(hasDescendant(withText(" ")))));

       //Performing click action on item 0 ("Nutella Pie")
       onView(withId(R.id.rv_recipes))
               .perform(actionOnItemAtPosition(0, click()));
       onView(withId(R.id.step_list))
               .check(matches(hasDescendant(withText("Recipe Introduction"))));







   }


}
