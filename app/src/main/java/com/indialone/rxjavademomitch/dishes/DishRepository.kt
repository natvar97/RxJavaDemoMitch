package com.indialone.rxjavademomitch.dishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.indialone.rxjavademomitch.dishes.api.RecipeRetrofitBuilder
import com.indialone.rxjavademomitch.dishes.models.search.RecipesItem
import com.indialone.rxjavademomitch.dishes.models.search.SearchResponse
import okhttp3.ResponseBody

object DishRepository {

//    fun getRecipes(query: String): LiveData<ResponseBody>{
//        return LiveDataReactiveStreams.fromPublisher(
//            RecipeRetrofitBuilder
//                .recipeApiService
//                .getRecipes(query)
//        )
//    }

    fun getRecipes(query: String) = RecipeRetrofitBuilder.recipeApiService
        .getRecipes(query)

    fun getDishDetails(recipeId: String) = RecipeRetrofitBuilder.recipeApiService
        .getRecipeDetails(recipeId)

    private var instance: DishRepository? = null

    fun getInstance(): DishRepository {
        if (instance == null) {
            instance = DishRepository
        }
        return instance!!
    }

}