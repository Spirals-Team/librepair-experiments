package com.hashmap.haf.workflow


import com.hashmap.haf.workflow.install.WorkflowInstallationService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAutoConfiguration
@ComponentScan
@EnableOAuth2Client
class WorkflowApiApplication

object WorkflowApiApplication extends App{
  private val SPRING_CONFIG_NAME_KEY = "--spring.config.name"
  private val DEFAULT_SPRING_CONFIG_PARAM = SPRING_CONFIG_NAME_KEY + "=" + "workflow-api"

  val context = SpringApplication.run(classOf[WorkflowApiApplication], updateArguments(args): _*)
  context.getBean(classOf[WorkflowInstallationService]).performInstall()


  private def updateArguments(args: Array[String]): List[String] ={
    val argsAsList = args.toList
    argsAsList.find(_.startsWith(SPRING_CONFIG_NAME_KEY)) match {
      case Some(_) => argsAsList
      case _ => DEFAULT_SPRING_CONFIG_PARAM :: argsAsList
    }
  }
}
