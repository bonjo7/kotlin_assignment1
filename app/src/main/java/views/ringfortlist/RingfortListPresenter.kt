package views.ringfortlist

import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView
import views.VIEW
import views.ringfort.RingfortView

class RingfortListPresenter(view: BaseView) : BasePresenter(view) {

//    fun getRingforts() = app.ringforts.findAll()
//
//    fun doEditRingfort(ringfort: RingfortModel) {
//        view.startActivityForResult(view.intentFor<RingfortView>().putExtra("ringfort_edit", ringfort), 0)
//    }

//    fun doShowRingfortssMap() {
//        view.startActivity<EditLocationView>()
//    }

    fun doAddRingfort() {
        view?.navigateTo(VIEW.RINGFORT)
    }

    fun doEditRingfort(ringfort: RingfortModel) {
        view?.navigateTo(VIEW.RINGFORT, 0, "ringfort_edit", ringfort)
    }

    fun doShowRingfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadRingforts() {
        doAsync {
        val ringforts = app.ringforts.findAll()
            uiThread {
                view?.showRingforts(ringforts)
            }
        }
    }

    fun doLogout() {
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doSettings() {
        view?.navigateTo((VIEW.SETTINGS))
    }
}
