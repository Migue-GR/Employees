package com.employees.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.employees.R
import com.employees.utils.ext.launchWithDelay
import com.employees.view.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToMainActivity()
    }

    private fun goToMainActivity() = lifecycleScope.launchWithDelay(400) {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}