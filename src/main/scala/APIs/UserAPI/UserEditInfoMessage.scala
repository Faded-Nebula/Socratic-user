package APIs.UserAPI

case class UserEditInfoMessage(userName:String, property:String, updateValue: String) extends UserMessage[String]
