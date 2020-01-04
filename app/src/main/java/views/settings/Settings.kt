package views.settings

import android.content.Intent
import android.os.Bundle
import com.example.bernardthompson_assignment1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import models.UserModel
import org.jetbrains.anko.*
import views.BaseView
import views.ringfortlist.RingfortListView

class Settings : BaseView() {

    lateinit var presenter : SettingsPresenter
    var userc = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)

        presenter = SettingsPresenter(this)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var RingfortsSize = presenter.app.ringforts.findAll().size

        val user = FirebaseAuth.getInstance().currentUser!!.uid
        if (user != null) {
            updateuseremail.setText(FirebaseAuth.getInstance().currentUser?.email)
            updateuserpassword.setText("********")
        } else {
            toast("No user signed in")
        }

            save_user_settings.setOnClickListener(){
                val user = FirebaseAuth.getInstance().currentUser
                user!!.updateEmail(updateuseremail.toString())
                user!!.updatePassword(updateuserpassword.toString())
                info(user.email)

                startActivity(Intent(this@Settings, RingfortListView::class.java))

            }

        var allRingforts = presenter.app.ringforts.findAll().size
        progressBar2.max = allRingforts
        var visitedRingforts = presenter.app.ringforts.findAll().filter { it.visited }.size

        progressBar2.progress = visitedRingforts

        size.setText("Number of Ringforts: ${RingfortsSize} in total. " +
                "\nVisited Ringforts: ${visitedRingforts}")


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(Intent(this@Settings, RingfortListView::class.java))
        return true
    }

    private val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.logoutBottom ->  {
                presenter.doLogout()
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)
            }
            R.id.item_add ->{
                presenter.doAddRingfort()
                overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in)
            }
            R.id.settings_bottom -> {

                presenter.doSettings()
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            R.id.item_map -> {
                presenter.doShowRingfortsMap()
                overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
            }

        }
        false
    }
}