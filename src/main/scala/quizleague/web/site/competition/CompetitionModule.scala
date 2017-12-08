package quizleague.web.site.competition

import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.site.results.ResultsService
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.season.SeasonService
import rxscalajs.Observable._
import quizleague.web.model.CompetitionType

object CompetitionModule {
  
}

object CompetitionService extends CompetitionGetService{
  
  override val resultsService = ResultsService
  override val fixturesService = FixturesService
  override val leagueTableService = LeagueTableService
  override val textService = TextService
  override val venueService = VenueService
  
  def firstClassCompetitions(seasonId:String) = {
        
    val competitions = SeasonService.get(seasonId).map(_.competitions.map(_.obs).toSeq).map(cs => combineLatest(cs)).flatten

    competitions.map(c => c.filter(_.typeName != CompetitionType.subsidiary.toString()))

  }
}