package Impl

import cats.effect.IO
import io.circe.generic.auto.*
import Common.API.{PlanContext, Planner}
import Common.DBAPI.{writeDB, *}
import Common.Object.{ParameterList, SqlParameter}
import APIs.UserManagementAPI.RegisterMessage
import Shared.UserInfo
import Common.ServiceUtils.schemaName
import cats.effect.IO
import io.circe.generic.auto.*
import APIs.UserManagementAPI.CheckUserExistsMessage


case class UserRegisterMessagePlanner(
                                       userInfo: UserInfo, password: String,
                                       override val planContext: PlanContext
                                     ) extends Planner[String] {
  override def plan(using planContext: PlanContext): IO[String] = {
    // Check if the user is already registered
    val checkUserExists = CheckUserExistsMessage(userInfo.userName).send

    checkUserExists.flatMap { exists =>
      if (exists) {
        IO.pure("Already registered")
      } else {
        for {
          _ <- RegisterMessage(userInfo.userName, password, "user").send
          _ <- writeDB(
            s"INSERT INTO ${schemaName}.user_info (user_name, sur_name, last_name, institute, expertise, email) VALUES (?, ?, ?, ?, ?, ?)",
            List(
              SqlParameter("String", userInfo.userName),
              SqlParameter("String", userInfo.surName),
              SqlParameter("String", userInfo.lastName),
              SqlParameter("String", userInfo.institute),
              SqlParameter("String", userInfo.expertise),
              SqlParameter("String", userInfo.email)
            )
          )
        } yield "OK"
      }
    }
  }
}

