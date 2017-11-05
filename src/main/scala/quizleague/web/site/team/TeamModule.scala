package quizleague.web.site.team

import quizleague.web.core._
import com.felstar.scalajs.vue.RouteConfig
import scalajs.js

object TeamModule extends Module{
  
  override val routes = @@(RouteConfig(path = "/team", components = Map(
      "default" -> TeamsComponent(), 
      "sidenav" -> TeamSidenavComponent())))
      
}