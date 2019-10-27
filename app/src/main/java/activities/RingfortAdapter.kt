package activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bernardthompson_assignment1.R
import kotlinx.android.synthetic.main.card_ringfort.view.*
import models.RingfortModel

interface RingfortListener {
    fun onRingfortClick(ringfort: RingfortModel)
}

class RingfortAdapter constructor(private var ringforts: List<RingfortModel>,
                                  private val listener : RingfortListener) :
                                    RecyclerView.Adapter<RingfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_ringfort,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val ringfort = ringforts[holder.adapterPosition]
        holder.bind(ringfort, listener)

    }

    override fun getItemCount(): Int = ringforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(ringfort: RingfortModel, listener : RingfortListener) {

            itemView.ringfortCName.text = ringfort.name
            itemView.ringfortCDescription.text = ringfort.description
            itemView.setOnClickListener { listener.onRingfortClick(ringfort) }
        }
    }
}