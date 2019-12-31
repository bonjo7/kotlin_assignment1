package views.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_settings.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.*
import views.Login.LoginView
import views.ringfort.RingfortView
import views.ringfortlist.RingfortListView

class Settings : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var userc = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        var RingfortsSize = app.ringforts.findAll().size

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

        var allRingforts = app.ringforts.findAll().size
        progressBar2.max = allRingforts
        var visitedRingforts = app.ringforts.findAll().filter { it.visited }.size

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
            R.id.logoutBottom -> {
                startActivity(Intent(this@Settings, LoginView::class.java))
                return@OnNavigationItemSelectedListener true
                finish()
            }
            R.id.item_add -> {
                startActivityForResult<RingfortView>(0)
                return@OnNavigationItemSelectedListener true
            }
            R.id.settings_bottom -> {
                startActivity(Intent(this@Settings, Settings::class.java))
                return@OnNavigationItemSelectedListener true

            }

        }
        false
    }
}