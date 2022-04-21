package no.vegardaaberge.data.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse (
    val successful: Boolean,
    val message: String,
    val createProfile: Boolean = false
)