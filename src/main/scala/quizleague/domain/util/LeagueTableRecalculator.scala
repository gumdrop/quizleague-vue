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
     
    def points(home:Boolean)(fixture:Fixture) = {
        val score1 = if(home)fixture.result.get.homeScore else fixture.result.get.awayScore
        val score2 = if(home)fixture.result.get.awayScore else fixture.result.get.homeScore
        if(score1 > score2) 2 else if (score1 == score2) 1 else 0
    }
    
    val scores = map.map{case(team,fixtures) => {
      val homeFixtures = fixtures.filter(_.home == team)
      val awayFixtures = fixtures.filter(_.away == team)
      
      val homeTotal = homeFixtures.map(points(true) _).sum
      
      val awayTotal = awayFixtures.map(points(false) _).sum
      
     // val others = fixtures.filter
      ((team, homeTotal + awayTotal))  
    }}
    
 
  }
  
}