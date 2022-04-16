package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class AuthDataSourceImpl(
    private val db: CoroutineDatabase
) : AuthDataSource {

    private val users = db.getCollection<User>()

    override suspend fun registerUser(user: User): Boolean {
        return users
            .insertOne(user)
            .wasAcknowledged()
    }

    override suspend fun checkIfUserExist(username: String): Boolean {
        return users.findOne(User::username eq username) != null
    }

    override suspend fun checkPasswordForUser(username: String, password: String): Boolean {
        val actualPassword = users
            .findOne(User::username eq username)
            ?.password ?: return false

        return actualPassword == password
    }
}