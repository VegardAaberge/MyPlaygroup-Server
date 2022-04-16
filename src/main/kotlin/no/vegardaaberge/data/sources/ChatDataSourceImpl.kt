package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase

class ChatDataSourceImpl(
    private val db: CoroutineDatabase
) : ChatDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return messages
            .find()
            .descendingSort(Message::timestamp)
            .toList()
    }

    override suspend fun insertMessage(message: Message) : Boolean {
        return messages
            .insertOne(message)
            .wasAcknowledged()
    }
}