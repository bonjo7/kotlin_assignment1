package models

import android.widget.EditText
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

//var userlastId = 0L
//
//
//internal fun getUserId(): Long {
//    return userlastId++
//}
//
//class UserMemStore : UserStore, AnkoLogger {
//
//    val users = ArrayList<UserModel>()
//
//
//    override fun findAll(): List<UserModel> {
//        return users
//    }
//
//
//    override fun create(user: UserModel) {
//        user.userId = getUserId()
//        users.add(user)
//        logAll()
//    }
//
////    override fun delete(user: UserModel){
////        users.remove(user)
////    }
//
//    override fun update(user: UserModel) {
//        var foundUser: UserModel? = users.find { p -> p.userId == user.userId}
//        if(foundUser != null) {
//            foundUser.userName = user.userName
//            foundUser.userEmail = user.userEmail
//            foundUser.userPassword = user.userPassword
//
//        }
//    }
//
//    fun logAll() {
//        users.forEach{ info("${it}") }
//    }

//}