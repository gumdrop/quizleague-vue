package quizleague.web.site.text

import quizleague.web.core._
import quizleague.web.model._
import com.felstar.scalajs.vue.Vue
import scalajs.js
import scala.scalajs.js.annotation.JSExportAll

object TextComponent extends Component {
  
    override val name = "ql-text"
    
    override val template =  """
          <div v-if="text">
            <div v-if="text.mimeType=='text/html'" v-html="text.text"></div>
            <div v-if="text.mimeType=='text/plain'" v-text="text.text"></div>
          </div>"""
     override val props = @@("id")
     override val  subscriptions = Map("text" -> ((v:js.Dynamic) => TextService.get(v.id.toString)))


}