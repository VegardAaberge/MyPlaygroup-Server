package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.User

interface AuthDataSource {

    suspend fun registerUser(user: User) : Boolean

    suspend fun checkIfUserExist(username: String) : Boolean

    suspend fun checkPasswordForUser(username: String, password: String) : Boolean
}