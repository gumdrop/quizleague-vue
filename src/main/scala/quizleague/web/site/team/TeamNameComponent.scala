package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.IdComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.util.rx.RefObservable
import quizleague.web.model.Team
import scalajs.js

@js.native
trait TeamNameComponent extends IdComponent{
  val team:RefObservable[Team]
}

object TeamNameComponent extends Component{
  
  type facade = TeamNameComponent
  val name = "ql-team-name"
  val template = """<span v-if="t">{{short ? t.shortName : t.name}}</span>"""
  override val props = @@("team","short","id")
  override val subscriptions = Map("t" -> (c => if(c.team != null) c.team.obs else TeamService.get(c.id)))
  override val subParams = Map("team"->"t", "id" -> "t")
  
}