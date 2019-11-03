package activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bernardthompson_assignment1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class RingfortActivityList : AppCompatActivity(), RingfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort_list)

        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadRingforts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> {
                startActivityForResult<RingfortActivity>(0)
            }
            R.id.logout -> {
                finish()
                startActivity(Intent(this@RingfortActivityList, UserLogin::class.java))
            }
        }


        return super.onOptionsItemSelected(item)
    }

    private val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.logoutBottom -> {
                startActivity(Intent(this@RingfortActivityList, UserLogin::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.item_add -> {
                startActivityForResult<RingfortActivity>(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings_bottom -> {
                startActivity(Intent(this@RingfortActivityList, Settings::class.java))
                return@OnNavigationItemSelectedListener true

            }

        }
        false
    }

    override fun onRingfortClick(ringfort: RingfortModel) {

        startActivityForResult(intentFor<RingfortActivity>().putExtra("ringfort_edit", ringfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadRingforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadRingforts() {
        showRingforts(app.ringforts.findAll())
    }

    fun showRingforts (ringforts : List<RingfortModel>) {
        recyclerView.adapter = RingfortAdapter(ringforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}

