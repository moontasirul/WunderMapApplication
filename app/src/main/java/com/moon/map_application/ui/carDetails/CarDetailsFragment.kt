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
        // return inflater.inflate(R.layout.fragment_car_details, container, false)
        carDetailsBinding = FragmentCarDetailsBinding.inflate(layoutInflater)
        return carDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.fetchCarDetails()
        viewModel.response.observe(viewLifecycleOwner, Observer { response ->
            when (response.status.name) {
                "SUCCESS" -> {
                    response.data?.let {
                        print(it)
                    }
                }
                "ERROR" -> {
                    print(response.message)
                }
                "LOADING" -> {
                    print(response.message)
                }
            }
        })
    }

}