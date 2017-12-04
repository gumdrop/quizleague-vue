package quizleague.web.site.competition

import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.site.results.ResultsService
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService

object CompetitionModule {
  
}

object CompetitionService extends CompetitionGetService{
  
  override val resultsService = ResultsService
  override val fixturesService = FixturesService
  override val leagueTableService = LeagueTableService
  override val textService = TextService
  override val venueService = VenueService
}