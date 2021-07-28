package com.indialone.rxjavademomitch.dishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.indialone.rxjavademomitch.dishes.models.details.RecipeDetailsResponse
import com.indialone.rxjavademomitch.dishes.models.search.RecipesItem
import com.indialone.rxjavademomitch.dishes.models.search.SearchResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class DishesViewModel : ViewModel() {

    private val dishesRepository = DishRepository.getInstance()

    private val dishes = MutableLiveData<SearchResponse>()
    private val dishDetails = MutableLiveData<RecipeDetailsResponse>()

    init {
        fetchDishes("")
        fetchDishDetails("")
    }

    fun fetchDishDetails(recipeId: String) {
        dishesRepository
            .getDishDetails(recipeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<RecipeDetailsResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: RecipeDetailsResponse) {
                    dishDetails.value = t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

            })
    }

    fun fetchDishes(selection: String) {
        dishesRepository.getRecipes(selection)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<SearchResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: SearchResponse) {
                    dishes.value = t
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

            })
    }

    fun getDishes(): LiveData<SearchResponse> = dishes

    fun getDishDetails(): LiveData<RecipeDetailsResponse> = dishDetails

}