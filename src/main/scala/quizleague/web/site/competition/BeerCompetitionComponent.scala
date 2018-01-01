package quizleague.web.site.competition


import quizleague.web.core._
import quizleague.web.core.IdComponent



object BeerCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id"></competition>"""
  override val components = @@(BeerCompetitionComponent)
}

object BeerCompetitionComponent extends Component{
  type facade = IdComponent
  val name = "competition"
  val template = """
  <v-container grid-list-lg fluid v-if="item">
    <v-layout column grid-list-lg>
      <v-flex>      
        <ql-named-text :name="item.textName"></ql-named-text>
        <ql-text :id="item.text.id"></ql-text>
      </v-flex>
      <v-flex><league-tables :id="id"></league-tables></v-flex>
      <v-flex><latest-results :id="id"></latest-results></v-flex>
    </v-layout>
   </v-container>
"""""
  
  override val props = @@("id")
  override val subParams = Map("id" -> "item")
 
  
  override val subscriptions = Map("item" -> (c => CompetitionService.get(c.id)))
      
  override val components = @@(LatestResults, LeagueTables)
}
