package com.indialone.rxjavademomitch.dishes.api

import com.indialone.rxjavademomitch.dishes.Constants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RecipeRetrofitBuilder {



    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_FORKIFY)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val recipeApiService = getInstance().create(RecipeApiService::class.java)

}