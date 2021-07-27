package com.indialone.rxjavademomitch.models

data class Task(
    var description: String = "",
    var isComplete: Boolean = false,
    var priority: Int = 0
)
