package models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var userlastId = 0L

internal fun getUserId(): Long {
    return userlastId++
}

class UserMemStore : UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = getUserId()
        users.add(user)
        logAll()
    }

//    override fun delete(user: UserModel){
//        users.remove(user)
//    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id}
        if(foundUser != null) {
            foundUser.userName = user.userName
            foundUser.userEmail = user.userEmail
            foundUser.userPassword = user.userPassword

        }
    }

    fun logAll() {
        users.forEach{ info("${it}") }
    }

}