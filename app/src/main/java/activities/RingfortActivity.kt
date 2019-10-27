package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_ringfort.*
import main.MainApp
import models.RingfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class RingfortActivity : AppCompatActivity(), AnkoLogger {

    var ringfort = RingfortModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)

        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("ringfort_edit")) {
            ringfort = intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            ringfortName.setText(ringfort.name)
            ringfortDescription.setText(ringfort.description)
        }

        btnAdd.setOnClickListener() {
            ringfort.name = ringfortName.text.toString()
            ringfort.description = ringfortDescription.text.toString()

            if (ringfort.name.isNotEmpty() && ringfort.description.isNotEmpty()) {
                app.ringforts.create(ringfort.copy())
                info("add Button Pressed: $ringfortName")

                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast (R.string.enter_ringfort_details)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ringfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
