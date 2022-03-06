package com.developeralamin.wallpaperappuser.ui.splashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.developeralamin.wallpaperappuser.R
import com.developeralamin.wallpaperappuser.ui.mainActivity.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },2000)
    }
}