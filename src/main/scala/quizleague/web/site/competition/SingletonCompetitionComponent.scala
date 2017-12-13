package quizleague.web.site.competition

import quizleague.web.core._
import quizleague.web.core.IdComponent


object BuzzerCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" text-name="buzzer-comp"></competition>"""
  override val components = @@(SingletonCompetitionComponent)
}

object IndividualCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" text-name="individual-comp"></competition>"""
  override val components = @@(SingletonCompetitionComponent)
}

object SingletonCompetitionComponent extends Component{
  
  type facade = IdComponent
  
  val name = "competition"
  
  val template = """ 
<div v-if="item">
    <ql-named-text :name="textName"></ql-named-text>
    <v-card>
     <v-card-text>
      <div><b>This season's competition will take place at <a :to="'/venue/' + item.event.venue.id">{{async(item.event.venue).name}}</a> on {{item.event.date | date("d MMMM yyyy")}} starting at {{item.event.start)}}</b> </div>
      <br>
      <ql-text :id="item.text.id"></ql-text>
      </v-card-text>
    </v-card> 
  </div>"""
  
  override val props = @@("id", "textName")
  override val subParams = Map("id" -> "item")
 
  
  override val subscriptions = Map("item" -> (c => CompetitionService.get(c.id)))
      

  
}