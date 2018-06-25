package com.hashmap.haf.workflow.factory

import java.util.UUID

import com.github.dexecutor.core.task.Task
import com.hashmap.haf.workflow.constants.XmlConstants
import com.hashmap.haf.workflow.task.{LivyTask, SparkIgniteTask}

import scala.language.higherKinds
import scala.xml.Node

object Factory{
	import XmlConstants._

	trait WorkflowTask[T <: Comparable[T], R] extends Task[T, R]

	trait TaskFactory[T <: Comparable[T], R] {
		def create(xml: Node): WorkflowTask[T, R]
	}

	object TaskFactory {
		def apply[T <: Comparable[T], R](xml: Node)(implicit ev: TaskFactory[T, R]): Task[T, R] = ev.create(xml)
	}

	implicit object EntityTaskFactory extends TaskFactory[UUID, String] {
		def create(xml: Node): WorkflowTask[UUID, String] = {
			(xml \ "_").headOption.map(_.label) match {
				case Some(LIVY_TASK) => LivyTask(xml)
				case Some(SPARK_TASK) => SparkIgniteTask(xml)
				case None => throw new IllegalStateException("At least one tak should be defined in a workflow")
				case _ => throw new IllegalArgumentException("No factory method found for given task")
			}
		}
	}

}