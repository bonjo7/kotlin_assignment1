package activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_ringfort.toolbarAdd
import kotlinx.android.synthetic.main.activity_settings.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class Settings : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener(bottomListener)

        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp

        save_user_settings.setOnClickListener(){

//                useremail.setText(app.loggedInUser.userEmail)
//                userpassword.setText(app.loggedInUser.userPassword)

                user.userEmail = useremail.text.toString()
                user.userPassword = userpassword.text.toString()
                app.users.update(user)

                info("User ${user.userName} has been updated" +
                        "\nNew email: ${user.userEmail}" +
                        "\nNew Password: ${user.userPassword}")

                toast("Saved changes")

                startActivity(Intent(this@Settings, RingfortActivityList::class.java))
            }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        startActivity(Intent(this@Settings, RingfortActivityList::class.java))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {

            R.id.home -> {
                onBackPressed()
                true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private val bottomListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.logoutBottom -> {
                startActivity(Intent(this@Settings, UserLogin::class.java))
                return@OnNavigationItemSelectedListener true
                finish()
            }
            R.id.item_add -> {
                startActivityForResult<RingfortActivity>(0)
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