package quizleague.web.model

import scalajs.js.Array
import quizleague.web.util.rx.RefObservable


case class Results(
    id:String, 
    fixtures:RefObservable[Fixtures],
    results:Array[RefObservable[Result]])
    

case class Result(
    id:String, 
    fixture:RefObservable[Fixture],
    homeScore:Int,
    awayScore:Int,
    submitter:RefObservable[User],
    note:String,
    reports:RefObservable[Reports])
    

case class Reports(
    id:String,
    reports:Array[Report],
    isEmpty:Boolean)
    

case class Report(
    team:RefObservable[Team],
    text:RefObservable[Text])
    