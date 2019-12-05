package views.Login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import views.BaseView

class LoginView : BaseView() {

    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progressBar.visibility = View.GONE

        info("Login activity started")

        presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

        tvreg.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(this@LoginView, RegisterView::class.java))
                info("Textview tvreg clicked\nRedirecting to register page")
            }
        })

        btnLogin.setOnClickListener {

            val passEmail = Lname.text.toString()
            val passPassword = Lpassword.text.toString()

            if (passEmail == "" || passPassword == "") {
                toast("Please provide email + password")
            }
            else {
                presenter.doLogin(passEmail, passPassword)
            }
        }

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

}