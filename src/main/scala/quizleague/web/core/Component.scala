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
import com.felstar.scalajs.vue.VueComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.util.Logging._

trait RouteComponent extends Component{

  val name = ""

}


trait Component {

  type facade <: VueComponent with VueRxComponent
  
  val name: String
  def template: String
  def props: js.Array[String] = @@()
  def watch: Map[String, facade => Unit] = Map()
  def subParams: Map[String, String] = Map()
  def subscriptions: Map[String, facade => Observable[Any]] = Map()
  def data: facade => Map[String, js.Any] = c => Map()
  def methods: Map[String, js.Function] = Map()

  var observables = js.Dictionary[js.Dictionary[Any]]() 
  val empty = new js.Object
  
  private val commonMethods:Map[String, js.Function] = Map("async" -> (((c:facade, in:UndefOr[RefObservable[js.Dynamic]]) => {
    
    println("ql-web : entering async")
    if(in.isDefined){
    
    val obs = in.get
    
    val retval = observables.get(obs.id)
    
    
    def sub() = {
      val a = js.Dictionary[Any]()
      c.asInstanceOf[js.Dynamic].$subscribeTo(obs.inner, (b:js.Dynamic) => {val r:js.Any = Vue.util.extend(a,b);c.$forceUpdate()})
      observables += ((obs.id,a))
      a
    }
    
    if(retval.isEmpty) sub() else retval.get
    }
    else empty

  }):js.ThisFunction))
  

  
  def apply() = {

    def update(subject: Subject[Any])(fn: facade => Observable[Any])(c: facade) = {
      c.asInstanceOf[js.Dynamic]
      .$subscribeTo(
          fn(c).inner, 
          (ve:Any) => subject.next(ve))
      subject.inner
    }

    def sw(fn: facade => Observable[Any]) = update(ReplaySubject())(fn) _

    val subs = subscriptions.map { case (k, v) => (k, sw(v)) }

    val subwatches = subParams.map { case (k, v) => (k, subs(v): js.ThisFunction) }
    
    val retval = literal(
      template = template,
      props = props,
      watch = (watch.map { case (k, v) => (k, v: js.ThisFunction) } ++ subwatches).toJSDictionary,
      subscriptions = ((c: facade) => subs.map { case (k, v) => (k, v(c)) }.toJSDictionary): js.ThisFunction,
      data = ((v: facade) => data(v).toJSDictionary): js.ThisFunction,
      methods = (commonMethods ++ methods).toJSDictionary
      
    )
    
    retval
  }

}

@js.native
trait IdComponent extends VueComponent with VueRxComponent{
  val id:String = js.native  
}

