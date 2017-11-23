package quizleague.web.site.text

import quizleague.web.core._
import quizleague.web.model._
import scalajs.js.Dynamic.literal
import quizleague.web.core.PageComponent
import com.felstar.scalajs.vue.Vue
import scalajs.js
import scala.scalajs.js.annotation.JSExportAll

object TextComponent extends PageComponent {



  override def apply() = {
    
    
    
    val vue = Vue.component("ql-text", literal(
        template =  """
          <div v-if="text">
            <div v-if="text.mimeType=='text/html'" v-html="text.text"></div>
            <div v-if="text.mimeType=='text/plain'" v-text="text.text"></div>
          </div>""",
        props = @@("id"),
        subscriptions = ((v:js.Dynamic)=> literal(text = TextService.get(v.id.toString).inner)):js.ThisFunction,
        watch = literal(id = (oldv:String, newv:String) => println(s"old = $oldv; new = $newv"))
        
    ))
    //vue.asInstanceOf[js.Dynamic].$subscriptions.text = TextService.get(vue.asInstanceOf[js.Dynamic].id.toString).inner
    //vue.asInstanceOf[Vue].$watch("id", (oldv, newv) => println(s"old = $oldv; new = $newv"))
    
    vue

  }
}