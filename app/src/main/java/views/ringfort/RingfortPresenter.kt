package views.ringfort

import android.annotation.SuppressLint
import helpers.showImagePicker
import main.MainApp
import models.Location
import models.RingfortModel
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import helpers.checkLocationPermissions
import helpers.createDefaultLocationRequest
import helpers.isPermissionGranted
import views.editLocation.EditLocationView
import org.jetbrains.anko.intentFor
import views.*

class RingfortPresenter (view: BaseView) : BasePresenter(view) {

    var map: GoogleMap? = null

    var ringfort = RingfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;

    var locationService: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(view)

    val locationRequest = createDefaultLocationRequest()



    init {
        if (view.intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = view.intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            view.showRingfort(ringfort)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

        override fun doRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            if (isPermissionGranted(requestCode, grantResults)) {
                doSetCurrentLocation()
            } else {
                locationUpdate(defaultLocation.lat, defaultLocation.lng)
            }
        }

        fun doConfigureMap(m: GoogleMap) {
            map = m
            locationUpdate(ringfort.lat, ringfort.lng)
        }

    fun locationUpdate(lat: Double, lng: Double) {
        ringfort.lat = lat
        ringfort.lng = lng
        ringfort.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(ringfort.name).position(LatLng(ringfort.lat, ringfort.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(ringfort.lat, ringfort.lng), ringfort.zoom))
        view?.showRingfort(ringfort)
    }

        fun doAddOrSave(
            title: String,
            description: String,
            visitedDateL: String,
            visitedL: Boolean
        ) {
            ringfort.name = title
            ringfort.description = description
            ringfort.visited = visitedL
            ringfort.visitedDate = visitedDateL
            if (edit) {
                app.ringforts.update(ringfort)
            } else {
                app.ringforts.create(ringfort)
            }
            view?.finish()
        }

        fun doCancel() {
            view?.finish()
        }

        fun doDelete() {
            app.ringforts.delete(ringfort)
            view?.finish()
        }

        fun doSelectImage() {
            view?.let {
                showImagePicker(view!!, IMAGE_REQUEST)
            }
        }

        fun doSetLocation() {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(ringfort.lat, ringfort.lng, ringfort.zoom)
            )

        }

        override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
            when (requestCode) {
                IMAGE_REQUEST -> {
                    ringfort.image = data.data.toString()
                    view?.showRingfort(ringfort)
                }
                LOCATION_REQUEST -> {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    ringfort.lat = location.lat
                    ringfort.lng = location.lng
                    ringfort.zoom = location.zoom
                    locationUpdate(ringfort.lat, ringfort.lng)
                }
            }
        }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    }

