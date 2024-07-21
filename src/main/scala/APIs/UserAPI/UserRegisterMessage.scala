package APIs.UserAPI
import Shared.UserInfo
case class UserRegisterMessage(userInfo: UserInfo, password: String) extends UserMessage[String]
