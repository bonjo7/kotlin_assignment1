package models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import helpers.readImageFromPath
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

class RingfortFireStore(val context: Context) : RingfortStore, AnkoLogger {

    val ringforts = ArrayList<RingfortModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

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
            updateImage(ringfort)
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
            foundRingfort.favourite = ringfort.favourite
            foundRingfort.rating = ringfort.rating
        }

        db.child("users").child(userId).child("ringforts").child(ringfort.fbId).setValue(ringfort)
        if ((ringfort.image.length) > 0 && (ringfort.image[0] != 'h')) {
            updateImage(ringfort)
        }

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

    fun updateImage(ringfort: RingfortModel) {
        if (ringfort.image != "") {
            val fileName = File(ringfort.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, ringfort.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        ringfort.image = it.toString()
                        db.child("users").child(userId).child("ringforts").child(ringfort.fbId).setValue(ringfort)
                    }
                }
            }
        }
    }
}