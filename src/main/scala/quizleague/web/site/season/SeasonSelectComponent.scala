package quizleague.web.site.season

import quizleague.web.core._
import quizleague.web.model.Season
import scalajs.js
import quizleague.util.collection._
import quizleague.web.util.Logging._
import com.felstar.scalajs.vue.VueRxComponent

@js.native
trait SeasonSelectComponent extends VueRxComponent{
  val season:Season
}

object SeasonSelectComponent extends Component{
  type facade = SeasonSelectComponent
  val name = "ql-season-select"
  val template = """
   <v-select
    :items="wrap(sort(seasons))"
    :v-model="season"
    >
  </v-select>
"""
  
  override val props = @@("season")
  override val subscriptions = Map("seasons" -> (c => SeasonService.list()))
  override val methods = Map(
      "sort" -> ((seasons:js.Array[Season]) => seasons.sortBy(_.startYear)(Desc)),
      "wrap" -> ((seasons:js.Array[Season]) => seasons.map(s => new SelectWrapper(s"${s.startYear} / ${s.endYear}", s )))    
  )
  override val watch = Map("season" -> (c => log(c.season, "ql-web : season")) )
  
  
}

class SelectWrapper(val text:String, val value:js.Any) extends js.Object