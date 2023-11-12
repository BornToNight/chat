package ru.borntonight.chat.repository.chat

import org.springframework.data.mongodb.repository.MongoRepository
import ru.borntonight.chat.model.chat.ChatMessage
import ru.borntonight.chat.util.MessageStatus

interface ChatMessageRepository : MongoRepository<ChatMessage, String> {

    fun countBySenderIdAndRecipientIdAndStatus(
        senderId: String,
        recipientId: String,
        messageStatus: MessageStatus
    ): Long

    fun findBySenderIdAndRecipientId(
        senderId: String,
        recipientId: String,
    ): List<ChatMessage>

    fun findByRecipientIdAndSenderId(
        recipientId: String,
        senderId: String,
    ): List<ChatMessage>

}
