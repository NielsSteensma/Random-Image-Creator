package com.randomimagecreator

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.choosesavedirectory.ChooseSaveDirectoryFragment
import com.randomimagecreator.result.ResultFragment
import com.randomimagecreator.configuration.ConfigurationFragment
import com.randomimagecreator.loading.LoadingFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytics.setup()
        replaceFragment(ConfigurationFragment(), true)
        lifecycleScope.launch {
            viewModel.state.collect(::updateNavigation)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                } else {
                    finish()
                }
            }
        })
    }

    private fun updateNavigation(state: State) {
        when (state) {
            is State.SubmittedConfigurationValid -> {
                replaceFragment(ChooseSaveDirectoryFragment())
            }
            is State.SubmitSaveDirectory -> {
                replaceFragment(LoadingFragment())
            }
            is State.FinishedCreatingImages -> {
                replaceFragment(ResultFragment())
            }
            else -> {
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
