package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RingfortModel(var id: Long = 0, var name: String = "", var description: String = "") : Parcelable

