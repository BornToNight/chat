package ru.borntonight.chat.service

interface ChatRoomService {

    fun getChatId(senderId: String, recipientId: String, createIfNotExist: Boolean): String

}