package quizleague.web.core

import scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.JSConverters._
import com.felstar.scalajs.vue.Vue
import rxscalajs.facade.ObservableFacade

trait RouteComponent {
  
  def component:js.Dynamic
  
  def apply() = component
  
}

trait PageComponent{
  def apply():js.Any
}

trait Component{
  val name:String
  def template:String
  def props:js.Array[String] = @@()
  def watch:Map[String, js.Dynamic => Unit] = Map()
  def subscriptions:js.Dynamic => Map[String, js.Any] = Map()
  def data:js.Dynamic => Map[String, js.Any] = Map()
  def methods:Map[String,js.Any] = Map()
  
  def apply() = {
    
   
    literal(
  
    template = template,
    props = props,
    watch = watch.map{ case(k,v) => (k,v:js.ThisFunction)}.toJSDictionary,
    subscriptions = ((v:js.Dynamic) => subscriptions(v).toJSDictionary):js.ThisFunction,
    data = ((v:js.Dynamic) => data(v).toJSDictionary):js.ThisFunction,
    methods = methods.toJSDictionary
  )
  }
}