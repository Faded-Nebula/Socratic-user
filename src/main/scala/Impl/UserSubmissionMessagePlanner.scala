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


case class UserSubmissionMessagePlanner(userName: String, taskName: String, override val planContext: PlanContext) extends Planner[String]:
  override def plan(using PlanContext): IO[String] = {
    /** step 1: 故意写入数据库 */
    startTransaction{
      for {
        _ <- writeDB(s"INSERT INTO ${schemaName}.user_task (user_name, task_name) VALUES (?, ?)",
          List(SqlParameter("String", userName), SqlParameter("String", taskName))
        )
        a <- startTransaction {
          TaskQueryMessage(userName, taskName).send  //发个消息出去
          //        >>rollback() //考虑可以回滚，这里可以注释掉看看效果
        }
      } yield "OK"
    }
  }
