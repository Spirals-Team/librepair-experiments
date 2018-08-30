package com.sikza.socksapi

import com.sikza.socksapi.repositotries.IMessagesRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = [IMessagesRepository::class])
class SocksApiApplication

fun main(args: Array<String>) {
    runApplication<SocksApiApplication>(*args)
}


