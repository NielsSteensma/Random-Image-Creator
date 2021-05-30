package com.randomimagecreator.ui.shared

import androidx.appcompat.app.AppCompatActivity

/**
 * Contains common logic used across different activities
 */
open class BaseActivity : AppCompatActivity() {

    /**
     * Configures the support action bar to show the back indicator and display the given title.
     */
    protected fun setupSupportActionBar(titleStringResId: Int) {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(titleStringResId)
        supportActionBar?.setHomeAsUpIndicator(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
    }
}