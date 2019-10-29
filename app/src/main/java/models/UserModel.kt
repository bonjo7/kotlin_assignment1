package models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var userId: Long = 0,
                     var userName: String = "",
                     var userEmail: String = "",
                     var userPassword: String =""): Parcelable