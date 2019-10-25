package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.activity_ringfort.*
import models.RingfortkModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class RingfortActivity : AppCompatActivity(), AnkoLogger {

    var ringfort = RingfortkModel()
    val ringforts = ArrayList<RingfortkModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ringfort)

        info("Archaeological Field Work App Started..")

        btnAdd.setOnClickListener() {
            ringfort.name = ringfortName.text.toString()
            ringfort.description = ringfortDescription.text.toString()
            if (ringfort.name.isNotEmpty()) {
                ringforts.add(ringfort.copy())
                info("add Button Pressed: $ringfortName")
                for(r in ringforts.indices){
                    info("Ringfort[$r]:${this.ringforts[r]}")
                }
            }
            else {
                toast ("Please Enter a title")
            }
        }
    }
}
