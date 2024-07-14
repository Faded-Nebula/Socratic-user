package Process

import Common.API.{API, PlanContext, TraceID}
import Global.{ServerConfig, ServiceCenter}
import Common.DBAPI.{initSchema, writeDB}
import Common.Object.SqlParameter
import Common.ServiceUtils.schemaName
import cats.effect.IO
import io.circe.generic.auto.*
import org.http4s.client.Client
import scala.io.Source

import java.util.UUID

def readFileToString(filePath: String): String = {
  val source = Source.fromFile(filePath)
  try {
    source.mkString
  } finally {
    source.close()
  }
}

object Init {
  def init(config:ServerConfig):IO[Unit]=
    val defaultImage = readFileToString("/Shared/default_profile.txt")
    given PlanContext=PlanContext(traceID = TraceID(UUID.randomUUID().toString),0)
    for{
      _ <- API.init(config.maximumClientConnection)
      _ <- initSchema(schemaName)
      _ <- writeDB(s"CREATE TABLE IF NOT EXISTS ${schemaName}.user_info (user_name TEXT, password TEXT, sur_name TEXT, last_name TEXT, institute TEXT, expertise TEXT, email TEXT)", List())
      _ <- writeDB(s"CREATE TABLE IF NOT EXISTS ${schemaName}.user_task (user_name TEXT, task_name TEXT)", List())
      _ <- writeDB(s"CREATE TABLE IF NOT EXISTS ${schemaName}.user_photo (user_name TEXT, profile_photo TEXT)", List())
      _ <- writeDB(s"INSERT INTO ${schemaName}.user_photo (user_name, profile_photo) VALUES (?, ?)",
        List(SqlParameter("String", "default"), SqlParameter("String", defaultImage)
        ))
    } yield ()
}
