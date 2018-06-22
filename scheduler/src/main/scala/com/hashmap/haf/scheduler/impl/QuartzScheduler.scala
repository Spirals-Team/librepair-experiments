package com.hashmap.haf.scheduler.impl

import akka.actor.{ActorRef, ActorSystem}
import com.hashmap.haf.scheduler.api.Scheduler
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.util.Try

@Service
case class QuartzScheduler @Autowired()(scheduler: QuartzSchedulerExtension) extends Scheduler {

  override def createJob(_name: String, _cronExpression: String) =
    Try(scheduler.createSchedule(name = _name, cronExpression = _cronExpression)).isSuccess

  override def submitJob(_name: String, _subscriberActor: ActorRef, msg: AnyRef) =
    Try(scheduler.schedule(_name, _subscriberActor, msg)).isSuccess

  override def updateJob(_name: String, _subscriberActor: ActorRef, _cronExpression: String, msg: AnyRef) =
    Try(scheduler.rescheduleJob(_name, _subscriberActor, msg,  cronExpression = _cronExpression)).isSuccess

  override def suspendJob(_name: String) = Try(scheduler.suspendJob(_name)).isSuccess

  override def resumeJob(_name: String) = Try(scheduler.resumeJob(_name)).isSuccess

  override def SuspendAll = Try(scheduler.suspendAll()).isSuccess

  override def cancelJob(_name: String) = Try(scheduler.cancelJob(_name)).isSuccess
}
