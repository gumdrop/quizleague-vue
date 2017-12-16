package quizleague.web.site.competition


import quizleague.web.core._
import quizleague.web.core.IdComponent

object CupCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" text-name="cup-comp"></competition>"""
  override val components = @@(KnockoutCompetitionComponent)
}


object KnockoutCompetitionComponent extends Component{
  type facade = IdComponent
  val name = "competition"
  val template = """
  <div v-if="item" >
    <ql-named-text :name="textName"></ql-named-text>
    <ql-text :id="item.text.id"></ql-text>
    <latest-results :id="id"></latest-results>
    <next-fixtures :id="id"></next-fixtures>
  </div>"""
  
  override val props = @@("id", "textName")
  override val subParams = Map("id" -> "item")
 
  
  override val subscriptions = Map("item" -> (c => CompetitionService.get(c.id)))
      
  override val components = @@(LatestResults,NextFixtures, LeagueTables)
}
