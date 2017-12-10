package quizleague.web.site.fixtures

import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.site.team.TeamService
import quizleague.web.site.venue.VenueService
import quizleague.web.model._
import quizleague.web.site.team.TeamService
import quizleague.web.core._
import scalajs.js
import js.JSConverters._
import rxscalajs.Observable
import rxscalajs.Observable._
import quizleague.web.model.CompetitionType
import org.threeten.bp.LocalDate
import quizleague.web.util.Logging._
import quizleague.web.site.season.SeasonService
import quizleague.web.site.competition.CompetitionService
import quizleague.util.collection.Desc
import quizleague.web.site.user.UserService
import quizleague.web.site.results.ReportsService

object FixturesModule extends Module {

  override val components = @@(SimpleFixturesComponent, FixtureLineComponent)
}

object FixturesService extends FixturesGetService {
  override val fixtureService = FixtureService

  def nextFixtures(seasonId: String): Observable[js.Array[Fixtures]] = activeFixtures(seasonId,1)
  
  def activeFixtures(seasonId: String, take:Int = Integer.MAX_VALUE) = {
        val today = LocalDate.now.toString()

    val competitions = CompetitionService.firstClassCompetitions(seasonId)

    val fxs = competitions.map(_.flatMap(_.fixtures.map(_.obs))).switchMap(o => combineLatest(o))

    fxs.map(_.filter(_.date >= today).sortBy(_.date).take(take).toJSArray)
  }


}

object FixtureService extends FixtureGetService {
  override val venueService = VenueService
  override val teamService = TeamService
  override val userService = UserService
  override val reportsService = ReportsService

  //def teamFixtures(season:Season,team:Team, take:Int = Integer.MAX_VALUE) = list(Some(s"season/${season.id}/team/${team.id}?take=$take"))
  
    def teamFixtures(teamId: String, seasonId: String, take:Int = Integer.MAX_VALUE): Observable[js.Array[Fixture]] = {

    
    val fixtures = FixturesService.activeFixtures(seasonId)
    
    val tf = fixtures.switchMap(fx => combineLatest(fx.flatMap(_.fixtures).map(_.obs)))
    .map(_.filter(f => f.home.id == teamId || f.away.id == teamId).sortBy(_.date))
      
    tf.map(_.take(take).toJSArray)
  }
}

