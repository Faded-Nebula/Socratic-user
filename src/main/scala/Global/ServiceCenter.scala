package Global

import Global.GlobalVariables.serviceCode
import Global.ServiceCenter.usermanagementServiceCode
import cats.effect.IO
import com.comcast.ip4s.Port
import org.http4s.Uri

object ServiceCenter {
  val projectName: String = "APP"

  val dbManagerServiceCode = "A000001"
  val doctorServiceCode    = "A000002"
  val patientServiceCode   = "A000003"
  val portalServiceCode    = "A000004"
  val userServiceCode      = "A000005"
  val taskServiceCode      = "A000006"
  val usermanagementServiceCode = "A000010"

  val fullNameMap: Map[String, String] = Map(
    dbManagerServiceCode ->  "数据库管理（DB_Manager）",
    doctorServiceCode    ->  "医生（Doctor）",
    patientServiceCode   ->  "病人（Patient）",
    portalServiceCode    ->  "门户（Portal）",
    userServiceCode      ->  "用户（UserDB）",
    taskServiceCode      ->  "任务（Task）",
    usermanagementServiceCode -> "用户管理（UserManagement）"
  )

  val address: Map[String, String] = Map(
    "DB-Manager" ->     "127.0.0.1:10001",
    "Doctor" ->         "127.0.0.1:10002",
    "Patient" ->        "127.0.0.1:10003",
    "Portal" ->         "127.0.0.1:10004",
    "UserDB" ->         "127.0.0.1:10005",
    "Task" ->           "127.0.0.1:10006",
    "UserManagement" -> "127.0.0.1:10010"
  )
}
