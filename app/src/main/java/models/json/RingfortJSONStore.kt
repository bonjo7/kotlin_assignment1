package models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import helpers.exists
import helpers.read
import helpers.write
import models.RingfortModel
import models.RingfortStore
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

    override fun findById(id:Long) : RingfortModel? {
        val foundPlacemark: RingfortModel? = ringforts.find { it.id == id }
        return foundPlacemark
    }

//    override fun findUserRingforts(userId: Long): List<RingfortModel> {
//        return ringforts.filter { it.user == userId }
//    }


    override fun update(ringfort: RingfortModel) {
        var foundRingfort: RingfortModel? = ringforts.find { p -> p.id == ringfort.id}
        if(foundRingfort != null) {
            foundRingfort.name = ringfort.name
            foundRingfort.description = ringfort.description
            foundRingfort.image = ringfort.image
            foundRingfort.location = ringfort.location
            foundRingfort.visited = ringfort.visited
            foundRingfort.visitedDate = ringfort.visitedDate
            foundRingfort.rating = ringfort.rating
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

    override fun clear() {
        ringforts.clear()
    }
}