package quizleague.web.site.season

import quizleague.web.core._
import quizleague.web.model.Season
import scalajs.js
import quizleague.util.collection._
import quizleague.web.util.Logging._
import com.felstar.scalajs.vue.VueRxComponent
import rxscalajs.Subject

@js.native
trait SeasonSelectComponent extends VueRxComponent{
  val season:Subject[Season]
  val seasonId:String
}

object SeasonSelectComponent extends Component{
  type facade = SeasonSelectComponent
  val name = "ql-season-select"
  val template = """
   <h2 v-if="seasons && seasonId" >
  
    <v-select style="top:5px;"
    :items="wrap(sort(seasons))"
    v-model="seasonId"
    >
  </v-select>
  </h2>
"""
  
  override val props = @@("season")
  override val subscriptions = Map(
      "seasons" -> (c => SeasonService.list()), 
      "seasonId" -> (_.season.map(_.id)))
  override val methods = Map(
      "sort" -> ((seasons:js.Array[Season]) => seasons.sortBy(_.startYear)(Desc)),
      "wrap" -> ((seasons:js.Array[Season]) => seasons.map(s => new SelectWrapper(s"${s.startYear}/${s.endYear}", s.id )))    
  )
  override val watch = Map("seasonId" -> ((c:facade, newValue:js.Any) => if(newValue != js.undefined) SeasonService.get(c.seasonId).subscribe(s => c.season.next(s))))
  override val data = c => Map("seasonId" -> "")
  
  
}

class SelectWrapper(val text:String, val value:js.Any) extends js.Object