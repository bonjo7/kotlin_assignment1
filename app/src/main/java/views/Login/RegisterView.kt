package views.Login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_register.*
import main.MainApp
import models.UserModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import views.BaseView

class RegisterView : BaseView() {

    lateinit var presenter: LoginPresenter

    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        info("Register activity started")

        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        tvlogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@RegisterView, LoginView::class.java))
                info("TextView tvlogin clicked\nRedirecting to login page")
            }
        })

        btnRegister.setOnClickListener {

            val email = useremail.text.toString()
            val password = userpassword.text.toString()

            if(email.isEmpty() && password.isEmpty()){
                toast(R.string.Userdetails)
            }else{
                presenter.doSignUp(email, password)
                startActivity(Intent(this@RegisterView, LoginView::class.java))


            }


        }
    }
}