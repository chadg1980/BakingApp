package com.h.chad.bakingapp.data;

import com.h.chad.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chad on 7/6/2017.
 *
 * This interface contains methods  for HTTP requests
 * GET
 * POST
 * PUT
 * PATCH
 * DELETE
 */

public interface SOService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();
}
