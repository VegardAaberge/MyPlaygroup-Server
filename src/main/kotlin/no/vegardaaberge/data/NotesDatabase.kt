package no.vegardaaberge.data

import no.vegardaaberge.data.model.User
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

private val client = KMongo.createClient().coroutine
private val database = client.getDatabase("MyPlaygroupDatabase")
private val users = database.getCollection<User>()

suspend fun registerUser(user: User) : Boolean {
    return users.insertOne(user).wasAcknowledged()
}

suspend fun checkIfUserExists(email: String) : Boolean {
    return users.findOne(User::username eq email) != null
}

suspend fun checkPasswordForEmail(email: String, passwordToCheck: String) : Boolean {
    val actualPassword = users.findOne(User::username eq email)?.password ?: return false
    return actualPassword == passwordToCheck
}