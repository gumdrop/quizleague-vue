package quizleague.web.maintain.fixtures

import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturePutService
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.service.fixtures.FixturesPutService
import quizleague.web.service.results.ReportsGetService
import quizleague.web.service.results.ReportsPutService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.user.UserService

object FixturesModule {
  
}

object FixtureService extends FixtureGetService with FixturePutService{
  override val teamService = TeamService
  override val venueService = VenueService
  override val reportsService = ReportsService
  override val userService = UserService
}

object FixturesService extends FixturesGetService with FixturesPutService{
  override val fixtureService = FixtureService
}

object ReportsService extends ReportsGetService with ReportsPutService{
  override val teamService = TeamService
  override val textService = TextService
}