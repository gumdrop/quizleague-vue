package quizleague.web.site

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.site.home.HomeModule
import quizleague.web.core.Module
import quizleague.web.site.team.TeamModule
import firebase.firebase._
import firebase.firebase.firestore._
import rxscalajs.subjects.BehaviorSubject
import rxscalajs.Observable

object SiteModule extends Module {
  
  override val modules = js.Array(HomeModule, TeamModule)
  
  override val routes = js.Array(RouteConfig(path = "",redirect = "/home"))
  
  val appData:BehaviorSubject[AppData] = BehaviorSubject(AppData(""))
  
  val config = literal(      
      apiKey = "AIzaSyDrTlMf77UN7iUVCLoyOwRTgJPgHMXRT1k",
      authDomain = "quizleague-d02fe.firebaseapp.com",
      databaseURL = "https://quizleague-d02fe.firebaseio.com",
      projectId = "quizleague-d02fe",
      storageBucket = "quizleague-d02fe.appspot.com",
      messagingSenderId = "127835213248")
  
   Firebase.initializeApp(config)
      
   val db = Firebase.firestore()
   
   val appConfig = db.doc("ApplicationConfig/LyIBn4rYvEbYjtBHW72x")
   
   appConfig.get().then((a) => {
     val b = a.asInstanceOf[DocumentSnapshot].data();
     println(s"b as json : ${js.JSON.stringify(b.asInstanceOf[js.Any])}")
     appData.next(AppData(a.asInstanceOf[DocumentSnapshot].get("title").toString))
     }, (t) => "")
  
}