package activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class UserRegister : AppCompatActivity(), AnkoLogger {

    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        info("Register activity started")

        tvlogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@UserRegister, UserLogin::class.java))
                info("TextView tvlogin clicked\nRedirecting to login page")
            }
        })
    }
}