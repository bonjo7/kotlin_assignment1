package activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import com.example.bernardthompson_assignment1.R
import com.example.bernardthompson_assignment1.RingfortMapsActivity
import com.google.android.gms.maps.model.LatLng
import helpers.readImage
import helpers.readImageFromPath
import helpers.showImagePicker
import kotlinx.android.synthetic.main.activity_ringfort.*
import main.MainApp
import models.Location
import models.RingfortModel
import models.RingfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import java.util.*


class RingfortActivity : AppCompatActivity(), AnkoLogger {

    var ringfort = RingfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        var edit = false

        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        if (intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            ringfortName.setText(ringfort.name)
            ringfortDescription.setText(ringfort.description)
            ringfortImage.setImageBitmap(readImageFromPath(this, ringfort.image))
            if(ringfort.image != null){
                chooseImage.setText(R.string.change_ringfort_image)
                chooseImage.setBackgroundColor(getColor(R.color.orange))
            }
            btnAdd.setText(R.string.save_ringfort)
            btnAdd.setBackgroundColor(getColor(R.color.orange))
        }

        btnAdd.setOnClickListener() {

            ringfort.name = ringfortName.text.toString()
            ringfort.description = ringfortDescription.text.toString()

            if (ringfort.name.isEmpty()) {
                toast(R.string.enter_ringfort_details)
            }else{
                if(edit){
                    app.ringforts.update(ringfort.copy())
                } else{
                    app.ringforts.create(ringfort.copy())
                }
                info("Ringfort created with the details - " +
                        "\nID: ${ringfort.id}" +
                        "\nName: ${ringfort.name}" +
                        "\nDesc: ${ringfort.description}" +
                        "\n Lat: ${ringfort.lat}, Long: ${ringfort.lng}")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
            info ("Select image")
        }

        ringfortLocation.setOnClickListener {
            info ("Set Location Pressed")
        }

        ringfortLocation.setOnClickListener {
            val location = Location(52.2593, -7.1101, 15f)
            if(ringfort.zoom != 0f){
                location.lat = ringfort.lat
                location.lng = ringfort.lng
                location.zoom = ringfort.zoom
            }
            startActivity (intentFor<RingfortMapsActivity>().putExtra("location", location))
        }

        visitedDateL.setOnClickListener {
            val datepicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { DatePicker, year, month, day ->
                    visitedDateL.setText("$day - $month - $year")}, year, month, day
                )
            datepicker.show()

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        startActivity(Intent(this@RingfortActivity, RingfortActivityList::class.java))
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ringfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.ringforts.delete(ringfort)
                finish()
            }
            R.id.home -> {
                onBackPressed()
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    ringfort.image = data.getData().toString()
                    ringfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_ringfort_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    ringfort.lat = location.lat
                    ringfort.lng = location.lng
                    ringfort.zoom = location.zoom
                }
            }
        }
    }


}