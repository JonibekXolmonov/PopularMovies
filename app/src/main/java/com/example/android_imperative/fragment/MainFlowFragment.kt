package com.example.android_imperative.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.android_imperative.R
import com.example.android_imperative.databinding.FragmentMainFlowBinding
import com.example.android_imperative.utils.Visibility.hide
import com.example.android_imperative.utils.Visibility.show


class MainFlowFragment : Fragment(R.layout.fragment_main_flow) {

    private lateinit var binding: FragmentMainFlowBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainFlowBinding.bind(view)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailsFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigation.show()
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.hide()
    }
}