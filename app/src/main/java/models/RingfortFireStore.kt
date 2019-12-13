package models

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger

class RingfortFireStore(val context: Context) : RingfortStore, AnkoLogger {

    val ringforts = ArrayList<RingfortModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun findAll(): List<RingfortModel> {
        return ringforts
    }

    override fun findById(id: Long): RingfortModel? {
        val foundRingfort: RingfortModel? = ringforts.find { p -> p.id == id }
        return foundRingfort
    }

    override fun create(ringfort: RingfortModel) {
        val key = db.child("users").child(userId).child("ringforts").push().key
        key?.let {
            ringfort.fbId = key
            ringforts.add(ringfort)
            db.child("users").child(userId).child("ringforts").child(key).setValue(ringfort)
        }
    }

    override fun update(ringfort: RingfortModel) {
        var foundRingfort: RingfortModel? = ringforts.find { p -> p.fbId == ringfort.fbId }
        if (foundRingfort != null) {
            foundRingfort.name = ringfort.name
            foundRingfort.description = ringfort.description
            foundRingfort.image = ringfort.image
            foundRingfort.location = ringfort.location
            foundRingfort.visited = ringfort.visited
            foundRingfort.visitedDate = ringfort.visitedDate
        }

        db.child("users").child(userId).child("ringforts").child(ringfort.fbId).setValue(ringfort)

    }

    override fun delete(ringfort: RingfortModel) {
        db.child("users").child(userId).child("ringforts").child(ringfort.fbId).removeValue()
        ringforts.remove(ringfort)
    }

    override fun clear() {
        ringforts.clear()
    }

    fun fetchRingforts(ringfortsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(ringforts) { it.getValue<RingfortModel>(RingfortModel::class.java) }
                ringfortsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        ringforts.clear()
        db.child("users").child(userId).child("ringforts").addListenerForSingleValueEvent(valueEventListener)
    }
}