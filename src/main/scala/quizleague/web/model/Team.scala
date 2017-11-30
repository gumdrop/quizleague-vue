package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import scalajs.js
import scala.scalajs.js.annotation._

@JSExportAll
case class Team (
    id:String,
    name:String,
    shortName:String,
    venue:RefObservable[Venue],
    text:RefObservable[Text],
    users:js.Array[RefObservable[User]],
    retired:Boolean
)