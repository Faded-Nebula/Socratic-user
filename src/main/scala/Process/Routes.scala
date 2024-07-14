package Process

import Common.API.PlanContext
import Impl.*
import cats.effect.*
import io.circe.generic.auto.*
import io.circe.parser.decode
import io.circe.syntax.*
import org.http4s.*
import org.http4s.client.Client
import org.http4s.dsl.io.*


object Routes:
  private def executePlan(messageType:String, str: String): IO[String]=
    messageType match {
      case "UserLoginMessage" =>
        IO(decode[UserLoginMessagePlanner](str).getOrElse(throw new Exception("Invalid JSON for UserLoginMessage")))
          .flatMap{m=>
            m.fullPlan.map(_.asJson.toString)
          }
      case "UserRegisterMessage" =>
        IO(decode[UserRegisterMessagePlanner](str).getOrElse(throw new Exception("Invalid JSON for UserRegisterMessage")))
          .flatMap{m=>
            m.fullPlan.map(_.asJson.toString)
          }
      case "UserReadInfoMessage" =>
        IO(decode[UserReadInfoMessagePlanner](str).getOrElse(throw new Exception("Invalid JSON for UserReadInfoMessage")))
          .flatMap{m=>
            m.fullPlan.map(_.asJson.toString)
          }
      case "UserSubmissionMessage" =>
        IO(decode[UserSubmissionMessagePlanner](str).getOrElse(throw new Exception("Invalid JSON for UserSubmissionMessage")))
          .flatMap{m=>
            m.fullPlan.map(_.asJson.toString)
          }
      case "UserEditProfilePhotoMessage" =>
        IO(decode[UserEditProfilePhotoMessagePlanner](str).getOrElse(throw new Exception("Invalid JSON for UserEditProfilePhotoMessage")))
          .flatMap{m=>
            m.fullPlan.map(_.asJson.toString)
          }
      case "UserReadProfilePhotoMessage" =>
        IO(decode[UserReadProfilePhotoMessagePlanner](str).getOrElse(throw new Exception("Invalid JSON for UserReadProfilePhotoMessage")))
          .flatMap{m=>
            m.fullPlan.map(_.asJson.toString)
          }
      case _ =>
        IO.raiseError(new Exception(s"Unknown type: $messageType"))
    }

  val service: HttpRoutes[IO] = HttpRoutes.of[IO]:
    case req @ POST -> Root / "api" / name =>
        println("request received")
        req.as[String].flatMap{executePlan(name, _)}.flatMap(Ok(_))
        .handleErrorWith{e =>
          println(e)
          BadRequest(e.getMessage)
        }
