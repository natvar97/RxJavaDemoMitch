package com.indialone.rxjavademomitch.mitch

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody


class MainViewModel : ViewModel() {

    private val repository: Repository = Repository.getInstance()!!

    fun makeQuery(): LiveData<ResponseBody?> {
        return repository.makeReactiveQuery()
    }

}