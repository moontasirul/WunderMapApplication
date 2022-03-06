package com.moon.map_application.utils.dialogUtils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.moon.map_application.databinding.CustomDialogFragmentBinding


class CustomDialogFragment(
    var message: String,
    var positiveButtonText: String,
    var negativeButtonText: String,
    var callback: CustomDialogCallback,
    var isOnlyPositiveButton: Boolean
) : IDialogNavigation, DialogFragment() {

    private lateinit var customDialogFragmentBinding: CustomDialogFragmentBinding
    private val viewModel by viewModels<CustomDialogViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        customDialogFragmentBinding = CustomDialogFragmentBinding.inflate(layoutInflater)
        //return inflater.inflate(R.layout.custom_dialog_fragment, container, false)
        customDialogFragmentBinding.lifecycleOwner = this
        customDialogFragmentBinding.dialogViewModel = viewModel
        return customDialogFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
        viewModel.dialogTitle.set(message)

        customDialogFragmentBinding.navigateButton.visibility = View.GONE
        viewModel.moreInfoBtnText.set(positiveButtonText)
        viewModel.closeBtnText.set(negativeButtonText)


        if (isOnlyPositiveButton) {
            customDialogFragmentBinding.closeButton.visibility = View.GONE
        }
    }

    override fun onNext() {
        callback.onNextClick()
    }

    override fun onPositive() {
        customDialogFragmentBinding.closeButton.visibility = View.GONE
    }

    override fun onCancel() {
        callback.onCloseClick()
    }

}