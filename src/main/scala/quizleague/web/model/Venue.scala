package quizleague.web.model

import scala.scalajs.js.annotation.JSExportAll

@JSExportAll
case class Venue(
    id:String,
    name:String,
    address:String,
    phone:String,
    email:String,
    website:String,
    imageURL:String,
    retired:Boolean = false
)

