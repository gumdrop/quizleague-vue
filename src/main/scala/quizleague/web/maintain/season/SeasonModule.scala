package quizleague.web.maintain.season

import quizleague.web.service.season.SeasonGetService
import quizleague.web.service.season.SeasonPutService
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.venue.VenueService

object SeasonModule {
  
}

object SeasonService extends SeasonGetService with SeasonPutService{
  override val competitionService = CompetitionService
  override val textService = TextService
  override val venueService = VenueService
}