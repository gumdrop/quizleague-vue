package quizleague.web.core

import scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.JSConverters._
import com.felstar.scalajs.vue.Vue
import rxscalajs.facade.ObservableFacade
import rxscalajs.Observable
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.util.rx.RefObservable
import scala.scalajs.js.UndefOr

trait RouteComponent extends Component{

  val name = ""

}


trait Component {

  val name: String
  def template: String
  def props: js.Array[String] = @@()
  def watch: Map[String, js.Dynamic => Unit] = Map()
  def subParams: Map[String, String] = Map()
  def subscriptions: Map[String, js.Dynamic => Observable[Any]] = Map()
  def data: js.Dynamic => Map[String, js.Any] = c => Map()
  def methods: Map[String, js.Function] = Map()

  var observables = js.Dictionary[js.Dictionary[Any]]() 
  val empty = new js.Object
  
  private val commonMethods:Map[String, js.Function] = Map("async" -> (((c:js.Dynamic, in:UndefOr[RefObservable[js.Dynamic]]) => {
    
    println("ql-web : entering async")
    if(in.isDefined){
    
    val obs = in.get
    
    val retval = observables.get(obs.id)
    
    println(s"ql-web : retval : $retval")
    
    //retval.foreach(v => {observables = observables + (obs,v)})
    
    def sub() = {
      println("ql-web : entering sub")
      val a = js.Dictionary[Any]()
      c.$subscribeTo(obs.inner, (b:js.Dynamic) => {val r:js.Any = Vue.util.extend(a,b);c.$forceUpdate()})
      observables += ((obs.id,a))
      a
    }
    
    if(retval.isEmpty) sub() else retval.get
    }
    else empty

  }):js.ThisFunction))
  

  
  def apply() = {

    def update(subject: Subject[Any])(fn: js.Dynamic => Observable[Any])(c: js.Dynamic) = {
      c.$subscribeTo(fn(c).inner, (ve:Any) => subject.next(ve))
      subject.inner
    }

    def sw(fn: js.Dynamic => Observable[Any]) = update(ReplaySubject())(fn) _

    val subs = subscriptions.map { case (k, v) => (k, sw(v)) }

    val subwatches = subParams.map { case (k, v) => (k, subs(v): js.ThisFunction) }

    literal(
      template = template,
      props = props,
      watch = (watch.map { case (k, v) => (k, v: js.ThisFunction) } ++ subwatches).toJSDictionary,
      subscriptions = ((c: js.Dynamic) => subs.map { case (k, v) => (k, v(c)) }.toJSDictionary): js.ThisFunction,
      data = ((v: js.Dynamic) => data(v).toJSDictionary): js.ThisFunction,
      methods = (commonMethods ++ methods).toJSDictionary
      
    )
  }

}