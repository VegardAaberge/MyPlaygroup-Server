package no.vegardaaberge.data.sources

import no.vegardaaberge.data.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ChatDataSourceImpl(
    private val db: CoroutineDatabase
) : ChatDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(username: String): List<Message> {
        return messages
            .find(Message::username eq username)
            .descendingSort(Message::timestamp)
            .toList()
    }

    override suspend fun insertMessage(message: Message) : Boolean {
        return messages
            .insertOne(message)
            .wasAcknowledged()
    }
}