package ru.borntonight.chat.model.chat

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ChatNotification(
    @Id
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
)
