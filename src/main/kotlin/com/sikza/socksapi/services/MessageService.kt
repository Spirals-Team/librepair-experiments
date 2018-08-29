package com.sikza.socksapi.services

import com.sikza.socksapi.models.Message
import com.sikza.socksapi.repositotries.IMessagesRepository
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class MessageService(
        private val messageRepository: IMessagesRepository,
        private val messageSendingTemplate: SimpMessageSendingOperations
) : IMessageService {

    override fun getConversation(from: String, to: String): MutableList<Message> {
        Assert.hasLength(from, "target source cannot be empty")
        Assert.hasLength(to, "target destination cannot be empty")
        return messageRepository.findByFromAndTo(from, to)
    }

    override fun getRecentMessage(emailAddress: String): Collection<Message> {
        Assert.hasLength(emailAddress.trim(), "target email cannot be empty")
        return this.messageRepository.findTop15ByTo(emailAddress)
    }

    override fun route(message: Message) {
        validateMessage(message)
        this.messageRepository.save(message)
        println("/topic/"+message.to.toString())
        this.messageSendingTemplate.convertAndSend("/topic/"+message.to.toString(), message)
    }

    private fun validateMessage(message: Message) {
        Assert.hasLength(message.from, "message source cannot be empty")
        Assert.hasLength(message.to, "message destination cannot be empty")
        Assert.hasLength(message.replyTo, "'replyTo' cannot be empty")
        Assert.hasLength(message.body, "message body cannot be empty")
    }
}
