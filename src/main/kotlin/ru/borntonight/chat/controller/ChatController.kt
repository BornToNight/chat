package ru.borntonight.chat.controller

import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.borntonight.chat.model.chat.ChatMessage
import ru.borntonight.chat.model.chat.ChatNotification
import ru.borntonight.chat.service.ChatMessageService
import ru.borntonight.chat.service.ChatRoomService


@Controller
class ChatController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatMessageService: ChatMessageService,
    private val chatRoomService: ChatRoomService,
) {

    @MessageMapping("/chat")
    fun processMessage(@Payload chatMessage: ChatMessage) {
        val chatId = chatRoomService.getChatId(chatMessage.senderId, chatMessage.recipientId, true)
        chatMessage.chatId = chatId
        val saved: ChatMessage = chatMessageService.save(chatMessage)
        messagingTemplate.convertAndSendToUser(
            chatMessage.recipientId, "/queue/messages",
            ChatNotification(
                saved.id,
                saved.senderId,
                saved.senderName,
            )
        )
    }

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    fun countNewMessages(
        @PathVariable senderId: String,
        @PathVariable recipientId: String
    ): ResponseEntity<Long> {
        return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId))
    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    fun findChatMessages(
        @PathVariable senderId: String,
        @PathVariable recipientId: String
    ): ResponseEntity<*> {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId))
    }

    @GetMapping("/messages/{id}")
    fun findMessage(@PathVariable id: String): ResponseEntity<*> {
        return ResponseEntity.ok(chatMessageService.findById(id))
    }
}
