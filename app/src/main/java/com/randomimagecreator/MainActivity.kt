package com.randomimagecreator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.randomimagecreator.choosesavedirectory.ChooseSaveDirectoryFragment
import com.randomimagecreator.common.Analytics
import com.randomimagecreator.configuration.ConfigurationFragment
import com.randomimagecreator.error.ErrorFragment
import com.randomimagecreator.loading.LoadingFragment
import com.randomimagecreator.result.ResultFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Analytics.setup()
        replaceFragment(ConfigurationFragment(), isRootFragment = true)
        lifecycleScope.launch {
            viewModel.navigationRequestBroadcaster.collect(::onNavigationRequest)
        }
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val isImageCreationInProgress =
                    supportFragmentManager.findFragmentByTag(LoadingFragment.TAG)?.isVisible == true
                if (isImageCreationInProgress) {
                    showBackNavigationDisabledToast()
                } else if (supportFragmentManager.backStackEntryCount > 0) {
                    viewModel.onUserWantsToGoBackToConfiguration()
                } else {
                    this@MainActivity.finish()
                }
            }
        })
    }

    private fun onNavigationRequest(screen: Screen) {
        when (screen) {
            is Screen.Configuration -> {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

            is Screen.ChooseSaveDirectory -> {
                replaceFragment(ChooseSaveDirectoryFragment())
            }

            is Screen.Loading -> {
                replaceFragment(LoadingFragment(), LoadingFragment.TAG)
            }

            is Screen.Error -> {
                replaceFragment(ErrorFragment())
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
     * @param tag Unique identifier for the fragment, or null if not needed.
     * @param isRootFragment If set to true the transaction will not be added to the backstack
     * making the fragment become the "root". If set to false (default) the transaction
     * will be added to the backstack with a name of "null".
     */
    private fun replaceFragment(
        fragment: Fragment,
        tag: String? = null,
        isRootFragment: Boolean = false
    ) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container_view, fragment, tag)
            if (!isRootFragment) {
                addToBackStack(null)
            }
        }.commit()
    }

    private fun showBackNavigationDisabledToast() {
        val text = baseContext.getString(R.string.back_press_warning)
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(baseContext, text, duration)
        toast.show()
    }
}
