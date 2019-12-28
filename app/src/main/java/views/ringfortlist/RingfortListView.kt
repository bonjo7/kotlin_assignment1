package views.ringfortlist

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bernardthompson_assignment1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_ringfort_list.*
import models.RingfortModel
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import views.BaseView

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
        presenter.loadRingforts(switch2.isChecked)


        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                    presenter.loadRingfortsSearch(newText!!)
                    recyclerView.adapter?.notifyDataSetChanged()

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {

                presenter.loadRingfortsSearch(query!!)
                recyclerView.adapter?.notifyDataSetChanged()
                
                return true
            }

        })

        switch2.setOnClickListener {
            if(switch2.isChecked){
                switch2.setThumbResource(R.drawable.favourite)
                presenter.loadRingforts(fav = true)
                recyclerView.adapter?.notifyDataSetChanged()
            }
            else{
                switch2.setThumbResource(R.drawable.heart_border_white)
                presenter.loadRingforts(fav = false)
                recyclerView.adapter?.notifyDataSetChanged()

            }

        }
   }

//    fun doOnOptionsItemSelected(item: MenuItem?) {
//        when (item?.itemId) {
//            R.id.undo_fav -> toast("ToDo")
//
//        }
//    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Chnaged to landscape view", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Changed to portrait view", Toast.LENGTH_SHORT).show()
        }
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

        presenter.loadRingforts(switch2.isChecked)
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

