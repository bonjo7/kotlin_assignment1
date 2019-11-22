package activities

import com.example.bernardthompson_assignment1.RingfortMapsActivity
import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class RingfortListPresenter(val view: RingfortActivityList) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getRingforts() = app.ringforts.findAll()

    fun doAddRingfort() {
        view.startActivityForResult<RingfortActivity>(0)
    }

    fun doEditRingfort(ringfort: RingfortModel) {
        view.startActivityForResult(view.intentFor<RingfortActivity>().putExtra("ringfort_edit", ringfort), 0)
    }

    fun doShowRingfortssMap() {
        view.startActivity<RingfortMapsActivity>()
    }
}
