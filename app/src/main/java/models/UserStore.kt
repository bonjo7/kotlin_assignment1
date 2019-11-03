package models

import android.widget.EditText


interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
    fun update(user: UserModel)
//    fun delete(user: UserModel)
//    fun findByEmail(name: String): UserModel ?
//    fun login(password: String, email: String): Boolean
//    fun findByEmail(email: String): UserModel?
}