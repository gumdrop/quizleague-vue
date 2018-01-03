package quizleague.web.site.team

import com.felstar.scalajs.vue._
import scalajs.js
import js.JSConverters
import quizleague.web.core.RouteComponent
import quizleague.web.core.Component
import quizleague.web.site.text.TextService
import quizleague.web.core._
import quizleague.web.model.Team
import quizleague.web.site.ApplicationContextService
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.model.ApplicationContext
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.site._

object TeamPage extends RouteComponent{
  override val template = """<ql-team :id="$route.params.id"></ql-team>"""
}

@js.native
trait TeamComponent extends IdComponent{
  val appConfig:ApplicationContext
}
object TeamComponent extends Component{

  type facade = IdComponent
  
  override val name = "ql-team"
  override val template = """
            <v-container v-if="team && appConfig" grid-list-lg fluid>
              <v-layout column>

           <v-flex><ql-text :id="team.text.id"></ql-text></v-flex>

            <v-flex><v-card >
              <v-card-title primary-title><h3 class="headline mb-0">Results</h3></v-card-title>
              <v-card-title >Last few results</v-card-title>
              <v-card-text>
                <ql-fixtures-simple :fixtures="results(id, appConfig.currentSeason.id)" :inlineDetails="true"></ql-fixtures-simple>
              </v-card-text>
              <v-card-actions>
                <v-btn flat :to="id + '/results'" primary>Show All</v-btn>
                <v-btn flat><v-icon>insert_chart</v-icon>Graphs & Stats</v-btn>
              </v-card-actions>
            </v-card>
            </v-flex>      
            <v-flex><v-card >
              <v-card-title primary-title><h3 class="headline mb-0">Fixtures</h3></v-card-title>
              <v-card-title >Next few fixtures</v-card-title>
              <v-card-text>
                <ql-fixtures-simple :fixtures="fixtures(id, appConfig.currentSeason.id)" :inlineDetails="true"></ql-fixtures-simple>
              </v-card-text>
              <v-card-actions>
                <v-btn flat :to="id + '/fixtures'" primary>Show All</v-btn>
                <v-btn flat ><v-icon>content_copy</v-icon>Calendar URL</v-btn>
                <v-btn flat><v-icon>file_download</v-icon>Download Calendar</v-btn>
              </v-card-actions>
            </v-card>
            </v-flex>
          </v-layout>
          </v-container>"""
  override val props = @@("id")
  override val subParams = List("id"->"team")
  override val subscriptions = Map("team" -> (v => TeamService.get(v.id)), "appConfig" -> (c => ApplicationContextService.get))
  override val methods = Map(
      "fixtures" -> ((teamId:String, seasonId:String) => FixtureService.teamFixtures(teamId,seasonId,5)),
      "results" ->  ((teamId:String, seasonId:String) => FixtureService.teamResults(teamId,seasonId,5))   
  )
}

object TeamTitleComponent extends RouteComponent {
  override val template = """<ql-team-title :id="$route.params.id"></ql-team-title>"""
}

object TeamTitle extends Component {
  
  type facade = IdComponent
  
  override val name = "ql-team-title"
  override val template = """
    <v-toolbar      
      color="amber darken-3"
      dark
      clipped-left
      v-if="team">
      <v-toolbar-title class="white--text" >
        {{team.name}}
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-tooltip top><v-btn icon slot="activator"><v-icon>email</v-icon></v-btn><span>Contact Us</span></v-tooltip>
      <v-tooltip top><v-btn icon :to="'/venue/' + team.venue.id" slot="activator"><v-icon>location_on</v-icon></v-btn><span>Venue</span></v-tooltip>
    </v-toolbar>"""
  
   override val props = @@("id")
   override val subParams = List("id"->"team")
   override val subscriptions = Map("team" -> (v => TeamService.get(v.id)))

}

object TeamMenuComponent extends RouteComponent {
  
  override val template = """
     <v-list dense v-if="teams">
       <v-list-tile v-for="team in sort(teams) " :key="team.id">
        <v-btn :to="'/team/' + team.id" flat >{{team.name}}</v-btn>
       </v-list-tile>
     </v-list>"""
    override val subscriptions = Map("teams" -> (c => TeamService.list))
    override val methods = Map(
        "sort" -> ((teams:js.Array[Team]) => teams.sortBy(_.shortName))
    )
}