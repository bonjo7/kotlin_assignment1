package models.room

import androidx.room.*
import models.RingfortModel

@Dao
interface RingfortDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(ringfort: RingfortModel)

    @Query("SELECT * FROM RingfortModel")
    fun findAll(): List<RingfortModel>

    @Query("select * from RingfortModel where id = :id")
    fun findById(id: Long): RingfortModel

    @Update
    fun update(ringfort: RingfortModel)

    @Delete
    fun deleteRingfort(ringfort: RingfortModel)
}
