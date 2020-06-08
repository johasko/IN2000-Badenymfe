package com.example.badenymfe.util

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * Binding adapter used to hide spinner once data is available.
 */
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}
