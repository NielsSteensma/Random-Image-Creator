package com.randomimagecreator.ui.createimages

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter


/**
 * Custom [ArrayAdapter] that does not perform any filtering.
 */
class NoFilterArrayAdapter<T> : ArrayAdapter<T> {
    constructor(context: Context, resource: Int, array: Array<T>) : super(context, resource, array)
    constructor(context: Context, resource: Int, list: List<T>) : super(context, resource, list)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?) = null

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?
            ) {
            }
        }
    }
}
