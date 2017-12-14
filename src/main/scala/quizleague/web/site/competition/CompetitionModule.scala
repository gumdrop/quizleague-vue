package quizleague.web.site.competition

import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.season.SeasonService
import rxscalajs.Observable._
import rxscalajs.Observable
import quizleague.web.model.CompetitionType
import quizleague.web.core._
import com.felstar.scalajs.vue.Router
import com.felstar.scalajs.vue.RouteConfig
import quizleague.web.model.Competition
import scalajs.js
import js.JSConverters._
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.model.Season
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonWatchService
import org.threeten.bp.LocalDate
import quizleague.web.model.Fixtures
import quizleague.util.collection._

//@Routes(
//    root = false,
//    Route(
//        path = "competition",
//        children = @@@(
//          Route(
//            path = ":id",
//            children = @@@(
//              Route("", children = @@@(
//               Route("league", component = %%[LeagueCompetitionComponent]),
//               Route("cup", component = %%[CupCompetitionComponent]),
//               Route("plate", component = %%[PlateCompetitionComponent]),
//               Route("subsidiary", component = %%[BeerCompetitionComponent]),
//               Route("singleton", component = %%[SingletonCompetitionComponent]),
//               Route("", component = %%[CompetitionTitleComponent], outlet = "title")
//               )
//              ),
//              Route(":name", children = @@@(
//                Route("results", children = @@@(
//                    Route("", component = %%[CompetitionResultsComponent]),
//                    Route("", component = %%[CompetitionResultsTitleComponent], outlet = "title")
//                    )
//                ),
//                Route("fixtures", children = @@@(
//                    Route("", component = %%[CompetitionFixturesComponent]),
//                    Route("", component = %%[CompetitionFixturesTitleComponent], outlet = "title"))
//                )
//               )
//              )
//            )
//          ) ,
//          Route(path = "", children = @@@(
//            Route(path = "", component = %%[CompetitionsComponent]),
//            Route(path = "", component = %%[CompetitionsTitleComponent], outlet = "title")
//          )
//         ),
//         Route(path = "", component = %%[CompetitionMenuComponent], outlet = "sidemenu")
//        )
//        
//   )
// )







object CompetitionModule extends Module {
  
  override val routes = @@(
        RouteConfig(path="/competition/:id/league", components=Map("default" -> LeagueCompetitionPage(), "title" -> CompetitionTitle(), "sidenav" -> CompetitionMenu())),
        RouteConfig(path="/competition/:id/subsidiary", components=Map("default" -> BeerCompetitionPage(), "title" -> CompetitionTitle(), "sidenav" -> CompetitionMenu())),
        RouteConfig(path="/competition/:id/cup", components=Map("default" -> CupCompetitionPage(), "title" -> CompetitionTitle(), "sidenav" -> CompetitionMenu())),
        RouteConfig(path="/competition/:id/singleton", components=Map("default" -> SingletonCompetitionPage(), "title" -> CompetitionTitle(), "sidenav" -> CompetitionMenu())),
        RouteConfig(path="/competition/:id/results", components=Map("default" -> ResultsPage(), "title" -> CompetitionTitle(), "sidenav" -> CompetitionMenu())),
        RouteConfig(path="/competition/:id/fixtures", components=Map("default" -> FixturesPage(), "title" -> CompetitionTitle(), "sidenav" -> CompetitionMenu())),

        RouteConfig(path="/competition", components=Map(/*"default" -> CompetitionsComponent(), */"title" -> CompetitionsTitleComponent(), "sidenav" -> CompetitionMenu())
  ))
}

object CompetitionService extends CompetitionGetService{
  
  override val fixturesService = FixturesService
  override val leagueTableService = LeagueTableService
  override val textService = TextService
  override val venueService = VenueService
  
  def firstClassCompetitions(seasonId:String) = competitions(seasonId).map(c => c.filter(_.typeName != CompetitionType.subsidiary.toString()))
  
  
  def competitions(seasonId:String) = SeasonService.get(seasonId).map(_.competitions.map(_.obs).toSeq).map(cs => combineLatest(cs)).flatten.map(_.toJSArray)
}

object CompetitionViewService extends SeasonWatchService {
  
  def competitions() = season.flatMap(s => CompetitionService.competitions(s.id))
  
  def fixtures(competitionId:String) = CompetitionService.get(competitionId).flatMap(c => Observable.combineLatest(c.fixtures.map(_.obs).toSeq)).map(_.toJSArray)
  
  def nextFixtures(competitionId:String, take:Integer = Integer.MAX_VALUE):Observable[js.Array[Fixtures]] = {
    val today = LocalDate.now.toString
    fixtures(competitionId).map(_.filter(_.date >= today).sortBy(_.date).take(take))
  }

  def latestResults(competitionId:String, take:Integer = Integer.MAX_VALUE):Observable[js.Array[Fixtures]] = {
    val today = LocalDate.now.toString
    fixtures(competitionId).map(_.filter(_.date <= today).sortBy(_.date)(Desc).take(take))
  }
}
