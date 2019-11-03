package models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import helpers.exists
import helpers.read
import helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val USER_JSON_FILE = "users.json"
val USER_gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val USER_listType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun userGenerateRandomId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, USER_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): List<UserModel> {
        return users
    }

//    override fun findByID(id:Long) : UserModel?{
//    val foundUser: UserModel? = users.find { it.userId == id }
//    return foundUser
//}

    override fun create(user: UserModel) {
        user.userId = userGenerateRandomId()
        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.userId == user.userId}
        if(foundUser != null) {
            foundUser.userName = user.userName
            foundUser.userEmail = user.userEmail
            foundUser.userPassword = user.userPassword

        }
    }

    private fun serialize() {
        val jsonString = USER_gsonBuilder.toJson(users, USER_listType)
        write(context, USER_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = Gson().fromJson(jsonString, USER_listType)
    }
}

