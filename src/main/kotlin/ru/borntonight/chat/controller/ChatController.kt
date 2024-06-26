package ru.borntonight.chat.controller

import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import ru.borntonight.chat.model.chat.ChatMessage
import ru.borntonight.chat.model.chat.ChatNotification
import ru.borntonight.chat.service.ChatMessageService


@Controller
@RequestMapping("api")
class ChatController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatMessageService: ChatMessageService,
) {

    @MessageMapping("/chat")
    fun processMessage(@Payload chatMessage: ChatMessage) {
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

    @GetMapping("/chat/messages/{senderId}/{recipientId}/count")
    fun countNewMessages(
        @PathVariable senderId: String,
        @PathVariable recipientId: String
    ): ResponseEntity<Long> {
        return ResponseEntity.ok(chatMessageService.countNewMessages(senderId, recipientId))
    }

    @GetMapping("/chat/messages/{senderId}/{recipientId}")
    fun findChatMessages(
        @PathVariable senderId: String,
        @PathVariable recipientId: String
    ): ResponseEntity<*> {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, recipientId))
    }

    @GetMapping("/chat/messages/{id}")
    fun findMessage(@PathVariable id: String): ResponseEntity<*> {
        return ResponseEntity.ok(chatMessageService.findById(id))
    }
}
