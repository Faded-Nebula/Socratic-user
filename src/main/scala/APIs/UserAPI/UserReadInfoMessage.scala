package APIs.UserAPI

case class UserReadInfoMessage(userName:String, property:String) extends UserMessage[String]
