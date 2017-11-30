package quizleague.web.model

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
case class User(
    id:String,
    name:String,
    email:String,
    retired:Boolean = false
)