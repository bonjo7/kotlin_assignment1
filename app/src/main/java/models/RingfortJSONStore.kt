package models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import helpers.exists
import helpers.read
import helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "ringforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<RingfortModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RingfortJSONStore : RingfortStore, AnkoLogger {

    val context: Context
    var ringforts = mutableListOf<RingfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RingfortModel> {
        return ringforts
    }

    override fun create(ringfort: RingfortModel) {
        ringfort.id = generateRandomId()
        ringforts.add(ringfort)
        serialize()
    }


    override fun update(ringfort: RingfortModel) {
        var foundRingfort: RingfortModel? = ringforts.find { p -> p.id == ringfort.id}
        if(foundRingfort != null) {
            foundRingfort.name = ringfort.name
            foundRingfort.description = ringfort.description
            foundRingfort.image = ringfort.image
            foundRingfort.lat = ringfort.lat
            foundRingfort.lng = ringfort.lng
            foundRingfort.zoom = ringfort.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(ringforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        ringforts = Gson().fromJson(jsonString, listType)
    }

    override fun delete(ringfort: RingfortModel){
        ringforts.remove(ringfort)
        serialize()
    }
}