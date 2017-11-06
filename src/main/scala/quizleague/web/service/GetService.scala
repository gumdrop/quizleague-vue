package quizleague.web.service

import scala.scalajs.js
import scala.scalajs.js.JSConverters._


import quizleague.domain.{ Entity, Ref }
import quizleague.web.names.ComponentNames
import quizleague.web.util.Logging._
import io.circe.Error
import scala.collection.mutable._
import quizleague.web.util.rx._
import quizleague.web.store.Firestore
import rxjs._
import firebase.firebase.firestore._

trait GetService[T] {
  this: ComponentNames =>
  type U <: Entity

  val serviceRoot: String

  lazy val uriRoot = typeName

  val db = Firestore.db
  private[service] val items: Map[String, U] = Map()
  val requestOptions = js.Dynamic.literal()

  def get(id: String): Observable[T] = Observable.of(items.get _).switchMap((f,i) => f(id).fold[Observable[T]](getFromHttp(id).map((u, i) => mapOutSparse(u)))(mapOut(_)))
  def getRO(id:String):RefObservable[T] = RefObservable(id,get(id))

  def list(): Observable[js.Array[T]] = list(None)
    
  def flush() = items.clear()

  
  protected final def list(path:Option[String]):Observable[js.Array[T]] = {
    
    
    val subject = new BehaviorSubject(js.Array[T]())
    
    db.collection(uriRoot).onSnapshot(
        onNext = (q:QuerySnapshot) => subject.next(q.docs.map(d => dec(d.data()).merge.asInstanceOf[U]).map(x => mapOutSparse(x))),
        onError = (_) => Unit,
        onCompletion = () => Unit)
    
    subject
  }
    
  protected final def add(item: U) = { items += ((item.id, item)); mapOutSparse(item) }
  protected final def getFromHttp(id: String): Observable[U] = {
    
   val appData:BehaviorSubject[U] = new BehaviorSubject(null.asInstanceOf[U])
  
   val appConfig = db.doc(s"$uriRoot/$id")
   
   appConfig.onSnapshot((a:DocumentSnapshot) => {
     appData.next(dec(a.data()).merge.asInstanceOf[U])
     }, (e:java.lang.Error) => Unit, () => Unit)
    
     appData
     
  }
  
  protected[service] def postProcess(u:U):U = u
  
  protected def dec(json:js.Any):Either[Error,U]
  protected def decList(json:String):Either[Error,List[U]]

  private[service] def getDom(id: String) = items(id)

  final def refObs(id:String):RefObservable[T] = RefObservable(id, get(id))
  final def refObs(opt:Option[Ref[U]]):RefObservable[T] = opt.fold[RefObservable[T]](null)(ref => refObs[U,T](ref,this))
  protected final def refObs[A <: Entity, B](ref: Ref[A], service: GetService[B]):RefObservable[B] = RefObservable(ref, service.get(ref.id))
  protected final def refObsList[A <: Entity, B](refs:List[Ref[A]], service:GetService[B]):js.Array[RefObservable[B]] = refs.map(refObs(_,service)).toJSArray
  
  def ref(id:String):Ref[U] = Ref(typeName,id)
  def ref(list:js.Array[RefObservable[T]]):List[Ref[U]] = list.map(ref _).toList
  def ref(ro:RefObservable[T]):Ref[U] = if(ro == null) null else Ref(typeName,ro.id)
  def ref(dom:U):Ref[U] = ref(dom.id)
  
  protected final def mapOut(domain: U): Observable[T] = Observable.of(mapOutSparse(domain))
  protected def mapOutSparse(domain: U): T
  

  def wrap()
}


