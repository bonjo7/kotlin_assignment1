//package models
//
//import org.jetbrains.anko.AnkoLogger
//import org.jetbrains.anko.info
//
//var lastId = 0L
//
//internal fun getId(): Long {
//    return lastId++
//}
//
//class RingfortMemStore : RingfortStore, AnkoLogger {
//
//    val ringforts = ArrayList<RingfortModel>()
//
//    override fun findAll(): List<RingfortModel> {
//        return ringforts
//    }
//
//    override fun create(ringfort: RingfortModel) {
//        ringfort.id = getId()
//        ringforts.add(ringfort)
//        logAll()
//    }
//
//    override fun delete(ringfort: RingfortModel){
//        ringforts.remove(ringfort)
//    }
//
//    override fun update(ringfort: RingfortModel) {
//        var foundRingfort: RingfortModel? = ringforts.find { p -> p.id == ringfort.id}
//        if(foundRingfort != null) {
//            foundRingfort.name = ringfort.name
//            foundRingfort.description = ringfort.description
//            foundRingfort.image = ringfort.image
//            foundRingfort.lat = ringfort.lat
//            foundRingfort.lng = ringfort.lng
//            foundRingfort.zoom = ringfort.zoom
//            foundRingfort.visited = ringfort.visited
//            foundRingfort.visitedDate = ringfort.visitedDate
//        }
//    }
//
//    fun logAll() {
//        ringforts.forEach{ info("${it}") }
//    }
//
//}