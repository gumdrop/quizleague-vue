package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.core.Component
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonIdComponent



object AllResultsPage extends RouteComponent{
  val template = """<div>
                      <ql-all-results></ql-all-results>
                    </div>"""
  
  override val components = @@(AllResultsComponent)
}


object AllResultsComponent extends Component{
  val name = "ql-all-results"
  type facade = SeasonIdComponent
  val template = """
    <v-container v-if="fixtures" grid-list-lg fluid>
    <v-layout column>
      <v-flex v-for="fixs in fixtures" :key="fixs.id">
        <v-card>
          <v-card-title primary-title><h3 class="headline mb-0">{{fixs.date | date('d MMM yyyy')}} {{fixs.description}}</h3></v-card-title>
          <v-card-text>
            <ql-fixtures-simple :fixtures="fixs.fixtures | combine"></ql-fixtures-simple>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    </v-container>"""
  subscription("fixtures")(c => ResultsViewService.results)
  
}

object AllResultsTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="red darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" >
        All Results
       </v-toolbar-title>
      &nbsp;
      <ql-season-select :season="season"></ql-season-select>
    </v-toolbar>"""
  
  data("season", ResultsViewService.season)
}