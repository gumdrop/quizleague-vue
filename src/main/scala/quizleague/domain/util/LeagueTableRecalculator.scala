package quizleague.domain.util

import quizleague.domain._

class LeagueTableRecalculator(
    table:LeagueTable,
    fixtures:List[Fixture]) {
  
  def recalulate(){
    val things = for(
      team <- table.rows.map(_.team);
      fixture <- fixtures if (fixture.home == team || fixture.away == team && fixture.result.isDefined)
    ) yield(team, fixture)
    
    val map = things.groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}
    
    val scores = map.map{case(team,fixtures) => {
      val homeTotal = fixtures.filter(_.home == team).map(fixture =>  {
        val homeScore = fixture.result.get.homeScore
        val awayScore = fixture.result.get.awayScore
        if(homeScore > awayScore) 2 else if (homeScore == awayScore) 1 else 0})
      
      val awayTotal = fixtures.filter(_.away == team).map(fixture =>  {
        val homeScore = fixture.result.get.homeScore
        val awayScore = fixture.result.get.awayScore
        if(homeScore < awayScore) 2 else if (homeScore == awayScore) 1 else 0})
        
      ((team, homeTotal + awayTotal))  
    }}
  }
  
}