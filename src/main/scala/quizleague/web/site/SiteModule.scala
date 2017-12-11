package quizleague.web.site

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.site.home.HomeModule
import quizleague.web.core._
import quizleague.web.site.team.TeamModule

import quizleague.web.store.Firestore
import io.circe._,io.circe.parser._,io.circe.syntax._,io.circe.scalajs.convertJsToJson
import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._
import rxscalajs.subjects.BehaviorSubject
import quizleague.domain.ApplicationContext
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.site.text.TextModule
import quizleague.web.site.venue.VenueModule
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.service.applicationcontext.ApplicationContextGetService
import quizleague.web.service.globaltext.GlobalTextGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.season.SeasonService
import quizleague.web.site.user.UserService
import quizleague.web.site.text.GlobalTextService
import quizleague.web.site.results.ResultsModule


object SiteModule extends Module {
  
  override val modules = @@(HomeModule, TeamModule, TextModule, VenueModule, FixturesModule, ResultsModule, LeagueTableModule)
  
  override val routes = @@(RouteConfig(path = "",redirect = "/home"))
  
  override val components = @@(SiteComponent)
   
}



object ApplicationContextService extends ApplicationContextGetService{
  
  override val globalTextService = GlobalTextService
  override val seasonService = SeasonService
  override val userService = UserService
  
}