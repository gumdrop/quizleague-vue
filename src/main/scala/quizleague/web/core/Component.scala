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
  def watch: Map[String, ((facade,js.Any) => Unit)] = Map()
  def subParams: Map[String, String] = Map()
  def subscriptions: Map[String, facade => Observable[Any]] = Map()
  def data: facade => Map[String, Any] = c => Map()
  def methods: Map[String, js.Function] = Map()
  def components: js.Array[Component] = @@()
  def computed: Map[String, js.Function] = Map()
  def mounted: js.Function = null
  def activated:js.Function = null
  def created:js.Function = null
  def beforeCreate:js.Function = null

  var observables = js.Dictionary[js.Dictionary[Any]]() 
  val empty = new js.Object
  
  private val commonMethods:Map[String, js.Function] = Map("async" -> (((c:facade, in:UndefOr[RefObservable[js.Dynamic]]) => {
    
    println("ql-web : entering async")
    if(in.isDefined){
    
    val obs = in.get
    
    val retval = observables.get(obs.id)
    
    
    def sub() = {
      val a = js.Dictionary[Any]()
      c.$subscribeTo(obs.inner, (b:js.Dynamic) => {val r:js.Any = Vue.util.extend(a,b);c.$forceUpdate()})
      observables += ((obs.id,a))
      a
    }
    
    if(retval.isEmpty) sub() else retval.get
    }
    else empty

  }):js.ThisFunction))
  

  
  def apply():js.Dynamic = {

    def update(subject: Subject[Any])(fn: facade => Observable[Any])(c: facade) = {
       c.$subscribeTo(
          fn(c).inner, 
          subject.inner)
      subject.inner
    }

    
    def makeSubscriptions(c:facade) = {

      val watchSubs = subParams.map{case (k, v) => {
        val subject = ReplaySubject[Any]()
        
        val subscription = update(subject)(subscriptions(v)) _
        val watch = c.$watchAsObservable(k).subscribe( x=> subscription(c))
        
        (v, subscription)
        
      }}
      
      val subs = subscriptions.filterKeys(k => !subParams.values.exists(_ == k)).map{
        case (k,v) => ((k, (c:facade) => v(c).inner))
      }
      
      watchSubs ++ subs
      
    }
    

    
    val retval = literal(
      template = template,
      props = props,
      data = ((v: facade) => data(v).toJSDictionary): js.ThisFunction,
      watch = (watch.map { case (k, v) => (k, v: js.ThisFunction) }).toJSDictionary,
      subscriptions = ((c: facade) => makeSubscriptions(c).map { case (k, v) => (k, v(c)) }.toJSDictionary): js.ThisFunction,

      methods = (commonMethods ++ methods).toJSDictionary,
      computed = computed.toJSDictionary,
      components = components.map(c => ((c.name, c()))).toMap.toJSDictionary,
      mounted = mounted,
      activated = activated,
      created = created,
      beforeCreate = beforeCreate
           
    )
    
    retval
  }

}

@js.native
trait IdComponent extends VueComponent with VueRxComponent{
  val id:String = js.native  
}
