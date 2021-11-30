package com.employees.view

import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.TransitionManager
import com.employees.R
import com.employees.databinding.ActivityMainBinding
import com.employees.model.enums.ErrorCode.*
import com.employees.utils.AppSession
import com.employees.utils.AppSession.shouldUpdateEmployees
import com.employees.utils.EmployeesException
import com.employees.utils.ext.launchWithDelay
import com.employees.utils.ext.showToast
import com.employees.view.home.HomeFragment
import com.employees.view.login.LoginFragment
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var errorSnackBar: Snackbar
    private var doubleBackToExitPressedOnce: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
        setUpErrorSnackBar()
        observeUiEvents()
    }

    private fun setUpErrorSnackBar() {
        @ColorInt
        val bgColor = ContextCompat.getColor(this@MainActivity, R.color.red)
        val textColor = ContextCompat.getColor(this@MainActivity, R.color.white)
        errorSnackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        errorSnackBar.setBackgroundTint(bgColor)
        errorSnackBar.setTextColor(textColor)
    }

    private fun observeUiEvents() {
        AppSession.run {
            globalProgressBar.observe(this@MainActivity, {
                TransitionManager.beginDelayedTransition(binding.layoutActivityMain)
                binding.pgbMain.visibility = if (it) View.VISIBLE else View.GONE
                binding.bgProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                binding.blockerViewsScreen.visibility = if (it) View.VISIBLE else View.GONE
            })
            globalError.observe(this@MainActivity, { error ->
                if (error.errorCode != UNKNOWN_ERROR) {
                    errorSnackBar.setText(error.localizedMessage).show()
                    showGlobalError(EmployeesException(UNKNOWN_ERROR))
                }
            })
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination?.label) {
            LoginFragment::class.simpleName -> pressAgainToExit()
            HomeFragment::class.simpleName -> pressAgainToExit()
            else -> super.onBackPressed()
        }
    }

    private fun pressAgainToExit() {
        if (doubleBackToExitPressedOnce) {
            shouldUpdateEmployees = true
            finishAffinity()
            return
        } else {
            doubleBackToExitPressedOnce = true
            showToast(getString(R.string.press_again_to_exit))
            lifecycleScope.launchWithDelay(2000) { doubleBackToExitPressedOnce = false }
        }
    }
}