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

  def subscribe(onNext:Function1[T,Unit],onError:Function1[scala.scalajs.js.Any,Unit],onComplete:Function0[Unit]) = inner.subscribe(onNext, onError, onComplete)
  
  def toJSON() = js.Dynamic.literal(("id",id))

}

object RefObservable {

  def apply[T](ref: Ref[_], obs: Observable[T]) = new RefObservable(ref.id, () => obs)
  def apply[T](id: String, obs: Observable[T]) = new RefObservable(id, () => obs)
  def apply[T](ref: Ref[_], obsf: () => Observable[T]) = new RefObservable(ref.id, obsf)
  def apply[T](id: String, obsf: () => Observable[T]) = new RefObservable(id, obsf)
}