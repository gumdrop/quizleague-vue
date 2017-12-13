package quizleague.web.site.competition

import quizleague.web.core._
import quizleague.web.core.IdComponent


object SingletonCompetitionPage extends RouteComponent{
  val template = """<competition :id="$route.params.id" ></competition>"""
  override val components = @@(SingletonCompetitionComponent)
}


object SingletonCompetitionComponent extends Component{
  
  type facade = IdComponent
  
  val name = "competition"
  
  val template = """ 
<div v-if="item">
    <ql-named-text :name="item.textName"></ql-named-text>
    <v-card>
     <v-card-text>
      <div><b>This season's competition will take place at <a :to="'/venue/' + item.event.venue.id">{{async(item.event.venue).name}}</a> on {{item.event.date | date("d MMMM yyyy")}} starting at {{item.event.time}}</b> </div>
      <br>
      <ql-text :id="item.text.id"></ql-text>
      </v-card-text>
    </v-card> 
  </div>"""
  
  override val props = @@("id")
  override val subParams = Map("id" -> "item")
 
  
  override val subscriptions = Map("item" -> (c => CompetitionService.get(c.id)))
      

  
}