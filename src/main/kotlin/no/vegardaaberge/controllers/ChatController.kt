package no.vegardaaberge.controllers

import no.vegardaaberge.data.model.Message
import no.vegardaaberge.data.responses.SimpleResponse
import no.vegardaaberge.data.sources.ChatDataSource

class ChatController(
    private val chatDataSource: ChatDataSource
) {
    suspend fun sendMessage(
        username: String,
        message: String,
        receivers: List<String>
    ) : SimpleResponse
    {
        val messageEntity = Message(
            text = message,
            username = username,
            receivers = receivers,
            timestamp = System.currentTimeMillis()
        )

        val isSuccessful = chatDataSource.insertMessage(messageEntity)

        return SimpleResponse(
            successful = isSuccessful,
            message = if(isSuccessful) "Message has been sent" else "Failed to send message"
        )
    }

    suspend fun getAllMessages(username: String): List<Message> {
        return chatDataSource.getAllMessages(username)
    }
}