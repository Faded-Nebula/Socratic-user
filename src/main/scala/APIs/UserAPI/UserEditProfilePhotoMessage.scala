package APIs.UserAPI

case class UserEditProfilePhotoMessage(userName: String, Base64Image:String) extends UserMessage[String]
