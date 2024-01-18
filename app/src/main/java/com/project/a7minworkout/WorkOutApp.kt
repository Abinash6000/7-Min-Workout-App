package com.project.a7minworkout

import android.app.Application
import com.project.a7minworkout.database.HistoryDatabase

class WorkOutApp : Application() {

    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}