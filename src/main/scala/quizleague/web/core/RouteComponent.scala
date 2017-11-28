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
  def watch:Map[String, Vue => Unit] = Map()
  def subscriptions:Vue => Map[String, js.Any] = Map()
  def data:Vue => Map[String, js.Any] = Map()
  def methods:Map[String,js.Any] = Map()
  
  def apply() = {
    
    val w = watch.map{ case(k,v) => (k,v:js.ThisFunction)}.toJSDictionary
    val s = ((v:Vue) => subscriptions(v).toJSDictionary):js.ThisFunction
    val d = ((v:Vue) => data(v).toJSDictionary):js.ThisFunction
    
    literal(
  
    template = template,
    props = props,
    watch = watch.map{ case(k,v) => (k,v:js.ThisFunction)}.toJSDictionary,
    subscriptions = ((v:Vue) => subscriptions(v).toJSDictionary):js.ThisFunction,
    data = ((v:Vue) => data(v).toJSDictionary):js.ThisFunction,
    methods = methods.toJSDictionary
  )
  }
}