package quizleague.web.maintain.competition

import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.service.competition.CompetitionPutService
import quizleague.web.maintain.fixtures.FixturesService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.leaguetable.LeagueTableService

object CompetitionModule {
  
}

object CompetitionService extends CompetitionGetService with CompetitionPutService{
  override val fixturesService = FixturesService
  override val venueService = VenueService
  override val textService = TextService
  override val leagueTableService = LeagueTableService
}

