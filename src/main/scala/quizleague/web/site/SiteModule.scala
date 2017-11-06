package quizleague.web.site

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.site.home.HomeModule
import quizleague.web.core.Module
import quizleague.web.site.team.TeamModule

import quizleague.web.store.Firestore
import firebase.firebase.firestore._
import rxjs.BehaviorSubject

object SiteModule extends Module {
  
  override val modules = js.Array(HomeModule, TeamModule)
  
  override val routes = js.Array(RouteConfig(path = "",redirect = "/home"))
  
  val appData:BehaviorSubject[AppData] = new BehaviorSubject(AppData(""))
  
   val db = Firestore.db
   
   val appConfig = db.doc("ApplicationConfig/LyIBn4rYvEbYjtBHW72x")
   
   appConfig.onSnapshot((a:DocumentSnapshot) => {
     appData.next(AppData(a.data()("title").toString))
     }, (e:Error) => Unit, () => Unit)
  
}