package quizleague.web.core

import scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.JSConverters._
import com.felstar.scalajs.vue.Vue
import rxscalajs.facade.ObservableFacade
import rxscalajs.Observable
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject

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
  def subParams:Map[String,String] = Map()
  def subscriptions:Map[String, js.Dynamic => Observable[Any]] = Map()
  def data:js.Dynamic => Map[String, js.Any] = Map()
  def methods:Map[String,js.Any] = Map()
  
  def apply() = {
    
    def sw(fn:js.Dynamic => Observable[Any]) = update(ReplaySubject())(fn) _ 
    
    val subs =  subscriptions.map{case(k,v) => (k, sw(v))}
    
    val subwatches = subParams.map{case(k,v) => (k, subs(v):js.ThisFunction)}
    

    
    
    
    literal(
  
    template = template,
    props = props,
    watch = (watch.map{ case(k,v) => (k,v:js.ThisFunction)} ++ subwatches).toJSDictionary,
    subscriptions = ((c:js.Dynamic) => subs.map{case(k,v) => (k,v(c))}.toJSDictionary):js.ThisFunction,
    data = ((v:js.Dynamic) => data(v).toJSDictionary):js.ThisFunction,
    methods = methods.toJSDictionary
  )
  }
  
  def update(subject: Subject[Any])(fn:js.Dynamic => Observable[Any])(c:js.Dynamic) = {
      fn(c).subscribe(ve => subject.next(ve))
      subject.inner
  }
}