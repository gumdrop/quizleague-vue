package quizleague.web.util.rx

import quizleague.domain.Ref
import scala.scalajs.js
import scala.scalajs.js.annotation._
import quizleague.web.util.Logging._
import rxscalajs.Observable
import rxscalajs.subscription.Subscription
import rxscalajs.facade.ObservableFacade

class RefObservable[+T](val id: String, obsf: () => Observable[T]) extends js.Object{
 
  def obs = obsf()
  def inner = obs.inner
   
  @JSName("subscribeScala")
  def subscribe(f: T => Unit) = obs.subscribe(f, (x) => Unit, () => Unit)

  def subscribe(onNext:T => Unit,onError:js.Any => Unit,onComplete:() => Unit) = inner.subscribe(onNext, onError, onComplete)
  
  def toJSON() = js.Dynamic.literal(("id",id))

}

object RefObservable {

  def apply[T](ref: Ref[_], obsf: () => Observable[T]) = new RefObservable(ref.id, obsf)
  def apply[T](id: String, obsf: () => Observable[T]) = new RefObservable(id, obsf)
}