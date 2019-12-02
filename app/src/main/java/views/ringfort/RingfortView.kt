package views.ringfort

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.bernardthompson_assignment1.R

import helpers.readImageFromPath
import kotlinx.android.synthetic.main.activity_ringfort.*
import models.RingfortModel
import org.jetbrains.anko.*
import views.BaseView
import java.util.*


class RingfortView : BaseView(), AnkoLogger {

        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)


    lateinit var presenter: RingfortPresenter
    var ringfort = RingfortModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        presenter = RingfortPresenter(this)

        chooseImage.setOnClickListener {
            presenter.doSelectImage()
        }

        ringfortLocation.setOnClickListener {
            presenter.doSetLocation()
        }

        visitedDateL.setOnClickListener {
            val datepicker = DatePickerDialog(
                this, R.style.DatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    visitedDateL.setText("$day - $month - $year")}, year, month, day
            )
            datepicker.show()

        }
    }

    override fun showRingfort(ringfort: RingfortModel) {
        ringfortName.setText(ringfort.name)
        ringfortDescription.setText(ringfort.description)
        visitedL.isChecked
        visitedDateL.setText(ringfort.visitedDate)
        ringfortImage.setImageBitmap(readImageFromPath(this, ringfort.image))

        if (ringfort.image != null) {
            chooseImage.setText(R.string.change_ringfort_image)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_ringfort, menu)
        if (presenter.edit && menu != null) menu.getItem(1).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                    if (ringfortName.text.toString().isEmpty()) {
                    toast(R.string.enter_ringfort_details)
                } else {
                    presenter.doAddOrSave(ringfortName.text.toString(), ringfortDescription.text.toString(), visitedDateL.text.toString(), visitedL.isChecked )
                    info("Ringfort created with the details - " +
                         "\nID: ${presenter.ringfort.id}" +
                         "\nName: ${presenter.ringfort.name}" +
                         "\nDesc: ${presenter.ringfort.description}" +
                         "\n Lat: ${presenter.ringfort.lat}, Long: ${presenter.ringfort.lng}" +
                         "\nVisted: ${ringfort.visited}" +
                         "\nDate Visited: ${presenter.ringfort.visitedDate}")
                }
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
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }


    }

    override fun onBackPressed() {
        presenter.doCancel()
    }


}