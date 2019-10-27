package activities

import android.app.Activity
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

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        app = application as MainApp

        var edit = false

        if (intent.hasExtra("ringfort_edit")) {
            edit = true
            ringfort = intent.extras?.getParcelable<RingfortModel>("ringfort_edit")!!
            ringfortName.setText(ringfort.name)
            ringfortDescription.setText(ringfort.description)
            btnAdd.setText(R.string.save_ringfort)
            btnAdd.setBackgroundColor(getColor(R.color.orange))
        }

        btnAdd.setOnClickListener() {

            ringfort.name = ringfortName.text.toString()
            ringfort.description = ringfortDescription.text.toString()

            if (ringfort.name.isEmpty()) {
                toast(R.string.enter_ringfort_details)
            }else{
                if(edit){
                    app.ringforts.update(ringfort.copy())
                } else{
                    app.ringforts.create(ringfort.copy())
                }
                info("Ringfort created with the details - \nID: ${ringfort.id}\nName: ${ringfort.name}\nDesc: ${ringfort.description}")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        chooseImage.setOnClickListener {
            info ("Select image")
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
