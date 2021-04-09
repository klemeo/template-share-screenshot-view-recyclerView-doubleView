package ru.android.easyscreenshots.base

import androidx.fragment.app.Fragment

object FragmentListenerUtils {

    fun <T> getFragmentListener(fragment: Fragment, listenerClass: Class<T>): T {
        return getFragmentListenerOrNull(fragment, listenerClass)
            ?: throw RuntimeException("$fragment must implement $listenerClass")
    }

    private fun <T> getFragmentListenerOrNull(fragment: Fragment, listenerClass: Class<T>): T? {
        val parentFragment = fragment.parentFragment
        val activity = fragment.activity

        return when {
            listenerClass.isInstance(parentFragment) -> listenerClass.cast(parentFragment)
            listenerClass.isInstance(activity) -> listenerClass.cast(activity)
            else -> null
        }
    }
}