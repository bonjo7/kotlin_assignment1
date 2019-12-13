package models

interface RingfortStore {
    fun findAll(): List<RingfortModel>
    fun create(ringfort: RingfortModel)
    fun update(ringfort: RingfortModel)
    fun delete(ringfort: RingfortModel)
//    fun findUserRingforts(userId: Long): List<RingfortModel>
    fun findById(id:Long) : RingfortModel?
    fun clear()
}