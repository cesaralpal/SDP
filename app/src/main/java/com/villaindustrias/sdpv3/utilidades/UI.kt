package com.villaindustrias.sdpv3.utilidades

import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import androidx.annotation.StringRes

internal fun Activity.showSnackbarShort(text: CharSequence) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

internal fun Activity.showSnackbarShort(@StringRes text: Int) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

internal fun Fragment.showSnackbarShort(text: CharSequence) {
    Snackbar.make(activity!!.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}

internal fun Fragment.showSnackbarShort(@StringRes text: Int) {
    Snackbar.make(activity!!.findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show()
}