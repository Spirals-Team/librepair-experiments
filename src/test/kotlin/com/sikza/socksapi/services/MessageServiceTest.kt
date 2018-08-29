package com.sikza.socksapi.services

import com.nhaarman.mockito_kotlin.*
import com.sikza.socksapi.models.Message
import com.sikza.socksapi.repositotries.IMessagesRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.messaging.simp.SimpMessageSendingOperations

internal class MessageServiceTest {

    private lateinit var messageService: IMessageService
    private lateinit var messageRepository: IMessagesRepository
    private lateinit var messageSendingTemplate: SimpMessageSendingOperations

    @BeforeEach
    fun setUp() {
        messageRepository = mock()
        messageSendingTemplate = mock()
    }

    @ParameterizedTest
    @CsvSource(value = ["'','','',''", "A,'',C,D", "A,B,'',''", "A,B,'C',''"])
    fun route_givenEmptyMessage_shouldThrowIllegalArgumentException(from: String, to: String, replyTo: String, body: String) {
        //Arrange
        messageService = MessageService(messageRepository, messageSendingTemplate)
        var message = Message(from = from, to = to, replyTo = replyTo, body = body)

        //Act
        var exception = assertThrows<IllegalArgumentException> { messageService.route(message) }

        //Assert
        assertThat(exception.message).contains("cannot be empty")
    }

    @Test
    fun route_givenValidMessage_shouldSaveMessage() {
        //Arrange
        messageService = MessageService(messageRepository, messageSendingTemplate)
        var message = Message(from = "Bob", to = "bob@gmail.com", replyTo = "simba@gmail.com", body = "Hi Lizo")

        //Act
        messageService.route(message)

        //Assert
        verify(messageRepository, times(1)).save(message)
    }

    @Test
    fun route_givenValidMessage_shouldPublishMessageOnSockets() {
        //Arrange
        messageService = MessageService(messageRepository, messageSendingTemplate)

        var message = Message(from = "Martin", to = "Marley@gmail.com", replyTo = "sikza@gmail.com", body = "Hi Lizo")

        //Act
        messageService.route(message)

        //Assert
        verify(messageSendingTemplate, times(1)).convertAndSend(message.to, message)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "\n", "\t"])
    fun getRecentMessages_givenEmptyEmailAddress_shouldThrowIllegalArgumentException(emailAddress: String) {
        //Arrange
        messageService = MessageService(messageRepository, messageSendingTemplate)

        //Act
        var exception = assertThrows<IllegalArgumentException> { messageService.getRecentMessage(emailAddress) }

        //Assert
        assertThat(exception.message).contains("cannot be empty")
    }

    @Test
    fun getRecentMessages_givenEmailAddressHasNoRecords_shouldReturnEmptyCollection() {
        //Arrange
        messageService = MessageService(messageRepository, messageSendingTemplate)

        //Act
        var emailAddress = "Bob@mail.com"
        var result = messageService.getRecentMessage(emailAddress)

        //Assert
        assertThat(result).isEmpty()
        verify(messageRepository, times(1)).findTop15ByTo(emailAddress)
    }

    @Test
    fun getRecentMessages_givenEmailAddress_shouldReturnMatchingMessages() {
        //Arrange
        var messages = arrayListOf<Message>(
                Message("A", "a@a.com", "Hi", "B"),
                Message("B", "b@b", "Hi", "A"))

        doReturn(messages).`when`(messageRepository).findTop15ByTo(any())
        messageService = MessageService(messageRepository, messageSendingTemplate)

        //Act
        var emailAddress = "Bob@mail.com"
        var result = messageService.getRecentMessage(emailAddress)

        //Assert
        assertThat(result).isEqualTo(messages)
        verify(messageRepository, times(1)).findTop15ByTo(emailAddress)
    }

    @ParameterizedTest
    @CsvSource(value = ["'',''", "'a@b.com',''", "'','b@b.com'"])
    fun getConversation_givenEmptyInput_shouldThrowIllegalArgumentException(from: String, to: String) {
        //Arrange
        messageService = MessageService(messageRepository, messageSendingTemplate)

        //Act
        var exception = assertThrows<IllegalArgumentException> { messageService.getConversation(from, to) }

        //Assert
        assertThat(exception.message).contains("cannot be empty")
    }

    @Test
    fun getConversation_givenValidInput_shouldReturnConversation() {
        //Arrange
        var messages = arrayListOf<Message>(
                Message("A", "user1@mail.com", "Hi", "B"),
                Message("A", "user1@mail.com", "Hi", "B"),
                Message("B", "user2@mail.com", "Hello", "A"),
                Message("B", "user2@mail.com", "Hello", "A"))

        doReturn(messages).`when`(messageRepository).findByFromAndTo(any(), any())
        messageService = MessageService(messageRepository, messageSendingTemplate)

        //Act
        var result = messageService.getConversation("A", "B")

        //Assert
        assertThat(result).isEqualTo(messages)
        verify(messageRepository, times(1)).findByFromAndTo("A", "B")
    }

}
