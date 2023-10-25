package com.randomimagecreator

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.randomimagecreator.choosesavedirectory.ChooseSaveDirectoryFragment
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.configuration.ConfigurationFragment
import com.randomimagecreator.loading.LoadingFragment
import com.randomimagecreator.result.ResultFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytics.setup()
        replaceFragment(ConfigurationFragment(), true)
        lifecycleScope.launch {
            viewModel.screen.collect(::onScreenChange)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    viewModel.onUserNavigatedBackToConfiguration()
                } else {
                    finish()
                }
            }
        })
    }

    private fun onScreenChange(screen: Screen) {
        when (screen) {
            is Screen.Configuration -> {
                // Don't do anything. This only can happen in case of back navigation
            }

            is Screen.ChooseSaveDirectory -> {
                replaceFragment(ChooseSaveDirectoryFragment())
            }

            is Screen.Loading -> {
                replaceFragment(LoadingFragment())
            }

            is Screen.Result -> {
                replaceFragment(ResultFragment())
            }
        }
    }

    /**
     * Replaces the current fragment with the provided fragment.
     *
     * @param fragment New fragment to show.
     * @param isRootFragment If set to true the transaction will not be added to the backstack
     * making the fragment become the "root". If set to false (default) the transaction
     * will be added to the backstack with a name of "null".
     */
    private fun replaceFragment(fragment: Fragment, isRootFragment: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, fragment)
            if (!isRootFragment) {
                addToBackStack(null)
            }
        }.commit()
    }
}
