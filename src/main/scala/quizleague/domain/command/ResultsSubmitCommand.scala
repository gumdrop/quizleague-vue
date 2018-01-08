package quizleague.domain.command

import quizleague.domain.Fixture

case class ResultsSubmitCommand(fixtures:List[ResultValues], reportText:Option[String], email:String)
case class ResultValues(fixtureId:String, homeScore:Int, awayScore:Int)