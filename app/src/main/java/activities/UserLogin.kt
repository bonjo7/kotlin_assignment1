package activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_login.*
import main.MainApp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import views.ringfortlist.RingfortListView

class UserLogin : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var validateUser = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        info("Login activity started")

        app = application as MainApp

//        val users: List<UserModel> = app.users.findAll()

        tvreg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@UserLogin, UserRegister::class.java))
                info("Textview tvreg clicked\nRedirecting to register page")
            }
        })

        btnLogin.setOnClickListener {

            val passEmail = Lname.text.toString()
            val passPassword = Lpassword.text.toString()

//            val userJSONFile: String = File("/data/data/com.example.bernardthompson_assignment1/files/users.json").readText(Charsets.UTF_8)
            info("Reading User JSON file")

//            users.forEach { user ->
//                if (passEmail.equals(user.userEmail) && passPassword.equals(user.userPassword)) {
//                    validateUser = true
//                    intent.putExtra("user", user)
//                    startActivity(Intent(this@UserLogin, RingfortListView::class.java))
//                }else if(!validateUser) {
//
//                    toast("Wrong details, try again")
//
//                }
//            }

            val loggedinUset = app.users.login(passEmail, passPassword)

            if (loggedinUset) {

                var currentUser = app.users.findByEmail(passEmail)
                if (currentUser != null) {

                    app.currentUser = currentUser
                    startActivity(intentFor<RingfortListView>())

                }


            }
        }

    }}