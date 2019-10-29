package activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_login.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.io.File

class UserLogin : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        info("Login activity started")

        app = application as MainApp

        loadUsers()

        tvreg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@UserLogin, UserRegister::class.java))
                info("Textview tvreg clicked\nRedirecting to register page")
            }
        })

        btnLogin.setOnClickListener {

            loadUsers()

            val passEmail = Lname.text.toString()
            val passPassword = Lpassword.text.toString()

            val userJSONFile: String = File("/data/data/com.example.bernardthompson_assignment1/files/users.json").readText(Charsets.UTF_8)
            info("Reading User JSON file")

            if(user.userEmail.equals(passEmail) && user.userPassword.equals(passPassword)) {

                startActivity(Intent(this@UserLogin, RingfortActivityList::class.java))
            }else{
                toast("Wrong details, try again")
            }


        }
    }

    private fun loadUsers() {
        app.users.findAll()
        info(user)
    }

}