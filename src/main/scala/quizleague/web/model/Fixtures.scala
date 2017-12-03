package quizleague.web.model

import scala.scalajs.js
import quizleague.web.util.rx.RefObservable


case class Fixtures(
    id:String, 
    description:String,
    parentDescription:String,
    date:String,
    start:String,
    duration:Float,
    fixtures:js.Array[RefObservable[Fixture]])
    

case class Fixture(
  id:String,
  description:String,
  parentDescription:String,
  venue: RefObservable[Venue],
  home:RefObservable[Team],
  away:RefObservable[Team],
  date: String,
  time: String,
  duration : Float
)