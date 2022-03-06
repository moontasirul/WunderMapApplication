package com.moon.map_application.utils.dialogUtils

import androidx.databinding.ObservableField
import com.moon.map_application.ui.base.BaseViewModel

class CustomDialogViewModel : BaseViewModel<IDialogNavigation>() {

    var dialogTitle = ObservableField<String>()
    var moreInfoBtnText = ObservableField<String>()
    var closeBtnText = ObservableField<String>()

    fun onNext() {
        navigator.onNext()
    }

    fun onPositive() {
        navigator.onPositive()
    }

    fun onCancel() {
        navigator.onCancel()
    }


}