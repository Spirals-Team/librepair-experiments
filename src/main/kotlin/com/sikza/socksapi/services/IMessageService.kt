package com.sikza.socksapi.services

import com.sikza.socksapi.models.Message

interface IMessageService {
    fun route(message: Message)
    fun getRecentMessage(emailAddress: String): Collection<Message>
    fun getConversation(from: String, to: String): MutableList<Message>
}
