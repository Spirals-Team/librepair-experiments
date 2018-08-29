package com.sikza.socksapi.repositotries

import com.sikza.socksapi.models.Message
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IMessagesRepository : MongoRepository<Message, String> {
    fun findTop15ByTo(emailAddress: String): Collection<Message>
    fun findByFromAndTo(from: String, to: String): MutableList<Message>
}
