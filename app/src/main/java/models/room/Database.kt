package models.room

import androidx.room.Database
import androidx.room.RoomDatabase
import models.RingfortModel

@Database(entities = arrayOf(RingfortModel::class), version = 1,  exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun ringfortDao(): RingfortDao
}