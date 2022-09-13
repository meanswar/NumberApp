package com.nikitosii.util.ext

import android.view.MenuItem
import android.view.View
import android.widget.Toolbar

inline fun View?.onClick(crossinline action: () -> Unit) {
    this?.setOnClickListener { action.invoke() }
}

inline fun Toolbar?.onClick(crossinline action: (MenuItem) -> Boolean) {
    this?.setOnMenuItemClickListener { action.invoke(it) }
}

fun View.show(show: Boolean = true, useGone: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}