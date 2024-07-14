package APIs.UserAPI

case class UserSubmissionMessage(userName:String, taskName:String, periodicalName:String, pdfBase64:String, researchArea:String, Abstract:String, TLDR:String) extends UserMessage[String]
