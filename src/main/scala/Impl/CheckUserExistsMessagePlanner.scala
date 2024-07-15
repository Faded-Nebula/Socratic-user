package Impl

import cats.effect.IO
import io.circe.Json
import io.circe.generic.auto.*
import io.circe.parser.decode
import Common.API.{PlanContext, Planner}
import Common.DBAPI.{writeDB, *}
import Common.Object.{ParameterList, SqlParameter}
import Common.ServiceUtils.schemaName
import cats.effect.IO
import io.circe.generic.auto.*

case class CheckUserExistsMessagePlanner(userName: String, override val planContext: PlanContext) extends Planner[Boolean]:
  override def plan(using PlanContext): IO[Boolean] = {
    // Search the User Info on the db
    readDBBoolean(
      s"SELECT EXISTS(SELECT 1 FROM ${schemaName}.user_info WHERE user_name = ?)",
      List(SqlParameter("String", userName))
    )
  }
