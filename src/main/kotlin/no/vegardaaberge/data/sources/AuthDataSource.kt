package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.Reset
import no.vegardaaberge.data.model.User

interface AuthDataSource {

    suspend fun registerUser(user: User) : Boolean

    suspend fun checkIfUserExist(username: String) : Boolean

    suspend fun checkPasswordForUser(username: String, password: String) : Boolean
    suspend fun getUsernameFromEmail(email: String) : User?
    suspend fun insertResetRequest(resetRequest: Reset): Boolean

    suspend fun verifyResetRequestCode(code: String): Boolean
}