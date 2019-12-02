package views.map

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import helpers.readImageFromPath
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import kotlinx.android.synthetic.main.content_ringfort_all_maps.*
import models.RingfortModel
import views.ringfortlist.RingfortListView

class RingfortMapView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: RingfortMapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_all_maps)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = RingfortMapPresenter(this)

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            presenter.doPopulateMap(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        startActivity(Intent(this@RingfortMapView, RingfortListView::class.java))
        return true
    }

    fun showPlacemark(ringfort: RingfortModel) {
        currentTitle.text = ringfort.name
        currentDescription.text = ringfort.description
        currentImage.setImageBitmap(readImageFromPath(this, ringfort.image))
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