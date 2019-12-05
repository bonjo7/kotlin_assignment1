package views.splash

import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.example.bernardthompson_assignment1.R
import views.BaseView
import views.VIEW

class SplashView : BaseView() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //hiding title bar of this activity
        window.requestFeature(Window.FEATURE_NO_TITLE)
        //making this activity full screen
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        //4second splash time
        Handler().postDelayed({
            //start main activity
            navigateTo(VIEW.RINGFORT)
            //finish this activity
            finish()
        },4000)

    }
}
