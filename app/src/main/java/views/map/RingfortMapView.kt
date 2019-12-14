package views.map

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.bernardthompson_assignment1.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import kotlinx.android.synthetic.main.content_ringfort_all_maps.*
import models.RingfortModel
import views.BaseView
import views.ringfortlist.RingfortListView

class RingfortMapView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: RingfortMapPresenter
    lateinit var map : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_all_maps)
        super.init(toolbar, true)

        presenter = initPresenter (RingfortMapPresenter(this)) as RingfortMapPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadPlacemarks()
        }
    }

    override fun showRingfort(ringfort: RingfortModel) {
        currentTitle.text = ringfort.name
        currentDescription.text = ringfort.description
//        currentImage.setImageBitmap(readImageFromPath(this, ringfort.image))
        Glide.with(this).load(ringfort.image).into(currentImage);
    }

    override fun showRingforts(ringforts: List<RingfortModel>) {
        presenter.doPopulateMap(map, ringforts)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

}