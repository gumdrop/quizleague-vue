package quizleague.web.site.home

import quizleague.web.core._
import com.felstar.scalajs.vue.RouteConfig
import scalajs.js

object HomeModule extends Module{
  
  
  override val components = @@(HomePageLeagueTable, NextFixturesComponent, LatestResultsComponent)
  
  override val routes = @@(RouteConfig(path = "/home", components = Map(
      "default" -> HomeComponent())))
      
   
}