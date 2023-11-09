package ru.borntonight.chat.repository.chat

import org.springframework.data.mongodb.repository.MongoRepository
import ru.borntonight.chat.model.chat.ChatRoom


interface ChatRoomRepository : MongoRepository<ChatRoom, String> {

    fun findBySenderIdAndRecipientId(
        senderId: String,
        recipientId: String,
    ): ChatRoom


}