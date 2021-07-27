package com.indialone.rxjavademomitch.models

import java.util.*

object DataSource {
    fun createTaskList(): List<Task> {
        val tasks: MutableList<Task> = ArrayList()
        tasks.add(Task("Take out the trash", true, 3))
        tasks.add(Task("Walk the Dog", false, 2))
        tasks.add(Task("Make my bed", true, 1))
        tasks.add(Task("Unload the dish washer", false, 0))
        tasks.add(Task("Make dinner", true, 5))
        tasks.add(Task("Make dinner", true, 5))
        return tasks
    }
}