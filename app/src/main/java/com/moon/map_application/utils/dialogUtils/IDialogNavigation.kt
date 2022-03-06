package com.moon.map_application.utils.dialogUtils

import com.moon.map_application.ui.base.IBaseNavigator

interface IDialogNavigation : IBaseNavigator {
    fun onNext()
    fun onPositive()
    fun onCancel()
}