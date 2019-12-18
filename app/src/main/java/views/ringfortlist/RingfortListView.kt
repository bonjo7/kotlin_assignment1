package views.ringfortlist

import activities.Settings
import views.Login.LoginView
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bernardthompson_assignment1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import models.RingfortModel
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import views.BaseView
import views.map.RingfortMapView
import views.ringfort.RingfortView

class RingfortListView : BaseView(), RingfortListener {

    lateinit var presenter: RingfortListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_list)
        super.init(toolbar, true)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)

        presenter = initPresenter(RingfortListPresenter(this)) as RingfortListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadRingforts()
    }

     override fun showRingforts(ringforts: List<RingfortModel>) {
        recyclerView.adapter = RingfortAdapter(ringforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRingfortClick(ringfort: RingfortModel) {
        presenter.doEditRingfort(ringfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadRingforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

        private val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.logoutBottom -> presenter.doLogout()
            R.id.item_add -> presenter.doAddRingfort()
            R.id.settings_bottom -> presenter.doSettings()
            R.id.item_map -> presenter.doShowRingfortsMap()
        }
        false
    }

}

