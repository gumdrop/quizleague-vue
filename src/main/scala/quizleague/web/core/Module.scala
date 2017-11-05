package quizleague.web.core

import com.felstar.scalajs.vue._
import scalajs.js._

trait Module {
  def modules:Array[Module] = Array()
  def routes:Array[Any] = Array()
  def components:Array[PageComponent] = Array()
  
  def apply():Array[Any] = {
    components.foreach(_())  
    modules.flatMap(_()) ++ routes
  }
}