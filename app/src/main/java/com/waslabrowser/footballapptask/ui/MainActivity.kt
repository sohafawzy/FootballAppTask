package com.waslabrowser.footballapptask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.waslabrowser.footballapptask.R
import com.waslabrowser.footballapptask.databinding.ActivityMainBinding
import com.waslabrowser.footballapptask.ui.base.BaseActivity
import com.waslabrowser.footballapptask.ui.home.views.FavoritesFragment
import com.waslabrowser.footballapptask.ui.home.views.FixturesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    private val fragNavController by lazy {
        FragNavController(
            supportFragmentManager,
            R.id.navHostFragment
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        setupFragmentNavController(savedInstanceState)
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_graph_home -> fragNavController.switchTab(0)
                R.id.nav_graph_favs -> fragNavController.switchTab(1)
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setupFragmentNavController(savedInstanceState: Bundle?) {
        val fragments: List<Fragment> = listOf(
            FixturesFragment(), FavoritesFragment()
        )
        fragNavController.apply {
            createEager = true
            fragNavController.rootFragments = fragments
            defaultTransactionOptions = FragNavTransactionOptions.newBuilder().build()
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
        }
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        fragNavController.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (fragNavController.isRootFragment.not())
            fragNavController.popFragment()
        else if (fragNavController.isRootFragment
            && fragNavController.currentFrag is FixturesFragment
        ) {
            moveTaskToBack(true)
        } else
            super.onBackPressed()
    }

}