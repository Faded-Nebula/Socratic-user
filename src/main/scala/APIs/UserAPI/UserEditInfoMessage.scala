package APIs.UserAPI

case class UserEditInfoMessage(userName:String, property:String) extends UserMessage[String]
