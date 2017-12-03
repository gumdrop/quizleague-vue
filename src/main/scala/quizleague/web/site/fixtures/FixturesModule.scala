package quizleague.web.site.fixtures

import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.site.team.TeamService
import quizleague.web.site.venue.VenueService
import quizleague.web.model._
import quizleague.web.site.team.TeamService
import quizleague.web.core._



object FixturesModule extends Module{
  
  override val components = @@(SimpleFixturesComponent)
}


object FixturesService extends FixturesGetService{
  override val fixtureService = FixtureService


  // def nextFixtures(season:Season) = list(Some(s"next/${season.id}"))
}


object FixtureService extends FixtureGetService {
    override val venueService = VenueService
    override val teamService = TeamService

  //def teamFixtures(season:Season,team:Team, take:Int = Integer.MAX_VALUE) = list(Some(s"season/${season.id}/team/${team.id}?take=$take"))
}

