package views.ringfortlist

import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.intentFor
import views.ringfort.RingfortView

class RingfortListPresenter(val view: RingfortListView) {

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getRingforts() = app.ringforts.findAll()

    fun doEditRingfort(ringfort: RingfortModel) {
        view.startActivityForResult(view.intentFor<RingfortView>().putExtra("ringfort_edit", ringfort), 0)
    }

//    fun doShowRingfortssMap() {
//        view.startActivity<EditLocationView>()
//    }
}
