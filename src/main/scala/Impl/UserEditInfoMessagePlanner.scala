package Impl

import cats.effect.IO
import io.circe.generic.auto.*
import Common.API.{PlanContext, Planner}
import Common.DBAPI.{readDBBoolean, writeDB, *}
import Common.Object.{ParameterList, SqlParameter}
import Common.ServiceUtils.schemaName
import cats.effect.IO
import io.circe.Json
import Shared.UserInfo
import io.circe.generic.auto.*

case class UserEditInfoMessagePlanner(userName: String, property: String, updateValue:String, override val planContext: PlanContext) extends Planner[String]:
  override def plan(using PlanContext): IO[String] = {
    writeDB(
      s"UPDATE ${schemaName}.user_info SET ${property} = ? WHERE user_name = ?",
      List(
        SqlParameter("String", updateValue),
        SqlParameter("String", userName)
      )
    ).map(_ => "OK")
  }
