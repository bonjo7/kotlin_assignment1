package views.ringfortlist

import android.view.MenuItem
import android.widget.Toast
import com.example.bernardthompson_assignment1.R
import com.google.firebase.auth.FirebaseAuth
import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread
import views.BasePresenter
import views.BaseView
import views.VIEW
import views.ringfort.RingfortView

class RingfortListPresenter(view: BaseView) : BasePresenter(view) {

    fun doAddRingfort() {
        view?.navigateTo(VIEW.RINGFORT)
    }

    fun doEditRingfort(ringfort: RingfortModel) {
        view?.navigateTo(VIEW.RINGFORT, 0, "ringfort_edit", ringfort)
    }

    fun doShowRingfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    /*
    Load ringforts based on what the switch icon being selected or not
    if true show ringforts which are favourites
    If false show all ringforts
     */
    fun loadRingforts(fav: Boolean) {
        doAsync {
//        val ringforts = app.ringforts.findAll()
            if(!fav){
            uiThread {
                view?.showRingforts(app.ringforts.findAll())
            }}
            else if(fav){
                uiThread {
                    view?.showRingforts(app.ringforts.findAll().filter { it.favourite })
                }
            }
        }

    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }

    fun doSettings() {
        view?.navigateTo((VIEW.SETTINGS))
    }

    /*
    Load ringforts based on the users search criteria
     */
    fun loadRingfortsSearch(containingString: String)
    {
            view?.showRingforts(app.ringforts.findAll().filter { it.name.toLowerCase().contains(containingString.toLowerCase()) })
    }
}
