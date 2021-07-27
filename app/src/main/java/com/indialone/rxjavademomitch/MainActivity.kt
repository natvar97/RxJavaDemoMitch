package com.indialone.rxjavademomitch

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.indialone.rxjavademomitch.databinding.ActivityMainBinding
import com.indialone.rxjavademomitch.databinding.DialogCustomListBinding
import com.indialone.rxjavademomitch.dishes.Constants
import com.indialone.rxjavademomitch.dishes.CustomListItemAdapter
import com.indialone.rxjavademomitch.dishes.DishesRvAdapter
import com.indialone.rxjavademomitch.dishes.DishesViewModel
import com.indialone.rxjavademomitch.dishes.api.RecipeRetrofitBuilder
import com.indialone.rxjavademomitch.dishes.models.search.RecipesItem
import com.indialone.rxjavademomitch.dishes.models.search.SearchResponse
import com.indialone.rxjavademomitch.mitch.MainViewModel
import com.indialone.rxjavademomitch.mitch.ViewModelFactory
import com.indialone.rxjavademomitch.models.DataSource
import com.indialone.rxjavademomitch.models.Task
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private var disposable = CompositeDisposable()
    private lateinit var searchView: SearchView
    private var timeSinceLastRequest: Long = System.currentTimeMillis()
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mDishesViewModel: DishesViewModel
    private var mSelectionString: String = ""
    private lateinit var mCustomDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mDishesViewModel =
            ViewModelProvider(this, ViewModelFactory()).get(DishesViewModel::class.java)


        mBinding.filter.setOnClickListener {
            openFilterDialog()
        }

//        getDishes(mSelectionString)

    }

    override fun onResume() {
        super.onResume()
        mDishesViewModel.getDishes().observe(this){ searchResponse ->
            mBinding.rvDishes.layoutManager = LinearLayoutManager(this@MainActivity)
            mBinding.rvDishes.adapter =
                DishesRvAdapter(this@MainActivity, searchResponse.recipes as ArrayList<RecipesItem>)
        }
    }

    private fun getDishesUpdate(selection: String) {
        mDishesViewModel.fetchDishes(selection)
        mDishesViewModel.getDishes().observe(this){ searchResponse ->
            mBinding.rvDishes.layoutManager = LinearLayoutManager(this@MainActivity)
            mBinding.rvDishes.adapter =
                DishesRvAdapter(this@MainActivity, searchResponse.recipes as ArrayList<RecipesItem>)
        }
    }
// mvc
    private fun getDishes(selection: String) {
        getDishesObservable(selection)
            .subscribe(object : Observer<SearchResponse> {
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onNext(t: SearchResponse) {
                    for (id in t.recipes!!) {
                        Log.e(TAG, "RECIPE_ID : ${id!!.recipe_id} ")
                    }
                    mBinding.rvDishes.layoutManager = LinearLayoutManager(this@MainActivity)
                    mBinding.rvDishes.adapter =
                        DishesRvAdapter(this@MainActivity, t.recipes as ArrayList<RecipesItem>)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "${e.message}")
                }

                override fun onComplete() {

                }

            })
    }

    private fun openFilterDialog() {
        mCustomDialog = Dialog(this)

        val binding = DialogCustomListBinding.inflate(layoutInflater)
        mCustomDialog.setContentView(binding.root)

        binding.tvTitle.text = Constants.TITLE_DIALOG
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter =
            CustomListItemAdapter(this, getSearchOptions(), Constants.FILTER_SELECTION)

        mCustomDialog.setCanceledOnTouchOutside(true)
        mCustomDialog.show()

    }

    fun filterSelection(selection: String) {
        mCustomDialog.dismiss()
//        getDishes(selection)
        getDishesUpdate(selection)
        mSelectionString = selection
    }

    // mvc
    private fun getDishesObservable(query: String): Observable<SearchResponse> {
        return RecipeRetrofitBuilder
            .recipeApiService
            .getRecipes(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

    private fun getSearchOptions(): ArrayList<String> {
        return arrayListOf(
            "carrot",
            "broccoli",
            "asparagus",
            "cauliflower",
            "corn",
            "cucumber",
            "green pepper",
            "lettuce",
            "mushrooms",
            "onion",
            "potato",
            "pumpkin",
            "red pepper",
            "tomato",
            "beetroot",
            "brussel sprouts",
            "peas",
            "zucchini",
            "radish",
            "sweet potato",
            "artichoke",
            "leek",
            "cabbage",
            "celery",
            "chili",
            "garlic",
            "basil",
            "coriander",
            "parsley",
            "dill",
            "rosemary",
            "oregano",
            "cinnamon",
            "saffron",
            "green bean",
            "bean",
            "chickpea",
            "lentil",
            "apple",
            "apricot",
            "avocado",
            "banana",
            "blackberry",
            "blackcurrant",
            "blueberry",
            "boysenberry",
            "cherry",
            "coconut",
            "fig",
            "grape",
            "kiwifruit",
            "lemon",
            "lime",
            "lychee",
            "mandarin",
            "mango",
            "melon",
            "nectarine",
            "orange",
            "papaya",
            "passion fruit",
            "peach",
            "pear",
            "pineapple",
            "plum",
            "pomegranate",
            "quince",
            "raspberry",
            "strawberry",
            "watermelon",
            "salad",
            "pizza",
            "pasta",
            "popcorn",
            "lobster",
            "steak",
            "bbq",
            "pudding",
            "hamburger",
            "pie",
            "cake",
            "sausage",
            "tacos",
            "kebab",
            "poutine",
            "seafood",
            "chips",
            "fries",
            "masala",
            "paella",
            "som tam",
            "chicken",
            "toast",
            "marzipan",
            "tofu",
            "ketchup",
            "hummus",
            "chili",
            "maple syrup",
            "parma ham",
            "fajitas",
            "champ",
            "lasagna",
            "poke",
            "chocolate",
            "croissant",
            "arepas",
            "bunny chow",
            "pierogi",
            "donuts",
            "rendang",
            "sushi",
            "ice cream",
            "duck",
            "curry",
            "beef",
            "goat",
            "lamb",
            "turkey",
            "pork",
            "fish",
            "crab",
            "bacon",
            "ham",
            "pepperoni",
            "salami",
            "ribs"
        )
    }

}

/*
        ** debounce ***

        searchView = findViewById(R.id.search_view)

        val queryTextObservable: Observable<String> = Observable
            .create(object : ObservableOnSubscribe<String> {
                override fun subscribe(emitter: ObservableEmitter<String>) {

                    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (!emitter.isDisposed) {
                                emitter.onNext(query!!)
                            }
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }
                    })
                }
            })
            .debounce(1000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())

        queryTextObservable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onNext(t: String) {
                Log.d(
                    TAG,
                    "time elapsed from last search ${System.currentTimeMillis() - timeSinceLastRequest}"
                )
                Log.d(TAG, "search string: $t")
                timeSinceLastRequest = System.currentTimeMillis()
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }

        })


 */

/*
            Buffer operator

    val taskObservable: Observable<Task> = Observable
        .fromIterable(DataSource.createTaskList())
        .subscribeOn(Schedulers.io())

    taskObservable
        .buffer(2)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(object : Observer<List<Task>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: List<Task>) {
                Log.d(TAG, "buffer:------bundle---------")
                for (task in t) {
                    Log.d(TAG, task.description)
                }
            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }

        })
       */
/*
            map operator

            Observable
            .fromIterable(DataSource.createTaskList())
            .map { t -> t.description }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: String) {
                    Log.d(TAG, t)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

            })

 */

/*
    take(value: Int) it takes only first three items from observable

    same like takeWhile is shown in below example

 */
//
//        val taskObservable: Observable<Task> = Observable
//            .fromIterable(DataSource.createTaskList())
//            .subscribeOn(Schedulers.io())
//            .takeWhile {
//                it.isComplete
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//
//
//        taskObservable.subscribe(object : Observer<Task> {
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onNext(t: Task) {
//                Log.d(TAG, t.description)
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//        })

/*
    distinct is some like filtering but it make some differences
    like duplicates
 */

//        val taskObservable: Observable<Task> = Observable
//            .fromIterable(DataSource.createTaskList())
//            .subscribeOn(Schedulers.io())
//            .distinct(object : Function<Task, String> {
//                override fun apply(task: Task): String {
//                    return task.description
//                }
//            })
//
//        taskObservable.subscribe(object : Observer<Task> {
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onNext(t: Task) {
//                Log.d(TAG, t.description)
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//        })


/*
        filter in RxJava

 */

//        val taskObservable: Observable<Task> = Observable
//            .fromIterable(DataSource.createTaskList())
//            .subscribeOn(Schedulers.io())
//            .filter(object : Predicate<Task> {
//                override fun test(t: Task): Boolean {
//                    Log.d(TAG, "thread: ${Thread.currentThread().name}")
//                    if (t.description == "Walk the Dog") return true
//                    return false
//                }
//            })
//            .observeOn(AndroidSchedulers.mainThread())
//
//        taskObservable.subscribe(object : Observer<Task> {
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onNext(t: Task) {
//                Log.d(TAG, "$t")
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//        })


/*
     from api calling  getting response  from api

     D/MainActivity: onChanged: this is a live data response!
        onChanged: {
          "userId": 1,
          "id": 1,
          "title": "delectus aut autem",
          "completed": false
        }

 */

//        val mainViewModel =
//            ViewModelProvider(this, ViewModelFactory()).get(MainViewModel::class.java)
//        mainViewModel.makeQuery().observe(this) { responseBody ->
//            Log.d(TAG, "onChanged: this is a live data response!")
//            try {
//                Log.d(TAG, "onChanged: " + responseBody!!.string())
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }


/*
    ** using create operator


 // we can create observable from single value and also iterable list
 // below is for single value observable
val task = Task("Walk the dog", false, 3)

 // below is the multiple value from list or arraylist observable
val taskList = DataSource.createTaskList()

 // you can use just() operator for above task variable and it will give the same output we got before
 // Observable.just(task)

val taskObservable: Observable<Task> = Observable
    .create(object : ObservableOnSubscribe<Task> {
        override fun subscribe(emitter: ObservableEmitter<Task>) {

            // it is for the list item observable
            for (task in taskList) {
                if (!emitter.isDisposed) {
                    emitter.onNext(task)
                }
            }

            if (!emitter.isDisposed) {
                emitter.onComplete()
            }

            // this is for single value observable creation
//                    if (!emitter.isDisposed) {
//                        emitter.onNext(task)
//                        emitter.onComplete()
//                    }
        }
    })
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())

taskObservable.subscribe(object : Observer<Task> {
    override fun onSubscribe(d: Disposable) {
        disposable.add(d)
    }

    override fun onNext(t: Task) {
        Log.d(TAG, t.description)
    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

})

 */

/*
    ** range operator
    *

    val observable: Observable<Task> = Observable
    .range(0, 9)
    .subscribeOn(Schedulers.io())
//            .map(object: Function<Int, Task>{
//                override fun apply(t: Int): Task {
//                    return  Task("This is a task #$t", false, t)
//                }
//
//            })
    .map { integer ->
        Task("This is a task #$integer", false, integer)
    }
    .takeWhile {
        it.priority < 9
    }
    .observeOn(AndroidSchedulers.mainThread())


observable.subscribe(object : Observer<Task> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: Task) {
        Log.d(TAG, "${t.priority}")
    }

    override fun onError(e: Throwable) {

    }

    override fun onComplete() {

    }

})
 */

/*
    interval operator

    - it will emits the data 1 to 5 seconds then it will stop
 */

//        val intervalObservable: Observable<Long> = Observable
//            .interval(1, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .takeWhile {
//                Log.d(TAG, Thread.currentThread().name)
//                it <= 5
//            }
//            .observeOn(AndroidSchedulers.mainThread())
//
//        intervalObservable.subscribe(object : Observer<Long> {
//            override fun onSubscribe(d: Disposable) {
//
//            }
//
//            override fun onNext(t: Long) {
//                Log.d(TAG, "on Next: $t")
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//        })

/*

    timer operator

    it will emits the data after specified time

 */

//        val timerObservable: Observable<Long> = Observable
//            .timer(3, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//
//        timerObservable.subscribe(object : Observer<Long> {
//            var time = 0L
//            override fun onSubscribe(d: Disposable) {
//                time = System.currentTimeMillis() / 1000
//            }
//
//            override fun onNext(t: Long) {
//                Log.d(
//                    TAG,
//                    "emit the data after ${System.currentTimeMillis() / 1000 - time} seconds"
//                )
//            }
//
//            override fun onError(e: Throwable) {
//
//            }
//
//            override fun onComplete() {
//
//            }
//
//        })

/*

    fromArray() operator

 */


/*
    ***** from iterable *****


        val taskObservable: Observable<Task> = Observable
            .fromIterable(DataSource.createTaskList())
            .subscribeOn(Schedulers.io())
            .filter { task ->
                // below only for testing purpose
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
                task.isComplete
            }
            .observeOn(AndroidSchedulers.mainThread())

        taskObservable.subscribe(object : Observer<Task> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "on Subscribe : called")
                disposable.add(d)
            }

            // here on main Thread i am sleeping the thread for 1000 sec
            // it freezes the ui for 1000 milli seconds
            // but i put thread sleep in filter in observable then it freezes the process in background

            override fun onNext(task: Task) {
                Log.d(TAG, "onNext : called : thread : ${Thread.currentThread().name}")
//                try {
//                    Thread.sleep(1000)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
                Log.d(TAG, "task: ${task.description}")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError : ${e.message}")
            }

            override fun onComplete() {
                Log.d(TAG, "onComplete : called")
            }

        })

 */