package models.room

import android.content.Context
import androidx.room.Room
import models.RingfortModel
import models.RingfortStore

class RingfortStoreRoom (val context: Context) : RingfortStore {

    var dao: RingfortDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "ringfort.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.ringfortDao()
    }

    override fun findAll(): List<RingfortModel> {
        return dao.findAll()
    }

    override fun findById(id: Long): RingfortModel? {
        return dao.findById(id)
    }

    override fun create(ringfort: RingfortModel) {
        dao.create(ringfort)
    }

    override fun update(ringfort: RingfortModel) {
        dao.update(ringfort)
    }

    override fun delete(ringfort: RingfortModel) {
        dao.deleteRingfort(ringfort)
    }

//    override fun findUserRingforts(userId: Long): List<RingfortModel> {
//        TODO("not implemented")
//    }

    override fun clear() {

    }
}