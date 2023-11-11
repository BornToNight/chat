package ru.borntonight.chat.model.chat

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class ChatRoom(
    @Id
    val id: String = UUID.randomUUID().toString(),
    val chatId: String = "",
    val senderId: String = "",
    val recipientId: String = "",
)
