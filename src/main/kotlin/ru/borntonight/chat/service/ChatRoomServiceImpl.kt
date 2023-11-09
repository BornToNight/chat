package ru.borntonight.chat.service

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import ru.borntonight.chat.model.chat.ChatRoom
import ru.borntonight.chat.repository.chat.ChatRoomRepository

@Service
class ChatRoomServiceImpl(
    private val chatRoomRepository: ChatRoomRepository,
) : ChatRoomService {
    override fun getChatId(
        senderId: String, recipientId: String, createIfNotExist: Boolean,
    ): String {

        return try {
            chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId).chatId
        } catch (e: EmptyResultDataAccessException) {
            if (createIfNotExist) {
                val chatId = String.format("%s_%s", senderId, recipientId)
                chatRoomRepository.save(
                    ChatRoom(
                        chatId = chatId,
                        senderId = senderId,
                        recipientId = recipientId,
                    )
                )
                chatRoomRepository.save(
                    ChatRoom(
                        chatId = chatId,
                        senderId = recipientId,
                        recipientId = senderId,
                    )
                )
                chatId
            } else ""
        }


    }
}