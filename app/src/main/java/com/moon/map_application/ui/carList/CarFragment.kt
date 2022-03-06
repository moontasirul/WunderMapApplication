package com.moon.map_application.ui.carList

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.moon.map_application.R
import com.moon.map_application.data.model.CarItem
import com.moon.map_application.databinding.BottomSheetDialogBinding
import com.moon.map_application.databinding.FragmentCarBinding
import com.moon.map_application.ui.carDetails.CarDetailsFragment
import com.moon.map_application.utils.Constants.Companion.TIMESTAMP_FORMAT
import com.moon.map_application.utils.Utils
import com.moon.map_application.utils.dialogUtils.CustomDialogCallback
import com.moon.map_application.utils.dialogUtils.CustomDialogFragment
import com.moon.map_application.utils.extension.LongExtension.toDate
import com.moon.map_application.utils.extension.StringExtension.toDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CarFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    ICarListNavigator {

    companion object {
        private var REQUEST_CODE = 101
        const val CAR_ID = "id"
    }

    private lateinit var carBinding: FragmentCarBinding
    private val viewModel by viewModels<CarViewModel>()
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var currentLocation: Location

    private var clickCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        carBinding = FragmentCarBinding.inflate(layoutInflater)
        return carBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setNavigator(this)
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
        if (Utils.isLocationEnabled(requireContext())) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                    val mapFragment =
                        childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)

                }
            }
        } else {
            showDialog(
                requireContext().getString(R.string.msg_failed),
                "Device Location is not enable. \n Please enable Location service.",
                true
            )
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
            mMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
            mMap?.addMarker(markerOptions)
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
        viewModel.isLoading.set(true)
        viewModel.fetchCarResponse()
        viewModel.response.observe(requireActivity()) { response ->
            viewModel.getCarResponse(response)
        }

        showReservationInfo()
    }

    override fun addCarMarker() {
        if (Utils.isLocationEnabled(requireContext())) {
            mMap.let {
                for (car in viewModel.carList) {
                    car.lat?.let { car.lon?.let { it1 -> LatLng(it, it1) } }?.let { latLng ->
                        val markerOptions = MarkerOptions().position(latLng).title(car.title).icon(
                            BitmapDescriptorFactory.fromResource(R.drawable.icons_car)
                        )
                        mMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        mMap?.addMarker(markerOptions)?.tag = car
                        mMap?.setOnMarkerClickListener(this)
                    }
                }
            }
        }

    }


    override fun onMarkerClick(marker: Marker): Boolean {

        // Retrieve the data from the marker.
        val carMarker = marker.tag as? CarItem

        // Check if a click count was set, then display the click count.
        carMarker?.let { car ->
            if (clickCount == 2) {
                car.carId?.let { it1 -> onClickedCar(it1) }
                clickCount = 0
            } else {
                mMap?.clear()
                car.lat?.let {
                    car.lon?.let { it1 ->
                        LatLng(it, it1)
                    }
                }?.let { latLng ->
                    val markerOptions = MarkerOptions().position(latLng).title(car.title).icon(
                        BitmapDescriptorFactory.fromResource(R.drawable.icons_car)
                    )
                    mMap?.setOnMarkerClickListener(this)
                    mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                    mMap?.addMarker(markerOptions)?.tag = car
                    mMap?.addMarker(markerOptions)?.showInfoWindow()
                }
            }
            clickCount += 1
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }

    private fun onClickedCar(carId: Int) {
        findNavController().navigate(
            R.id.action_carsFragment_to_carDetailFragment,
            bundleOf(CAR_ID to carId)
        )
    }

    fun showDialog(title: String, message: String, isOnlyPositive: Boolean) {
        var dialog: DialogFragment? = null
        var bntText = requireContext().getString(R.string.yes_txt)
        if (isOnlyPositive) {
            bntText = requireContext().getString(R.string.btn_text_ok)
        }
        dialog = CustomDialogFragment(
            title,
            message,
            bntText,
            requireContext().getString(R.string.cancel_text),
            object : CustomDialogCallback {
                override fun onNextClick() {
                    dialog?.dismiss()
                }

                override fun onPositiveClick() {
                    dialog?.dismiss()
                }

                override fun onCloseClick() {
                    dialog?.dismiss()
                }
            },
            isOnlyPositive,
        )
        dialog.show(childFragmentManager, CarDetailsFragment.DIALOG_TAG)
    }


    @SuppressLint("SetTextI18n")
    fun showReservationInfo() {
        if (viewModel.getReservationData() != null) {
            val response = viewModel.getReservationData()

            val dialog = BottomSheetDialog(requireContext())
            val bottomSheetBinding = BottomSheetDialogBinding.inflate(layoutInflater)



            bottomSheetBinding.carIdTv.text = "Car Id: ${response?.carId}"
            bottomSheetBinding.reservationIdTv.text =
                "reservation Id:  ${response?.reservationId}"
            bottomSheetBinding.reservationCostTv.text =
                "reservationCost:  ${response?.cost}"
            bottomSheetBinding.drivenDistanceTv.text =
                "drivenDistance:  ${response?.drivenDistance}"
            bottomSheetBinding.licencePlateTv.text =
                "licencePlate:  ${response?.licencePlate}"
            bottomSheetBinding.startAddressTv.text =
                "startAddress:  ${response?.startAddress}"
            bottomSheetBinding.parkModeEnabledTv.text =
                "parkModeEnabled:  ${response?.isParkModeEnabled}"
            bottomSheetBinding.damageDescriptionTv.text =
                "damageDescription:  ${response?.damageDescription}"
            bottomSheetBinding.fuelCardPinTv.text =
                "fuelCardPin:  ${response?.fuelCardPin}"
            bottomSheetBinding.startTimeTv.text = "startTime:  ${
                response?.startTime?.toLong().toDate(TIMESTAMP_FORMAT).toDate(TIMESTAMP_FORMAT)
            }"
            bottomSheetBinding.endTimeTv.text =
                "endTime:  ${response?.endTime?.toLong()?.let { Date(it) }}"

            bottomSheetBinding.idBtnDismiss.setOnClickListener {
                //dialog.dismiss()


                dialog.setCancelable(true)
                dialog.setContentView(bottomSheetBinding.root)
                dialog.show()

            }
        }
    }
}