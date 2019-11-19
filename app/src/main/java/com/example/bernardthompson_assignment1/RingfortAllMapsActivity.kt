package com.example.bernardthompson_assignment1

import activities.RingfortActivityList
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import helpers.readImageFromPath

import kotlinx.android.synthetic.main.activity_ringfort_all_maps.*
import kotlinx.android.synthetic.main.content_ringfort_all_maps.*
import main.MainApp

class RingfortAllMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    lateinit var map: GoogleMap

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as MainApp

        setContentView(R.layout.activity_ringfort_all_maps)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    fun configureMap() {
        map.setOnMarkerClickListener(this)
        map.uiSettings.setZoomControlsEnabled(true)
        app.ringforts.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        startActivity(Intent(this@RingfortAllMapsActivity, RingfortActivityList::class.java))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.home -> {
                onBackPressed()
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        val tag = marker.tag as Long
        val ringfort = app.ringforts.findById(tag)
        currentTitle.text = ringfort!!.name
        currentDescription.text = ringfort!!.description
        currentImage.setImageBitmap(readImageFromPath(this, ringfort.image))
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
