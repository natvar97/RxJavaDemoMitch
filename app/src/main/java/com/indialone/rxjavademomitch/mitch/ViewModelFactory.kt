package com.indialone.rxjavademomitch.mitch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indialone.rxjavademomitch.dishes.DishesViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        if (modelClass.isAssignableFrom(DishesViewModel::class.java)) {
            return DishesViewModel() as T
        }
        throw IllegalArgumentException("Unknown view model class found")
    }
}