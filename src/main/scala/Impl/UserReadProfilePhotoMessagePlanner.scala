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

case class UserReadProfilePhotoMessagePlanner(userName: String, override val planContext: PlanContext) extends Planner[String]:
  override def plan(using PlanContext): IO[String] = {
    val checkPhotoExists = readDBBoolean(
      s"SELECT EXISTS(SELECT 1 FROM ${schemaName}.user_photo WHERE user_name = ?)",
      List(SqlParameter("String", userName))
    )
    checkPhotoExists.flatMap { exists =>
      if (exists) {
        // Search the User profile photo on the db
        readDBString(
          s"SELECT profile_photo FROM ${schemaName}.user_photo WHERE user_name = ?",
          List(SqlParameter("String", userName))
        )
      } else {
        IO.pure("Not Initialized")
      }
    }
  }