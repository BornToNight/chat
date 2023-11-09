package ru.borntonight.chat.model.chat

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.borntonight.chat.util.MessageStatus
import java.util.*

@Document
data class ChatMessage(
    @Id
    var id: String = "",
    var chatId: String = "",
    var senderId: String = "",
    var recipientId: String = "",
    var senderName: String = "",
    var recipientName: String = "",
    var content: String = "",
    var timestamp: Date,
    var status: MessageStatus,
)
