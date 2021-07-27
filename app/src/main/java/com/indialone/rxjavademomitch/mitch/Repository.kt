package com.indialone.rxjavademomitch.mitch

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.indialone.rxjavademomitch.mitch.ServiceGenerator.requestApi
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody


object Repository {
    fun makeReactiveQuery(): LiveData<ResponseBody?> {
        return LiveDataReactiveStreams.fromPublisher(
            requestApi
                .makeQuery()
                .subscribeOn(Schedulers.io())
        )
    }


    private var instance: Repository? = null

    fun getInstance(): Repository? {
        if (instance == null) {
            instance = Repository
        }
        return instance
    }


}