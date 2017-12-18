package quizleague.web.maintain.component

import com.felstar.scalajs.vue.VueRxComponent
import scalajs.js
import quizleague.web.service.PutService
import quizleague.web.core.IdComponent
import quizleague.web.service.GetService
import quizleague.web.core.Component
import quizleague.web.model.Model
import quizleague.web.names.ComponentNames
import rxscalajs.Observable

@js.native
trait ItemComponent[T] extends VueRxComponent{
  val valid:Boolean
  val item:T
}

trait ItemComponentConfig[T <: Model] extends Component{
  type facade = ItemComponent[T]
  
  val paramName = "id"
  
  val service:GetService[T] with PutService[T]
  
  override def subscriptions:Map[String, facade => Observable[Any]] = Map("item" -> (c => service.get(c.$route.params(paramName))))
  
  override val methods = Map(
      "save" -> ({(c:facade) => {service.save(c.item);c.$router.back()}}:js.ThisFunction),
      "cancel" -> ({c:facade => c.$router.back()}:js.ThisFunction)  
      
  )
  
  override val data = c => Map("valid" -> false)
}

@js.native
trait ItemListComponent[T <: Model] extends VueRxComponent{
  val valid:Boolean
  val item:T
}


trait ItemListComponentConfig[T <: Model] extends Component{

  this:ComponentNames =>
    
  type facade = ItemListComponent[T]
  
  val service:GetService[T] with PutService[T]
  override val subscriptions = Map("items" -> (c => service.list()))
  override val methods = Map("add" -> ({(c:facade) => {
    val i = service.instance()
    c.$router.push(s"$typeName/${i.id}")}}:js.ThisFunction))
}