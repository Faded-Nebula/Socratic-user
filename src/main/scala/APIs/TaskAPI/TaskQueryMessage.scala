package APIs.TaskAPI

import java.io.InputStream

case class TaskQueryMessage(userName:String, taskName:String, periodicalName:String, pdfBase64:String) extends TaskMessage[String]
