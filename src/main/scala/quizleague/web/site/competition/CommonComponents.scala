package quizleague.web.site.competition

import quizleague.web.core.@@
import quizleague.web.core.Component
import quizleague.web.core.IdComponent
import quizleague.web.core.IdComponent
import quizleague.web.core.RouteComponent

object LeagueTables extends Component{
  type facade = IdComponent
  val name = "league-tables"
  val template = """
    <v-card class="mb-x">
      <v-card-title primary-title><h3 class="headline mb-x">League Table</h3></v-card-title>
      <v-card-text>
        <ql-league-table v-for="table in item.tables" :key="table.id" :id="table.id"></ql-league-table>
      </v-card-text>
    </v-card>"""
  
  override val props = @@("id")
  override val subParams = Map("id" -> "item")
  override val subscriptions = Map("item" -> (c => CompetitionService.get(c.id)))
}

object LatestResults extends Component{
  type facade = IdComponent
  val name = "latest-results"
  val template = """<v-card class="mb-x">
      <v-card-title primary-title><h3 class="headline mb-x">Results</h3></v-card-title>
      <v-card-title>Latest results</v-card-title>
      <v-card-text  v-if="latestResults">
        <div v-for="results in latestResults" :key="results.id">
          <div><h4>{{results.date | date("d MMM yyyy")}}</h4></div>
          <ql-results-simple :results="results.fixtures | combine" ></ql-results-simple>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" flat to="results">Show All</v-btn>
      </v-card-actions>
    </v-card>"""
  
  override val props = @@("id")
  
    override val subParams = Map("id" -> "latestResults")
    override val subscriptions = Map(
      "latestResults" -> (c => CompetitionViewService.latestResults(c.id,1))
    )
}

object NextFixtures extends Component{
  type facade = IdComponent
  val name = "next-fixtures"
  val template = """<v-card class="mb-x">
      <v-card-title primary-title><h3 class="headline mb-x">Fixtures</h3></v-card-title>
      <v-card-title>Next fixtures</v-card-title>
      <v-card-text  v-if="nextFixtures">
        <div v-for="fixtures in nextFixtures" :key="fixtures.id">
          <div><h4>{{fixtures.date | date("d MMM yyyy")}}</h4></div>
          <ql-fixtures-simple :fixtures="fixtures.fixtures | combine" ></ql-fixtures-simple>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" flat to="fixtures">Show All</v-btn>
      </v-card-actions>
    </v-card>"""
  

 
  override val props = @@("id")
  override val subParams = Map("id" -> "nextFixtures")
  override val subscriptions = Map(
      "nextFixtures" -> (c => CompetitionViewService.nextFixtures(c.id,1))
      )
}


object CompetitionTitle extends RouteComponent{
  val template = """<competition-title :id="$route.params.id"></competition-title>"""
  override val components = @@(CompetitionTitleComponent)
}
  
object CompetitionTitleComponent extends Component{
  type facade = IdComponent
  val name = "competition-title"
  val template = """<v-toolbar      
      color="purple darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" v-if="item" >
        {{item.name}} {{season.startYear}}/{{season.endYear}}
      </v-toolbar-title>
    </v-toolbar>"""
  
  override val props = @@("id")
  override val subParams = Map("id" -> "item", "id" -> "season")
  override val subscriptions = Map(
      "item" -> (c => CompetitionService.get(c.id)),
      "season" -> (c => CompetitionViewService.parentSeason(c.id))
      )
}

object ResultsPage extends RouteComponent{
  val template = """<all-results :id="$route.params.id"></all-results>"""
  override val components = @@(AllResults) 
}

trait ResultsComponent extends Component {
  type facade = IdComponent
 

  override val props = @@("id")

  override val subParams = Map("id" -> "latestResults")
  override val subscriptions = Map("latestResults" -> (c => CompetitionViewService.latestResults(c.id, take)))

  def take:Int
}


object AllResults extends ResultsComponent{
  val name = "all-results"
  val template = """
    <v-container grid-list-lg fluid v-if="latestResults">
    <v-layout column>
    <v-flex v-for="results in latestResults" :key="results.id">
      <v-card>
        <v-card-title primary-title><h3 class="headline mb-x">{{results.date | date("d MMM yyyy")}}</h3></v-card-title>
        <v-card-text>
            <ql-results-simple :results="results.fixtures | combine" ></ql-results-simple>
          </div>
        </v-card-text>
      </v-card>
    </v-flex>
    </v-layout>
    </v-container>"""
    
    val take = Integer.MAX_VALUE
}

object FixturesPage extends RouteComponent{
  val template = """<remaining-fixtures :id="$route.params.id"></remaining-fixtures>"""
  override val components = @@(RemainingFixtures) 
}

object RemainingFixtures extends Component{
  type facade = IdComponent
  val name = "remaining-fixtures"
  val template = """
    <v-container grid-list-lg fluid v-if="nextFixtures">
    <v-layout column>
    <v-flex v-for="fixtures in nextFixtures" :key="fixtures.id">
      <v-card>
        <v-card-title primary-title><h3 class="headline mb-x">{{fixtures.date | date("d MMM yyyy")}}</h3></v-card-title>
        <v-card-text>
            <ql-fixtures-simple :fixtures="fixtures.fixtures | combine" ></ql-fixtures-simple>
        </v-card-text>
      </v-card>
    </v-layout>
    </v-container>"""
  

 
  override val props = @@("id")
  override val subParams = Map("id" -> "nextFixtures")
  override val subscriptions = Map(
      "nextFixtures" -> (c => CompetitionViewService.nextFixtures(c.id))
      )
}
