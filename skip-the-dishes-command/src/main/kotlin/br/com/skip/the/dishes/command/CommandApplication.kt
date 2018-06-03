package br.com.skip.the.dishes.command

import org.slf4j.LoggerFactory
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.net.InetAddress

private val logger = LoggerFactory.getLogger("br.com.skip.the.dishes.query.QueryApplication")

fun main(args: Array<String>) {
    runApplication<CommandConfig>(*args)
            .also { logger.info(buildLogApplication(it)) }
}

private fun buildLogApplication(app: ConfigurableApplicationContext) = buildString {
    val port = app.environment.getProperty("server.port")
    val contextPath = app.environment.getProperty("server.contextPath") ?: "/"

    appendln()
    appendln("----------------------------------------------------------")
    appendln("Application '${app.environment.getProperty("spring.application.name")}' is running! Access URLs:")
    appendln("Local: \t\thttp://127.0.0.1:$port$contextPath")
    appendln("External: \thttp://${InetAddress.getLocalHost().hostAddress}:$port$contextPath")
    appendln("Version: \t${app.environment.getProperty("server.version")}")
    appendln("Profile(s): ${app.environment.activeProfiles.joinToString()}")
    appendln("----------------------------------------------------------")
}
