package com.indialone.rxjavademomitch.dishes.api

import com.indialone.rxjavademomitch.dishes.models.details.RecipeDetailsResponse
import com.indialone.rxjavademomitch.dishes.models.search.SearchResponse
import com.indialone.rxjavademomitch.dishes.Constants
import com.indialone.rxjavademomitch.dishes.models.search.RecipesItem
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET(Constants.SEARCH_END_POINT)
    fun getRecipes(
        @Query("q") q: String
    ): Observable<SearchResponse>

    @GET(Constants.GET_END_POINT)
    fun getRecipeDetails(
        @Query("rId") rId: String
    ): Observable<RecipeDetailsResponse>

}