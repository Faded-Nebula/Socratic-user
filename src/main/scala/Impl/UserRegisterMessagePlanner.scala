package Impl

import cats.effect.IO
import io.circe.generic.auto.*
import Common.API.{PlanContext, Planner}
import Common.DBAPI.{writeDB, *}
import Common.Object.{ParameterList, SqlParameter}
import Shared.UserInfo
import Common.ServiceUtils.schemaName
import cats.effect.IO
import io.circe.generic.auto.*


case class UserRegisterMessagePlanner(
                                       userInfo: UserInfo,
                                       override val planContext: PlanContext
                                     ) extends Planner[String] {
  override def plan(using planContext: PlanContext): IO[String] = {
    // Check if the user is already registered
    val checkUserExists = readDBBoolean(
      s"SELECT EXISTS(SELECT 1 FROM ${schemaName}.user_info WHERE user_name = ?)",
      List(SqlParameter("String", userInfo.userName))
    )

    checkUserExists.flatMap { exists =>
      if (exists) {
        IO.pure("Already registered")
      } else {
        writeDB(
          s"INSERT INTO ${schemaName}.user_info (user_name, password, sur_name, last_name, institute, expertise, email) VALUES (?, ?, ?, ?, ?, ?, ?)",
          List(
            SqlParameter("String", userInfo.userName),
            SqlParameter("String", userInfo.password),
            SqlParameter("String", userInfo.surName),
            SqlParameter("String", userInfo.lastName),
            SqlParameter("String", userInfo.institute),
            SqlParameter("String", userInfo.expertise),
            SqlParameter("String", userInfo.email)
          )
        ).map(_ => "OK")
      }
    }
  }
}

