package models

import android.widget.EditText


interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
//    fun delete(user: UserModel)
//    fun findByID(id:Long) : UserModel?
}