package com.randomimagecreator.ui.shared

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.randomimagecreator.R

/**
 * Contains common logic used across different activities.
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * Configures the support action bar to show the back indicator and display the given title.
     */
    protected fun setupSupportActionBar(titleStringResId: Int) {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(titleStringResId)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_material)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                handleBackNavigation()
                true
            }
            else -> {
                false
            }
        }
    }

    private fun handleBackNavigation() {
        finish()
    }
}