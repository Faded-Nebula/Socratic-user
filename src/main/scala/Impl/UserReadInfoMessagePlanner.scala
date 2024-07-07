package Impl

import cats.effect.IO
import io.circe.generic.auto.*
import Common.API.{PlanContext, Planner}
import Common.DBAPI.{writeDB, *}
import Common.Object.{ParameterList, SqlParameter}
import Common.ServiceUtils.schemaName
import cats.effect.IO
import io.circe.Json
import Shared.UserInfo
import io.circe.generic.auto.*


case class UserReadInfoMessagePlanner(property: String,override val planContext: PlanContext) extends Planner[String]:
  override def plan(using PlanContext): IO[String] = {
    // Search the User Info on the db
    readDBString(
      s"SELECT ${property} FROM ${schemaName}.user_info WHERE user_name = ?",
      List(SqlParameter("String", property))
    )
  }
