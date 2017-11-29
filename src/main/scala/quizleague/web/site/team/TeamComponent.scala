package quizleague.web.site.team

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import quizleague.web.core.RouteComponent

object TeamsComponent extends RouteComponent{
  override val template="""<span>Teams</span>"""

}


object TeamSidenavComponent extends RouteComponent{
   override val template="""
  <v-list dense>
    <v-list-tile>Team 1</v-list-tile>
    <v-list-tile>Team 2</v-list-tile>
  </v-list>"""

}