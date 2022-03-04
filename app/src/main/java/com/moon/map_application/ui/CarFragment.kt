package com.moon.map_application.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.moon.map_application.R
import com.moon.map_application.data.model.CarItem
import com.moon.map_application.databinding.FragmentCarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    companion object {
        fun newInstance() = CarFragment()
    }

    private lateinit var carBinding: FragmentCarBinding
    private val viewModel by viewModels<CarViewModel>()
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var currentLocation: Location

    private var REQUEST_CODE = 101
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        carBinding = FragmentCarBinding.inflate(layoutInflater)
        // inflater.inflate(R.layout.fragment_car, container, false)
        return carBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(requireContext())
        fetchLocation()
        setupObservers()
    }

    private fun fetchLocation() {
        activity?.let {
            if (ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_CODE
                )
                return
            }
        }


        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)

            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        currentLocation.let { cLocation ->
            val latLng = LatLng(cLocation.latitude, cLocation.longitude)
            val markerOptions = MarkerOptions().position(latLng).title("I am here!")
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
            mMap.addMarker(markerOptions)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }


    private fun setupObservers() {
        viewModel.fetchCarResponse()
        viewModel.response.observe(requireActivity()) { response ->
            when (response.status.name) {
                "SUCCESS" -> {
                    response.data?.let {
                        viewModel.carList.addAll(it)
                    }
                    addCarMarker()
                }
                "ERROR" -> {
                    print(response.message)
                }
                "LOADING" -> {
                    print(response.message)
                }
            }
        }
    }

    private fun addCarMarker() {
        for (car in viewModel.carList) {
            car.lat?.let { car.lon?.let { it1 -> LatLng(it, it1) } }?.let { latLng ->
                val markerOptions = MarkerOptions().position(latLng).title(car.title).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.icons_car)
                )
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                mMap.addMarker(markerOptions)?.tag = car
                mMap.setOnMarkerClickListener(this)
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Retrieve the data from the marker.
        val clickCount = marker.tag as? CarItem

        // Check if a click count was set, then display the click count.
        clickCount?.let {
            Toast.makeText(
                requireContext(),
                "${it.title} has been clicked.",
                Toast.LENGTH_SHORT
            ).show()
            it.carId?.let { it1 -> onClickedCar(it1) }
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    fun onClickedCar(carId: Int) {
        findNavController().navigate(
            R.id.action_carsFragment_to_carDetailFragment,
            bundleOf("id" to carId)
        )
    }
}