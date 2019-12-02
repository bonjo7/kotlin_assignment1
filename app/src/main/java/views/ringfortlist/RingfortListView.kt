package views.ringfortlist

import activities.Settings
import activities.UserLogin
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bernardthompson_assignment1.R
import com.example.bernardthompson_assignment1.RingfortAllMapsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import models.RingfortModel
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import views.map.RingfortMapView
import views.ringfort.RingfortView

class RingfortListView : AppCompatActivity(), RingfortListener {

    lateinit var presenter: RingfortListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_list)
        toolbar.title = title
        setSupportActionBar(toolbar)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)

        presenter = RingfortListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter =
            RingfortAdapter(presenter.getRingforts(), this)
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
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

        private val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.logoutBottom -> {
                startActivity(Intent(this@RingfortListView, UserLogin::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_add -> {
                startActivityForResult<RingfortView>(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings_bottom -> {
                startActivity(Intent(this@RingfortListView, Settings::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_map -> {
                startActivity<RingfortMapView>()
                return@OnNavigationItemSelectedListener  true
            }


        }
        false
    }

//    lateinit var app: MainApp
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_ringfort_list)
//
//        app = application as MainApp
//
//        toolbar.title = title
//        setSupportActionBar(toolbar)
//
//        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
//
//        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)
//
//        val layoutManager = LinearLayoutManager(this)
//        recyclerView.layoutManager = layoutManager
//        loadRingforts()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when (item?.itemId) {
//            R.id.item_add -> {
//                startActivityForResult<RingfortView>(0)
//            }
//            R.id.logout -> {
//                finish()
//                startActivity(Intent(this@RingfortListView, UserLogin::class.java))
//            }
//        }
//
//
//        return super.onOptionsItemSelected(item)
//    }
//
//    private val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.logoutBottom -> {
//                startActivity(Intent(this@RingfortListView, UserLogin::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.item_add -> {
//                startActivityForResult<RingfortView>(0)
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.settings_bottom -> {
//                startActivity(Intent(this@RingfortListView, Settings::class.java))
//                return@OnNavigationItemSelectedListener true
//            }
//            R.id.item_map -> {
//                startActivity<RingfortAllMapsActivity>()
//                return@OnNavigationItemSelectedListener  true
//            }
//
//
//        }
//        false
//    }
//
//    override fun onRingfortClick(ringfort: RingfortModel) {
//
//        startActivityForResult(intentFor<RingfortView>().putExtra("ringfort_edit", ringfort), 0)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        loadRingforts()
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    private fun loadRingforts() {
//        showRingforts(app.ringforts.findAll())
//    }
//
//    fun showRingforts (ringforts : List<RingfortModel>) {
//        recyclerView.adapter = RingfortAdapter(ringforts, this)
//        recyclerView.adapter?.notifyDataSetChanged()
//    }
}

