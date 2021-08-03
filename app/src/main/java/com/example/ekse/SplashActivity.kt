package com.example.ekse

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.res.ResourcesCompat
import com.example.splashscreen.R

class SplashActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SplashTimeOut:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.splashlogo, null)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,MainActivity::class.java))

            // close this activity
            finish()
        }, SplashTimeOut)
    }
}