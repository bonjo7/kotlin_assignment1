package views.ringfort

import helpers.showImagePicker
import main.MainApp
import models.Location
import models.RingfortModel
import android.content.Intent
import views.editLocation.EditLocationView
import org.jetbrains.anko.intentFor
import views.*

class RingfortPresenter (view: BaseView) : BasePresenter(view){

    var ringfort = RingfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;

    init {
        if (view.intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = view.intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            view.showRingfort(ringfort)
        }
    }

    fun doAddOrSave(title: String, description: String, visitedDateL: String, visitedL: Boolean) {
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
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(
                VIEW.LOCATION,
                LOCATION_REQUEST,
                "location",
                Location(ringfort.lat, ringfort.lng, ringfort.zoom)
            )
        }
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
            }
        }
    }

//    val IMAGE_REQUEST = 1
//    val LOCATION_REQUEST = 2
//
//    var ringfort = RingfortModel()
//    var location = Location(52.245696, -7.139102, 15f)
//    var app: MainApp
//    var edit = false;
//
//    init {
//        app = view.application as MainApp
//        if (view.intent.hasExtra("ringfort_edit")) {
//            edit = true
//            ringfort = view.intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
//            view.showRingfort(ringfort)
//        }
//    }
//
//    fun doAddOrSave(title: String, description: String, visitedDateL: String, visitedL: Boolean) {
//        ringfort.name = title
//        ringfort.description = description
//        ringfort.visited = visitedL
//        ringfort.visitedDate = visitedDateL
//
//        if (edit) {
//            app.ringforts.update(ringfort)
//        } else {
//            app.ringforts.create(ringfort)
//
//        }
//        view.finish()
//    }
//
//    fun doCancel() {
//        view.finish()
//    }
//
//    fun doDelete() {
//        app.ringforts.delete(ringfort)
//
//        view.finish()
//    }
//
//    fun doSelectImage() {
//        showImagePicker(view, IMAGE_REQUEST)
//    }
//
//    fun doSetLocation() {
//        if (ringfort.zoom != 0f) {
//            location.lat = ringfort.lat
//            location.lng = ringfort.lng
//            location.zoom = ringfort.zoom
//        }
//        view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)
//    }
//
//
//    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        when (requestCode) {
//            IMAGE_REQUEST -> {
//                ringfort.image = data.data.toString()
//                view.showRingfort(ringfort)
//            }
//            LOCATION_REQUEST -> {
//                location = data.extras?.getParcelable<Location>("location")!!
//                ringfort.lat = location.lat
//                ringfort.lng = location.lng
//                ringfort.zoom = location.zoom
//            }
//        }
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            IMAGE_REQUEST -> {
//                if (data != null) {
//                    ringfort.image = data.getData().toString()
//                    ringfortImage.setImageBitmap(readImage(this, resultCode, data))
//                    chooseImage.setText(R.string.change_ringfort_image)
//                }
//            }
//            LOCATION_REQUEST -> {
//                if (data != null) {
//                    val location = data.extras?.getParcelable<Location>("location")!!
//                    ringfort.lat = location.lat
//                    ringfort.lng = location.lng
//                    ringfort.zoom = location.zoom
//                }
//            }
//        }
//    }
}