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
  <div v-if="item" >
    <ql-named-text name="beer-comp"></ql-named-text>
    <ql-text :id="item.text.id"></ql-text>
  <v-container grid-list-xl>
    <v-layout column>
      <league-tables :id="id"></league-tables>
      <latest-results :id="id"></latest-results>
    </v-layout>
   </v-container>
  </div>"""
  
  override val props = @@("id")
  override val subParams = Map("id" -> "item")
 
  
  override val subscriptions = Map("item" -> (c => CompetitionService.get(c.id)))
      
  override val components = @@(LatestResults, LeagueTables)
}
