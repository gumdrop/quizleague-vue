package quizleague.web.maintain.component

import scalajs.js
import quizleague.web.util.rx.RefObservable
import quizleague.web.service.GetService
import rxscalajs.Observable
import quizleague.web.model.Model

object SelectUtils {
  def model[T <: Model](service:GetService[T])(nameMaker: T => String):Observable[js.Array[SelectWrapper[T]]] = service.list.map(_.map(o => new SelectWrapper(nameMaker(o),service.refObs(o.id))))
}

class SelectWrapper[T](val text:String, val value:RefObservable[T]) extends js.Object{
  override def equals(in:Any) = in != null && in.isInstanceOf[SelectWrapper[T]] && in.asInstanceOf[SelectWrapper[T]].value.id == value.id
}