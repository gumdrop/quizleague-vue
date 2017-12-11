package quizleague.web.site.team

import quizleague.web.core._
import com.felstar.scalajs.vue.RouteConfig
import scalajs.js
import quizleague.web.service.team.TeamGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.user.UserService


object TeamModule extends Module{
  
  override val components = @@(TeamComponent,TeamTitle, TeamFixturesComponent, TeamNameComponent, TeamResultsComponent)
  
  override val routes = @@(      
      RouteConfig(path = "/team", 
          components = Map("default" -> TeamsComponent(), "title" -> TeamsTitleComponent(),"sidenav" -> TeamMenuComponent())),
      RouteConfig(path = "/team/:id", 
          components = Map("default" -> TeamPage(), "title" -> TeamTitleComponent(),"sidenav" -> TeamMenuComponent())),
      RouteConfig(path = "/team/:id/fixtures", 
          components = Map("default" -> TeamFixturesPage(), "title" -> TeamTitleComponent(),"sidenav" -> TeamMenuComponent())),
      RouteConfig(path = "/team/:id/results", 
          components = Map("default" -> TeamResultsPage(), "title" -> TeamTitleComponent(),"sidenav" -> TeamMenuComponent()))

  )

      
}

object TeamService extends TeamGetService{
  
  override val textService = TextService
  override val userService = UserService
  override val venueService = VenueService
  
}