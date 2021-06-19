package mak.onelinecoding.qrcodepdfapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BaseFragment.Callback {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.navHostFragment)

        NavigationUI.setupWithNavController(collapsingToolbarLayout, toolbar, navController)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val lp = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        appBarHeight = lp.height
    }

    override fun handleAppBar(expanded: Boolean, hideActionBar: Boolean) {
        toolbar.navigationIcon = null
        appBarLayout.setExpanded(expanded)

        val lp = appBarLayout.layoutParams as CoordinatorLayout.LayoutParams

        lp.height = if (hideActionBar) 0 else appBarHeight

        appBarLayout.layoutParams = lp
    }

    companion object {
        var appBarHeight = 0
    }

}