package no.vegardaaberge.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageRequest (
    val username: String,
    val message: String
)
