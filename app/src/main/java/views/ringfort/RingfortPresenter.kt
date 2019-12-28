package views.ringfort

import android.annotation.SuppressLint
import helpers.showImagePicker
import models.Location
import models.RingfortModel
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
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
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
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
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(Location(l.latitude, l.longitude))
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
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
                locationUpdate(defaultLocation)
            }
        }

        fun doConfigureMap(m: GoogleMap) {
            map = m
            locationUpdate(ringfort.location)
        }

    fun locationUpdate(location: Location) {
        ringfort.location = location
        ringfort.location.zoom = 15f
        map?.clear()
        val options = MarkerOptions().title(ringfort.name).position(LatLng(ringfort.location.lat, ringfort.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(ringfort.location.lat, ringfort.location.lng), ringfort.location.zoom))
        view?.showLocation(ringfort.location)
    }


    fun doAddOrSave(name: String, description: String, visitedDateL: String, visitedL: Boolean, favourite: Boolean, rating: Float) {
        ringfort.name = name
        ringfort.description = description
        ringfort.visitedDate = visitedDateL
        ringfort.visited = visitedL
        ringfort.favourite = favourite
        ringfort.rating = rating
        doAsync {
            if (edit) {
                app.ringforts.update(ringfort)
            } else {
                app.ringforts.create(ringfort)
            }
            uiThread {
                view?.finish()
            }
        }
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
                Location(ringfort.location.lat, ringfort.location.lng, ringfort.location.zoom)
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
                    ringfort.location = location
                    locationUpdate(location)
                }
            }
        }

    @SuppressLint("MissingPermission")
    fun doResartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(Location(l.latitude, l.longitude))
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    fun shareRingfort(){
        val details = "Checkout this Ringfort" +
                "\nName: ${ringfort.name}" +
                "\nDescription: ${ringfort.description}" +
                "\nRating: ${ringfort.rating}" +
                "\nLocation: ${ringfort.location}"

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, details);
        view!!.startActivity(Intent.createChooser(shareIntent,"Share via"))
    }

    }

