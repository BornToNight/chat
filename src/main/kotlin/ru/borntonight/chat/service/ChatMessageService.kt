package ru.borntonight.chat.service

import ru.borntonight.chat.model.chat.ChatMessage
import ru.borntonight.chat.util.MessageStatus

interface ChatMessageService {

    fun save(chatMessage: ChatMessage): ChatMessage

    fun countNewMessages(senderId: String, recipientId: String): Long

    fun findChatMessages(senderId: String, recipientId: String): List<ChatMessage>

    fun findById(id: String): ChatMessage

    fun updateStatuses(senderId: String, recipientId: String, messageStatus: MessageStatus)
}
