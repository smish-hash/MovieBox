package com.example.moviebox

import android.os.Bundle
import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.moviebox.base.BaseActivityBinding
import com.example.moviebox.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>(), OnApplyWindowInsetsListener {

    private lateinit var navController: NavController
    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView, this)
    }

    fun updateTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        /*var gestureLeft = 0
        if (Build.VERSION.SDK_INT >= 29) {
            gestureLeft = this.window.decorView.rootWindowInsets.systemGestureInsets.left
        }

        if (gestureLeft == 0) {
            // Doesn't use gesture type navigation
            showSmallToast("Has nav buttons")
        } else {
            // Uses gesture type navigation
            showSmallToast("use gesture")
        }
        return insets*/
    }

}