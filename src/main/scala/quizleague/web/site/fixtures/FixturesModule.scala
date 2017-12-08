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

object FixturesModule extends Module {

  override val components = @@(SimpleFixturesComponent, FixtureLineComponent)
}

object FixturesService extends FixturesGetService {
  override val fixtureService = FixtureService

  def nextFixtures(seasonId: String): Observable[js.Array[Fixtures]] = {
   
    val competitions = SeasonService.get(seasonId).map(_.competitions.map(_.obs).toSeq).map(cs => combineLatest(cs)).flatten

    val today = LocalDate.now.toString()

    //val competitions = combineLatest(cs)
    
    val fx = competitions.map(c => c
        .filter(_.typeName != CompetitionType.subsidiary.toString())
        .flatMap(_.fixtures.map(_.obs)))

        
     val fxs = fx.switchMap(o => combineLatest(o))

    fxs.map(_.filter(_.date >= today).sortBy(_.date).take(1).toJSArray)
  }
}

object FixtureService extends FixtureGetService {
  override val venueService = VenueService
  override val teamService = TeamService

  //def teamFixtures(season:Season,team:Team, take:Int = Integer.MAX_VALUE) = list(Some(s"season/${season.id}/team/${team.id}?take=$take"))
}

