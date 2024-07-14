package Impl

import Common.API.{PlanContext, Planner}
import Common.DBAPI.{startTransaction, writeDB}
import cats.effect.IO
import io.circe.generic.auto.*
import Common.API.{PlanContext, Planner}
import Common.DBAPI.{writeDB, *}
import Common.Object.{ParameterList, SqlParameter}
import Common.ServiceUtils.schemaName
import APIs.TaskAPI.TaskQueryMessage
import cats.effect.IO
import io.circe.generic.auto.*

import java.io.InputStream


case class UserSubmissionMessagePlanner(userName: String, taskName: String, periodicalName: String, pdfBase64: String, researchArea:String, Abstract:String, TLDR:String, override val planContext: PlanContext) extends Planner[String]:
  override def plan(using PlanContext): IO[String] = {
    val checkTaskConflict = TaskQueryMessage(userName, taskName, periodicalName, pdfBase64).send
    checkTaskConflict.flatMap { conflict =>
      if (conflict == "Conflict") {
        IO.pure("Task Name Conflict")
      } else {
        writeDB(s"INSERT INTO ${schemaName}.user_task (user_name, task_name) VALUES (?, ?)",
          List(SqlParameter("String", userName), SqlParameter("String", taskName)
          ))
      }.map(_ => "OK")
    }
  }
