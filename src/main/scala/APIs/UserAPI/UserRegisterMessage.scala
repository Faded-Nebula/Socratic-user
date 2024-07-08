package APIs.UserAPI
import Shared.UserInfo
case class UserRegisterMessage(userInfo: UserInfo) extends UserMessage[String]
