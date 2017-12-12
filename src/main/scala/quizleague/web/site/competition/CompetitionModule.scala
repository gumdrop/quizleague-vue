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
  
  override val components = @@(CompetitionMenuComponent)
  
  override val routes = @@(
//        RouteConfig(path="/competition/:id/league", components=Map("default" -> LeagueCompetitionComponent(), "title" -> CompetitionTitleComponent(), "sidenav" -> CompetitionMenu()),
        RouteConfig(path="/competition", components=Map(/*"default" -> CompetitionsComponent(), "title" -> CompetitionsTitleComponent(),*/ "sidenav" -> CompetitionMenu())
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