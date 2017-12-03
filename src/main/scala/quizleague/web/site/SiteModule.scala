package quizleague.web.site

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.site.home.HomeModule
import quizleague.web.core._
import quizleague.web.site.team.TeamModule

import quizleague.web.store.Firestore
import io.circe._,io.circe.parser._,io.circe.syntax._,io.circe.scalajs.convertJsToJson
import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._
import rxscalajs.subjects.BehaviorSubject
import quizleague.domain.ApplicationContext
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.site.text.TextModule
import quizleague.web.site.venue.VenueModule
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.site.leaguetable.LeagueTableModule


object SiteModule extends Module {
  
  override val modules = @@(HomeModule, TeamModule, TextModule, VenueModule, FixturesModule, LeagueTableModule)
  
  override val routes = @@(RouteConfig(path = "",redirect = "/home"))
  
  override val components = @@(SiteComponent)
  
  val appData:Subject[ApplicationContext] = ReplaySubject()
  
   val db = Firestore.db
   
   val appConfig = db.doc("applicationcontext/5659313586569216")

  appConfig.onSnapshot(a => {
    
    if (a.exists){
      println(s"incoming ${a.data}")
          appData.next(decodeJson[ApplicationContext](convertJsToJson(a.data).merge.asInstanceOf[Json]).merge.asInstanceOf[ApplicationContext])

    }
     else println(s"no data")
  }, (e: java.lang.Error) => Unit, () => Unit)
     
     
     
   def decodeJson[T](obj:Json)(implicit dec:Decoder[T]) = dec.decodeJson(obj)
  
}