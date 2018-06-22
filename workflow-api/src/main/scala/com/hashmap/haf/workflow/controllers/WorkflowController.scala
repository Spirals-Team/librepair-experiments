package com.hashmap.haf.workflow.controllers
import java.util

import com.hashmap.haf.workflow.model.{SavedWorkflow, WorkflowTask}
import com.hashmap.haf.workflow.service.WorkflowService
import com.hashmap.haf.workflow.util.UUIDConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.web.bind.annotation._

import scala.collection.JavaConverters._
import scala.collection.immutable
import scala.xml.Elem

@RestController
@RequestMapping(Array("/api"))
class WorkflowController @Autowired()(private val workflowService: WorkflowService) {

  @RequestMapping(value = Array("/workflows/{workflowId}"), method = Array(RequestMethod.GET))
  @ResponseBody
  def findById(@PathVariable("workflowId") workflowId: String): String = {
     workflowService.findById(UUIDConverter.fromString(workflowId)).toXml.toString
  }

  @RequestMapping(value = Array("/workflows"), method = Array(RequestMethod.GET),
    produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody
  def findAll: util.Map[String, SavedWorkflow] = {
    workflowService.findAll.map(workflow => {
      val xml: Elem = workflow.toXml
      val id = (xml \ "@id").toString()
      val workflowTasks = (xml \ "task").map(taskXml => {
        WorkflowTask (
          (taskXml \ "@name").toString(),
          (taskXml \\ "inputCache").text,
          (taskXml \\ "outputCache").text,
          (taskXml \\ "to").map(_ \ "@task").mkString(",")
        )
      }).asJava
      id -> SavedWorkflow(id, (xml \ "@name").toString(), xml.toString(), workflowTasks)
    }).toMap.asJava
  }

  @RequestMapping(value = Array("/workflows"), method = Array(RequestMethod.PUT),
    consumes = Array("text/xml"), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody
  def saveOrUpdate(@RequestBody workflowXml: String): SavedWorkflow = {
    val workflowSaved = workflowService.saveOrUpdate(workflowXml).toXml

    val id = (workflowSaved \ "@id").toString()
    val workflowTasks = (workflowSaved \ "task").map(taskXml => {
      WorkflowTask (
        (taskXml \ "@name").toString(),
        (taskXml \\ "inputCache").text,
        (taskXml \\ "outputCache").text,
        (taskXml \\ "to").map(_ \ "@task").mkString(",")
      )
    }).asJava

    SavedWorkflow(id, (workflowSaved \ "@name").toString(),
      workflowSaved.toString(), workflowTasks)

  }

  @RequestMapping(value = Array("/workflows/{workflowId}"), method = Array(RequestMethod.DELETE))
  @ResponseStatus(value = HttpStatus.OK)
  def delete(@PathVariable("workflowId") workflowId: String): Unit = {
    workflowService.delete(UUIDConverter.fromString(workflowId))
  }
}
