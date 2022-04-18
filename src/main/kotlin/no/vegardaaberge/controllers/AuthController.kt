package no.vegardaaberge.controllers

import no.vegardaaberge.data.model.Reset
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

    suspend fun tryRegister(username: String, password: String, email: String) : SimpleResponse{

        val userExist = authDataSource.checkIfUserExist(username)

        return if(!userExist) {
            val newUser = User(
                username = username,
                password = password,
                email = email
            )
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
    suspend fun tryRequestEmail(email: String): SimpleResponse {

        val userEntity = authDataSource.getUsernameFromEmail(email)

        userEntity?.let { user ->

            val resetRequest = Reset(
                userID = user.id,
                username =  user.username,
                code = (11111..99999).shuffled().last().toString(),
                request_time = System.currentTimeMillis()
            )
            val isInserted = authDataSource.insertResetRequest(resetRequest)

            // TODO Send email

            return if(isInserted){
                return SimpleResponse(true, resetRequest.code)
            }else{
                return SimpleResponse(false, "An unknown error occurred")
            }
        }

        return SimpleResponse(false, "Email does not exist")
    }

    suspend fun tryVerifyCode(code: String): SimpleResponse {

        val isCodeValid = authDataSource.verifyResetRequestCode(code)

        return if(isCodeValid){
            SimpleResponse(true, "Typed in the correct code")
        }else{
            SimpleResponse(false, "Typed in the wrong code")
        }
    }
}