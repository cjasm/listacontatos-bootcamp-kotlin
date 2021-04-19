package com.everis.listadecontatos.application

import android.app.Application

class ContatoApplication : Application() {

    companion object {
        lateinit var instance: ContatoApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}