package com.sikza.socksapi.controllers

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.sikza.socksapi.models.Message
import com.sikza.socksapi.repositotries.IMessagesRepository
import com.sikza.socksapi.services.IMessageService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class MessageControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var messageRepository: IMessagesRepository

    @SpyBean
    private lateinit var messageService: IMessageService

    @Test
    fun whenSendingMessage_givenValidMessageDetails_shouldSaveAndBroadcast() {
        //when
        var response = restTemplate.postForEntity<Any>("/api/message", Message(
                "User User1",
                "tester@gmail.com",
                "Hello tester",
                "dev@gmail.com"))
        //then
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        verify(messageService, times(1)).route(any())
    }


    @Test
    fun whenGettingRecentMessages_andHasMoreThan15Messages_shouldReturnTheLast15Messages() {
        //given
        var testMessages = createTestMessages(20)
        testMessages.addAll(createTestMessages(10))
        messageRepository.saveAll(testMessages)

        //when
        var result = restTemplate.postForEntity("/api/messages/recent", testMessages[0].to, ArrayList::class.java)

        //then
        assertThat(result.body!!.size).isEqualTo(15)
    }


    @Test
    fun whenGettingRecentMessages_andHasLessThan15Messages_shouldReturnOnlyThose() {
        //given
        var testMessage = createTestMessages(7)
        messageRepository.saveAll(testMessage)

        //when
        var result = restTemplate.postForEntity("/api/messages/recent", testMessage[0].to, ArrayList::class.java)

        //then
        assertThat(result.body!!.size).isEqualTo(7)
    }

    @ParameterizedTest
    @ValueSource(strings = ["[a@mail.com, b@mail.com]", "['a@mail.com', 'b@mail.com']"])
    fun whenGettingConversation_GivenThereHasNeverBeenAny_ShouldReturnEmptyCollection(emailJsonList: String) {
        //given

        //when
        var result = restTemplate.postForEntity(
                "/api/conversation",
                emailJsonList,
                MutableList::class.java)

        //then
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        verify(messageService, times(1)).getConversation(any(), any())
    }

    @ParameterizedTest
    @ValueSource(strings = ["\t", " ", "\n"]) //Empty body [""] to be handled by spring
    fun whenGettingConversation_givenNoEmails_shouldThrowIllegalArgumentException(emailJsonList: String) {
        //when
        var result: ResponseEntity<IllegalArgumentException> = restTemplate.postForEntity(
                "/api/conversation",
                emailJsonList,
                IllegalArgumentException::class.java)

        //then
        assertThat(result.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        assertThat(result.body!!.message).contains("Request body cannot be empty")
        verify(messageService, never()).getConversation(any(), any())
    }

    @ParameterizedTest
    @ValueSource(strings = ["[a@mail.com]", "['a@mail.com', 'b@mail.com','b@mail.com']"])
    fun whenGettingConversation_EmailListNotEqualToTwo_shouldThrowIllegalArgumentException(emailJsonList: String) {
        //when
        var result: ResponseEntity<IllegalArgumentException> = restTemplate.postForEntity(
                "/api/conversation",
                emailJsonList,
                IllegalArgumentException::class.java)

        //then
        assertThat(result.statusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
        assertThat(result.body!!.message).contains("One or more than two emails are not supported")
        verify(messageService, never()).getConversation(any(), any())
    }

    private fun createTestMessages(number: Int): ArrayList<Message> {
        var epoch = Instant.now().epochSecond
        var messages = ArrayList<Message>()
        var counter = number
        do {
            messages.add(Message(from = "source_$epoch",
                    replyTo = "$epoch@mail.com",
                    body = "Hello $epoch",
                    to = "destination_$epoch"))
            counter--
        } while (counter > 0)
        return messages
    }
}