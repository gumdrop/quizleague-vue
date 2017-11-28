package quizleague.web.core

import com.felstar.scalajs.vue._
import scalajs.js._
import quizleague.web.core.Component
import com.felstar.scalajs.vue.Vue

trait Module {
  def modules:Array[Module] = Array()
  def routes:Array[Any] = Array()
  def components:Array[PageComponent] = Array()
  def newComponents:Array[Component] = Array()
  
  def apply():Array[Any] = {
    components.foreach(_()) 
    newComponents.foreach(c => Vue.component(c.name, c()))
    modules.flatMap(_()) ++ routes
  }
}