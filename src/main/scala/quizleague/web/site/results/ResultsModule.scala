package quizleague.web.site.results

import quizleague.web.service.results.{ ReportsGetService }
import quizleague.web.site.fixtures.{ FixtureService, FixturesService }
import quizleague.web.site.team.TeamService
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures.AllFixturesComponent
import quizleague.web.site.fixtures.AllFixturesTitleComponent
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.core._
import com.felstar.scalajs.vue.RouteConfig
import quizleague.web.site.fixtures.AllFixturesTitleComponent
import quizleague.web.site.fixtures.AllFixturesPage
import quizleague.web.site.season.SeasonWatchService
import quizleague.web.site.fixtures.FixturesService



object ResultsModule extends Module {

  override val components = @@(SimpleResultsComponent)

  override val routes = @@(
    RouteConfig(
      path = "/results/all",
      components = Map("default" -> AllResultsPage(), "title" -> AllResultsTitleComponent(), "sidenav" -> ResultsMenuComponent())),
    RouteConfig(
      path = "/fixtures/all",
      components = Map("default" -> AllFixturesPage(), "title" -> AllFixturesTitleComponent(), "sidenav" -> ResultsMenuComponent())),
    RouteConfig(
      path = "/results/submit",
      components = Map("default" -> AllFixturesPage(), "title" -> AllFixturesTitleComponent(), "sidenav" -> ResultsMenuComponent())),

    RouteConfig(path = "/results", redirect = "/results/all"))

}

object ReportsService extends ReportsGetService {
  val textService = TextService
  val teamService = TeamService
}

object ResultsViewService extends SeasonWatchService{
  def results = season.flatMap(s => FixturesService.spentFixtures(s.id))
}



