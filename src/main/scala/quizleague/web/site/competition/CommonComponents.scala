package quizleague.web.site.competition

import quizleague.web.core.@@
import quizleague.web.core.Component
import quizleague.web.core.IdComponent
import quizleague.web.core.IdComponent

object LeagueTables extends Component{
  type facade = IdComponent
  val name = "league-tables"
  val template = """
    <v-card>
      <v-card-title primary-title><h3 class="headline mb-0">League Table</h3></v-card-title>
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
  val template = """<v-card v-if="latestResults">
      <v-card-title primary-title><h3 class="headline mb-0">Results</h3></v-card-title>
      <v-card-title>Latest results</v-card-title>
      <v-card-text>
        <div v-for="results in latestResults" :key="results.id">
          <div><h4>{{results.date | date("d MMM yyyy")}}</h4></div>
          <ql-results-simple :results="results.fixtures | combine" ></ql-results-simple>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn flat to="results">Show All</v-btn>
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
  val template = """<v-card>
      <v-card-title primary-title><h3 class="headline mb-0">Fixtures</h3></v-card-title>
      <v-card-title>Next fixtures</v-card-title>
      <v-card-text  v-if="nextFixtures">
        <div v-for="fixtures in nextFixtures" :key="fixtures.id">
          <div><h4>{{fixtures.date | date("d MMM yyyy")}}</h4></div>
          <ql-fixtures-simple :fixtures="fixtures.fixtures | combine" ></ql-fixtures-simple>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn flat to="fixtures">Show All</v-btn>
      </v-card-actions>
    </v-card>"""
  

 
  override val props = @@("id")
  override val subParams = Map("id" -> "nextFixtures")
  override val subscriptions = Map(
      "nextFixtures" -> (c => CompetitionViewService.nextFixtures(c.id,1))
      )
}