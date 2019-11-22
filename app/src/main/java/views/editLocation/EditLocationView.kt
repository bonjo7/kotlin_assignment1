package views.editLocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bernardthompson_assignment1.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker


class EditLocationView : AppCompatActivity(), GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap
    lateinit var presenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        presenter = MapPresenter(this)
        mapFragment.getMapAsync {
            map = it
            map.setOnMarkerDragListener(this)
            map.setOnMarkerClickListener(this)
            presenter.initMap(map)
        }
    }

    override fun onMarkerDragStart(marker: Marker) {}

    override fun onMarkerDrag(marker: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude, map.cameraPosition.zoom)
    }

    override fun onBackPressed() {
        presenter.doOnBackPressed()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }

// AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {
//
//    private lateinit var mMap: GoogleMap
//    var location = Location()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_ringfort_maps)
//        location = intent.extras?.getParcelable<Location>("location")!!
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        mMap.setOnMarkerDragListener(this)
//        mMap.setOnMarkerClickListener(this)
//        val loc = LatLng(location.lat, location.lng)
////        val waterford = LatLng(52.2593, -7.1101)
//        val options = MarkerOptions()
//            .title("Placemark")
//            .snippet("GPS : " + loc.toString())
//            .draggable(true)
//            .position(loc)
//        mMap.addMarker(options)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
//    }
//
//    override fun onMarkerClick(marker: Marker): Boolean {
//        val loc = LatLng(location.lat, location.lng)
//        marker.setSnippet("GPS : " + loc.toString())
//        return false
//    }
//
//    override fun onMarkerDragStart(marker: Marker) {
//    }
//
//    override fun onMarkerDrag(marker: Marker) {
//    }
//
//    override fun onMarkerDragEnd(marker: Marker) {
//        location.lat = marker.position.latitude
//        location.lng = marker.position.longitude
//        location.zoom = mMap.cameraPosition.zoom
//    }
//
//    override fun onBackPressed() {
//        val resultIntent = Intent()
//        resultIntent.putExtra("location", location)
//        setResult(Activity.RESULT_OK, resultIntent)
//        finish()
//        super.onBackPressed()
//    }
}
