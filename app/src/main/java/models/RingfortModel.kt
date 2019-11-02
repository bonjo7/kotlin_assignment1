package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RingfortModel(var id: Long = 0,
                         var name: String = "",
                         var description: String = "",
                         var image: String = "",
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f,
                         var user: Long = 0,
                         var visited: Boolean = false,
                         var visitedDate: String = ""
                         ) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

