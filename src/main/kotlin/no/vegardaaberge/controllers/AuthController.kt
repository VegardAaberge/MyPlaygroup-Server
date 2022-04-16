package no.vegardaaberge.controllers

import no.vegardaaberge.data.model.User
import no.vegardaaberge.data.responses.SimpleResponse
import no.vegardaaberge.data.sources.AuthDataSource

class AuthController(
    private val authDataSource: AuthDataSource
) {
    suspend fun tryLogin(username: String, password: String) : SimpleResponse{

        val isPasswordCorrect = authDataSource.checkPasswordForUser(username, password)

        return if(isPasswordCorrect){
            SimpleResponse(true, "You are now logged in")
        }else{
            SimpleResponse(false, "The email or password is incorrect")
        }
    }

    suspend fun tryRegister(username: String, password: String) : SimpleResponse{

        val userExist = authDataSource.checkIfUserExist(username)

        return if(!userExist) {
            val newUser = User(username, password)
            val registerSuccessful = authDataSource.registerUser(newUser)
            if(registerSuccessful){
                SimpleResponse(true, "Successfully created an account")
            }else{
                SimpleResponse(false, "An unknown error occurred")
            }
        }else{
            SimpleResponse(false, "A user with that email already exist")
        }
    }
}