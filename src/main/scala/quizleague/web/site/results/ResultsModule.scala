package quizleague.web.site.results


import quizleague.web.service.results.{ ResultGetService, ResultsGetService, ReportsGetService }
import quizleague.web.site.fixtures.{ FixtureService, FixturesService }
import quizleague.web.site.team.TeamService
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures.AllFixturesComponent
import quizleague.web.site.fixtures.AllFixturesTitleComponent
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.core._
//import quizleague.web.site.global.ApplicationContextService

//import quizleague.web.model.Season
//import quizleague.web.site.season.SeasonModule
//import quizleague.web.site.text.TextModule
//import quizleague.web.util.Logging._
//import quizleague.web.site.common.SeasonSelectService
//import angulate2.ext.classModeJS
//import quizleague.web.service.CachingService
//import quizleague.web.model.Result
//import quizleague.web.model.Team
//import quizleague.web.core._
//import inviewport.InViewportModule
//
//@NgModule(
//  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, ResultsRoutesModule,CommonAppModule, TextModule, FixturesModule, ResultsComponentsModule, SeasonModule],
//  declarations = @@[AllResultsComponent, AllResultsTitleComponent, ResultsMenuComponent, ReportComponent, ReportTitleComponent],
//  providers = @@[ResultsService, ResultService, ResultsViewService, ReportsService])
//class ResultsModule
//
//@Routes(
//  root = false,
//  Route(
//    path = "results",
//    children = @@@(
//      Route(path = "all", children = @@@(
//        Route(path = "", component = %%[AllResultsComponent]),
//        Route(path = "", component = %%[AllResultsTitleComponent], outlet = "title"))),
//      Route(path = "fixtures", children = @@@(
//        Route(path = "", component = %%[AllFixturesComponent]),
//        Route(path = "", component = %%[AllFixturesTitleComponent], outlet = "title"))),
//      Route(path = "", component = %%[ResultsMenuComponent], outlet = "sidemenu"),
//      Route(path = ":id", children = @@@(
//          Route("reports", children = @@@(
//            Route("", component = %%[ReportComponent]),
//            Route("", component = %%[ReportTitleComponent], outlet="title")
//          )))),
//      Route(path = "",   redirectTo = "all", pathMatch = "full" )  
//    ))
// 
//)
//@classModeScala
//class ResultsRoutesModule
//
//@NgModule(
//  imports = @@[CommonModule,RouterModule,MaterialModule,InViewportModule],
//  declarations = @@[SimpleResultsComponent],
//  exports = @@[SimpleResultsComponent]
//  )
//class ResultsComponentsModule

object ResultsModule extends Module {
  
  override val components = @@(SimpleResultsComponent)
  override val routes = @@(
        
  )
  
}


object ResultsService extends ResultsGetService {
    override val resultService = ResultService
    override val fixturesService = FixturesService
  
  //def latestResults(season:Season) = list(Some(s"latest/${season.id}"))

    }


object ResultService extends ResultGetService {
    override val userService = UserService
    override val fixtureService = FixtureService
    override val teamService = TeamService
    val reportsService = ReportsService

  //def teamResults(season:Season,team:Team, take:Int = Integer.MAX_VALUE) = list(Some(s"season/${season.id}/team/${team.id}?take=$take"))
}


object ReportsService extends ReportsGetService {
    val textService = TextService
    val teamService = TeamService
}



//object ResultsViewService (
//  override val applicationContextService:ApplicationContextService 
//)extends SeasonSelectService
