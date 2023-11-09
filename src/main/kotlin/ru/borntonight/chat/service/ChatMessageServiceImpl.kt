package ru.borntonight.chat.service

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import ru.borntonight.chat.model.chat.ChatMessage
import ru.borntonight.chat.repository.chat.ChatMessageRepository
import ru.borntonight.chat.util.MessageStatus
import ru.borntonight.chat.util.MessageStatus.DELIVERED
import ru.borntonight.chat.util.MessageStatus.RECEIVED

@Service
class ChatMessageServiceImpl(
    private val chatMessageRepository: ChatMessageRepository,
    private val chatRoomService: ChatRoomService,
    private val mongoOperations: MongoOperations
) : ChatMessageService {

    override fun save(chatMessage: ChatMessage): ChatMessage {
        chatMessage.status = RECEIVED
        chatMessageRepository.save(chatMessage)
        return chatMessage
    }

    override fun countNewMessages(senderId: String, recipientId: String): Long {
        return chatMessageRepository.countBySenderIdAndRecipientIdAndStatus(
            senderId, recipientId, RECEIVED
        )
    }

    override fun findChatMessages(senderId: String, recipientId: String): List<ChatMessage> {
        val chatId = chatRoomService.getChatId(senderId, recipientId, false)
        val chatMessageList = chatMessageRepository.findByChatId(chatId)
        if (chatMessageList.isNotEmpty()) {
            updateStatuses(senderId, recipientId, DELIVERED)
        }
        return chatMessageList
    }

    override fun findById(id: String): ChatMessage {
        return chatMessageRepository.findById(id).map { chatMessage ->
            chatMessage.status = DELIVERED
            chatMessageRepository.save(chatMessage)
        }.orElseThrow { ResourceNotFoundException("can't find message ($id)") }
    }

    override fun updateStatuses(senderId: String, recipientId: String, status: MessageStatus) {
        val query = Query(
            Criteria
                .where("senderId").`is`(senderId)
                .and("recipientId").`is`(recipientId)
        )
        val update = Update.update("status", status)
        mongoOperations.updateMulti(query, update, ChatMessage::class.java)
    }
}