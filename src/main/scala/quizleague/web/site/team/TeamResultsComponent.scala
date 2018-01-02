package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.Component
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.core.IdComponent
import scalajs.js
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonIdComponent
import quizleague.web.core.IdComponent
import quizleague.web.core.IdComponent

object TeamResultsPage extends RouteComponent {
  val template = """<v-container v-if="season" grid-list-lg fluid>
                      <v-layout column>
                      <v-flex><v-card>
                        <v-card-text>
                          <ql-all-team-results  :id="$route.params.id" :seasonId="season.id"></ql-all-team-results>
                        </v-card-text>
                      </v-card></v-flex>
                      <div></div>
                      </v-layout>
                    </v-container>"""
  override val subscriptions = Map("season" -> (c => TeamViewService.season))
  
  
}

object TeamResultsComponent extends Component {
  
  type facade = SeasonIdComponent with IdComponent
  val name = "ql-all-team-results"
  val template = """<ql-results-simple :results="fixtures(id,seasonId)" :inlineDetails="true"></ql-results-simple>"""
  override val methods = Map("fixtures" -> ((teamId:String,seasonId:String) => FixtureService.teamResults(teamId, seasonId)))
  override val props = @@("id","seasonId")
  
}

object TeamResultsTitle extends RouteComponent{
  val template = """<results-title :id="$route.params.id"></results-title>"""
  override val components = @@(TeamResultsTitleComponent)
}

object TeamResultsTitleComponent extends Component{
  type facade = IdComponent
  
  val name = "results-title"
  val template = """<v-toolbar      
      color="amber darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" v-if="team">
        {{team.name}} Results 
       </v-toolbar-title>
      &nbsp;
      <ql-season-select :season="season"></ql-season-select>
    </v-toolbar>"""
  
  override val props = @@("id")
  override val subParams = List("id" -> "team")
  override val data = c => Map("season" -> TeamViewService.season)
  override val subscriptions  = Map("team" -> (c => TeamService.get(c.id)))
}