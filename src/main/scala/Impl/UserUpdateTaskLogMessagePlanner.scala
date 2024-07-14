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

case class UserUpdateTaskLogMessagePlanner(userName: String, taskName: String)
  
