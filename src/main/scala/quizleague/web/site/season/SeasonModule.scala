package quizleague.web.site.season

import quizleague.web.service.season.SeasonGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.competition.CompetitionService

object SeasonModule {
  
}

object SeasonService extends SeasonGetService{
  
  override val textService = TextService
  override val venueService = VenueService
  override val competitionService = CompetitionService
  
}