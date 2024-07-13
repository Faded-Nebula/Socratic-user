package APIs.UserAPI

case class UserSubmissionMessage(userName:String, taskName:String, periodicalName:String, pdfBase64: String) extends UserMessage[String]
