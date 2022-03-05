package com.moon.map_application.ui.carDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.moon.map_application.databinding.FragmentCarDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = CarDetailsFragment()
    }

    private lateinit var carDetailsBinding: FragmentCarDetailsBinding
    private val viewModel by viewModels<CarDetailsViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.setCarId(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        carDetailsBinding = FragmentCarDetailsBinding.inflate(layoutInflater)
        carDetailsBinding.lifecycleOwner = this
        carDetailsBinding.carDetailsViewModel = viewModel
        return carDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.isLoading.set(true)
        viewModel.fetchCarDetails()
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            when (response.status.name) {
                "SUCCESS" -> {
                    response.data?.let {
                        print(it)
                        viewModel.isLoading.set(false)
                        viewModel.getCarData(it)
                    }
                }
                "ERROR" -> {
                    viewModel.isLoading.set(false)
                    print(response.message)
                }
                "LOADING" -> {
                    viewModel.isLoading.set(true)
                    print(response.message)
                }
            }
        })
    }

}