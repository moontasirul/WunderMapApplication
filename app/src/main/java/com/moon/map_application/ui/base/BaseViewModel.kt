package com.moon.map_application.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<N : IBaseNavigator> : ViewModel() {
    var isLoading = ObservableBoolean(false)
    private lateinit var mNavigator: N

    /**
     * Get Navigator
     */
    protected val navigator: N
        get() {
            return mNavigator
        }

    /**
     * Set Navigator
     */
    fun setNavigator(navigator: N) {
        this.mNavigator = navigator
    }
}