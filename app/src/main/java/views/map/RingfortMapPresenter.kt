package views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import main.MainApp
import models.RingfortModel
import views.BasePresenter
import views.BaseView

class RingfortMapPresenter(view: BaseView) : BasePresenter(view) {

    fun doPopulateMap(map: GoogleMap, ringforts: List<RingfortModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        ringforts.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val ringfort = app.ringforts.findById(tag)
        if (ringfort != null) view?.showRingfort(ringfort)

    }

    fun loadPlacemarks() {
        view?.showRingforts(app.ringforts.findAll())
    }

}

