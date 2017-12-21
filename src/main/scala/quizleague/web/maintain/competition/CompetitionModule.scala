package quizleague.web.maintain.competition

import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.service.competition.CompetitionPutService
import quizleague.web.maintain.fixtures.FixturesService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.leaguetable.LeagueTableService
import quizleague.web.core._
import com.felstar.scalajs.vue.RouteConfig
import quizleague.web.maintain.MaintainMenuComponent

object CompetitionModule extends Module{
    override val routes = @@(     
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/league",
        components = Map("default" -> LeagueCompetitionComponent(), "sidenav" -> MaintainMenuComponent())
      ),
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/cup",
        components = Map("default" -> CupCompetitionComponent(), "sidenav" -> MaintainMenuComponent())
      ),
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/subsidiary",
        components = Map("default" -> SubsidiaryCompetitionComponent(), "sidenav" -> MaintainMenuComponent())
      ),
      RouteConfig(
        path = "/maintain/season/:seasonId/competition/:id/singleton",
        components = Map("default" -> SingletonCompetitionComponent(), "sidenav" -> MaintainMenuComponent())
      ),
 )
}

object CompetitionService extends CompetitionGetService with CompetitionPutService{
  override val fixturesService = FixturesService
  override val venueService = VenueService
  override val textService = TextService
  override val leagueTableService = LeagueTableService
}

