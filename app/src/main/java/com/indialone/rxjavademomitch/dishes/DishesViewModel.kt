package com.indialone.rxjavademomitch.dishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    init {
        fetchDishes("")
    }

    fun fetchDishes(selection: String) {
            val dishesObservable = dishesRepository.getRecipes(selection)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<SearchResponse>{
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

}