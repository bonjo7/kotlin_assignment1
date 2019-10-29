package activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class UserRegister : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    var user = UserModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        info("Register activity started")

        app = application as MainApp

        tvlogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@UserRegister, UserLogin::class.java))
                info("TextView tvlogin clicked\nRedirecting to login page")
            }
        })

        btnRegister.setOnClickListener {

            user.userName = username.text.toString()
            user.userEmail = useremail.text.toString()
            user.userPassword = userpassword.text.toString()

            if(user.userEmail.isEmpty() && user.userPassword.isEmpty()){
                toast(R.string.Userdetails)
            }else{
                app.users.create(user.copy())
                toast("Redirecting to login page....")
                info("Registered user\n" +
                        "Name: ${user.userName}\n" +
                        "Email: ${user.userEmail}\n" +
                        "ID: ${user.userId}")
                startActivity(Intent(this@UserRegister, UserLogin::class.java))


            }


        }
    }
}