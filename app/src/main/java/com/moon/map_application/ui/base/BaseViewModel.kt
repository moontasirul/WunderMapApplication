package com.moon.map_application.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    var isLoading = ObservableBoolean(false)
}