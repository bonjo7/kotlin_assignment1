package views

import views.settings.Settings
import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import models.Location
import models.RingfortModel
import org.jetbrains.anko.AnkoLogger
import views.Login.LoginView
import views.editLocation.EditLocationView
import views.map.RingfortMapView
import views.ringfort.RingfortView
import views.ringfortlist.RingfortListView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, RINGFORT, MAPS, LIST, LOGIN, SETTINGS
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, RingfortListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.RINGFORT -> intent = Intent(this, RingfortView::class.java)
            VIEW.MAPS -> intent = Intent(this, RingfortMapView::class.java)
            VIEW.LIST -> intent = Intent(this, RingfortListView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
            VIEW.SETTINGS -> intent = Intent(this, Settings::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: androidx.appcompat.widget.Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showRingfort(ringfort: RingfortModel) {}
    open fun showRingforts(ringforts: List<RingfortModel>) {}
    open fun showLocation(location : Location) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}