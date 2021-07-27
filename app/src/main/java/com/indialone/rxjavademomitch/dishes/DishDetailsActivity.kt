package com.indialone.rxjavademomitch.dishes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.indialone.rxjavademomitch.R
import com.indialone.rxjavademomitch.databinding.ActivityDishDetailsBinding
import com.indialone.rxjavademomitch.dishes.api.RecipeRetrofitBuilder
import com.indialone.rxjavademomitch.dishes.models.details.Recipe
import com.indialone.rxjavademomitch.dishes.models.details.RecipeDetailsResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DishDetailsActivity : AppCompatActivity() {

    private val TAG = "DishDetailsActivity"

    private lateinit var mBinding: ActivityDishDetailsBinding
    private var dishId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityDishDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setUpActionBar()

        if (intent.hasExtra(Constants.EXTRA_DISH_ID)) {
            dishId = intent.getStringExtra(Constants.EXTRA_DISH_ID)!!
        }

        getDishDetailsObservable()
            .subscribe(object : Observer<RecipeDetailsResponse> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: RecipeDetailsResponse) {
                    setUpUI(t.recipe)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "${e.message}")
                }

                override fun onComplete() {

                }

            })


    }

    private fun setUpActionBar() {
        setSupportActionBar(mBinding.toolbarDishDetails)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        mBinding.toolbarDishDetails.setNavigationOnClickListener { onBackPressed() }
    }

    private fun getDishDetailsObservable(): Observable<RecipeDetailsResponse> {
        return RecipeRetrofitBuilder
            .recipeApiService
            .getRecipeDetails(dishId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun setUpUI(recipe: Recipe?) {
        mBinding.tvTitle.text = recipe!!.title
        mBinding.tvPublisher.text = recipe.publisher
        mBinding.tvSocialRank.text = recipe.social_rank

        var ingredientString = ""
        for (ingredient in recipe.ingredients!!) {
            ingredientString = "$ingredientString-> $ingredient\n"
        }

        mBinding.tvIngredients.text = ingredientString
        Glide.with(this)
            .load(recipe.image_url)
            .centerCrop()
            .into(mBinding.ivDish)

    }

}