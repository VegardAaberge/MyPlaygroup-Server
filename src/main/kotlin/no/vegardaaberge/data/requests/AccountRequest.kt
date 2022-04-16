package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class AccountRequest (
    val username: String,
    val password: String
)