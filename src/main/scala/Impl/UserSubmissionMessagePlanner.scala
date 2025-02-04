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
import APIs.EditorAPI.AllocateReviewerMessage
import cats.effect.IO
import io.circe.generic.auto.*

import java.io.InputStream


case class UserSubmissionMessagePlanner(userName: String, taskName: String, periodicalName: String, pdfBase64: String, researchArea:String, Abstract:String, TLDR:String, keyword:String, override val planContext: PlanContext) extends Planner[String]:
  override def plan(using PlanContext): IO[String] = {
    val checkTaskConflict = TaskQueryMessage(userName, taskName, periodicalName, pdfBase64, researchArea, Abstract, TLDR, keyword).send
    checkTaskConflict.flatMap { conflict =>
      if (conflict == "Conflict") {
        IO.pure("Task Name Conflict")
      } else {
        AllocateReviewerMessage(taskName, periodicalName).send
      }.map(_ => "OK")
    }
  }
