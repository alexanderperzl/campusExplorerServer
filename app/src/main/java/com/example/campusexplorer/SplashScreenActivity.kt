package com.example.campusexplorer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson


class SplashScreenActivity : AppCompatActivity() {
    val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

}
