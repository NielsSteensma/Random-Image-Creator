package com.randomimagecreator.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.randomimagecreator.R
import com.randomimagecreator.analytics.AnalyticsManager
import com.randomimagecreator.ui.choosesavedirectory.ChooseSaveDirectoryFragment
import com.randomimagecreator.ui.createdimages.CreatedImagesFragment
import com.randomimagecreator.ui.createimages.CreateImagesFragment
import com.randomimagecreator.ui.creatingimages.CreatingImagesFragment
import com.randomimagecreator.ui.shared.MainViewModel
import com.randomimagecreator.ui.shared.State

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AnalyticsManager.setup()
        replaceFragment(CreateImagesFragment(), true)
        viewModel.state.observe(this, ::updateNavigation)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            super.onBackPressed()
        }
    }

    private fun updateNavigation(state: State) {
        when (state) {
            State.SUBMIT_CONFIG_VALID -> {
                replaceFragment(ChooseSaveDirectoryFragment())
            }
            State.SUBMIT_SAVE_DIRECTORY -> {
                replaceFragment(CreatingImagesFragment())
            }
            State.FINISHED_CREATING_IMAGES -> {
                replaceFragment(CreatedImagesFragment())
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
