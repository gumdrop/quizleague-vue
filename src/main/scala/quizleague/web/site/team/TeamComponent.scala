package quizleague.web.site.team

import com.felstar.scalajs.vue._
import scalajs.js
import js.JSConverters
import quizleague.web.core.RouteComponent
import quizleague.web.core.Component
import quizleague.web.site.text.TextService
import quizleague.web.core._
import quizleague.web.model.Team

object TeamPage extends RouteComponent {
  override val template = """<ql-team :id="$route.params.id"></ql-team>"""
}

object TeamComponent extends Component {

  override val name = "ql-team"
  override val template = """
          <div v-if="team">
           <ql-text :id="team.text.id"></ql-text>
            <v-card>
              <v-card-title primary-title><h3 class="headline mb-0">Results</h3></v-card-title>
              <v-card-title >Last few results</v-card-title>
              <v-card-text>
              </v-card-text>
              <v-card-actions>
                <v-btn flat>Show All</v-btn>
                <v-btn flat>Graphs & Stats</v-btn>
              </v-card-actions>
            </v-card>
            <v-card>
              <v-card-title primary-title><h3 class="headline mb-0">Fixtures</h3></v-card-title>
              <v-card-title >Next few fixtures</v-card-title>
              <v-card-text>
              </v-card-text>
              <v-card-actions>
                <v-btn flat>Show All</v-btn>
                <v-btn flat >Calendar URL</v-btn>
                <v-btn flat>Download Calendar</v-btn>
              </v-card-actions>
            </v-card>
          </div>"""
  override val props = @@("id")
  override val subParams = Map("id"->"team")
  override val subscriptions = Map("team" -> ((v: js.Dynamic) => TeamService.get(v.id.toString)))

}

object TeamTitleComponent extends RouteComponent {
  override val template = """<ql-team-title :id="$route.params.id"></ql-team-title>"""
}

object TeamTitle extends Component {
  
  override val name = "ql-team-title"
  override val template = """
    <v-toolbar      
      color="orange darken-3"
      dark
      clipped-left
      v-if="team">
      <v-toolbar-title class="white--text" >
        {{team.name}}  : Venue {{async(team.venue).name}}
      </v-toolbar-title>
    </v-toolbar>"""
  
   override val props = @@("id")
   override val subParams = Map("id"->"team")
   override val subscriptions = Map("team" -> ((v: js.Dynamic) => TeamService.get(v.id.toString)))

}

object TeamMenuComponent extends RouteComponent {
  
  override val template = """<v-list dense v-if="teams">
                    <v-list-tile v-for="team in sort(teams) " :key="team.id">
                    <v-btn v-bind:to="'/team/' + team.id" flat style="text-transform: none;">{{team.name}}</v-btn>
                    </v-list-tile>
                   </v-list>"""
    override val subscriptions = Map("teams" -> ((v: js.Dynamic) => TeamService.list))
    override val methods = Map("sort" -> ((teams:js.Array[Team]) => teams.sortBy(_.shortName)))
}