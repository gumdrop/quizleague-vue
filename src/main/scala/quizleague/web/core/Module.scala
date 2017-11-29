package quizleague.web.core

import scalajs.js._
import com.felstar.scalajs.vue.Vue

trait Module {
  def modules:Array[Module] = Array()
  def routes:Array[Any] = Array()
  def components:Array[Component] = Array()
  
  def apply():Array[Any] = {
    components.foreach(c => Vue.component(c.name, c()))
    modules.flatMap(_()) ++ routes
  }
}