package quizleague.web.site.calendar

import quizleague.web.site.season.SeasonWatchService

//import angulate2.std._
//import angular.material.MaterialModule
//import angulate2.platformBrowser.BrowserModule
//import angulate2.router.{ Route, RouterModule }
//
import scala.scalajs.js
import quizleague.web.model._
//import angulate2.http.Http
//import quizleague.web.service.EntityService
//import angular.flexlayout.FlexLayoutModule
//import quizleague.web.util.UUID
//import quizleague.web.names.ComponentNames
//
//import angulate2.ext.classModeScala
//import angulate2.common.CommonModule
import rxscalajs.Observable
import rxscalajs.Observable.combineLatest
//import quizleague.web.service.venue.VenueGetService
//import quizleague.web.site._
//import quizleague.web.site.common.CommonAppModule
//import quizleague.web.site.text.TextModule
//import quizleague.web.site.global.ApplicationContextService
//import quizleague.web.site.common.SeasonSelectService
//import quizleague.web.site.season.SeasonModule
//import quizleague.web.model.Season
//import quizleague.web.site.season.SeasonService
import org.threeten.bp.LocalDate
//import quizleague.web.model.Competition
//import quizleague.web.model.SingletonCompetition
import js.JSConverters._
import scala.scalajs.js.annotation.JSExportAll
//import quizleague.web.site.results.ResultsComponentsModule
//import quizleague.web.site.fixtures.FixturesComponentsModule
//import quizleague.web.model._
//import quizleague.web.site.competition.CompetitionService
import quizleague.web.util.rx._
import quizleague.web.site.season.SeasonService
import quizleague.web.site.competition._

//import scala.scalajs.js.WrappedArray
//import quizleague.web.util.Logging._
//
//@NgModule(
//  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, CommonAppModule, SeasonModule, CalendarRoutesModule, ResultsComponentsModule, FixturesComponentsModule],
//  declarations = @@[CalendarComponent, CalendarTitleComponent, ResultsEventComponent, FixturesEventComponent, CalendarEventComponent, CompetitionEventComponent],
//  providers = @@[CalendarViewService])
//class CalendarModule
//
//@Routes(
//  root = false,
//  Route(
//    path = "calendar",
//    children = @@@(
//      Route("", component = %%[CalendarComponent]),
//      Route("", component = %%[CalendarTitleComponent], outlet = "title")
//    )))
//class CalendarRoutesModule


object CalendarViewService extends SeasonWatchService{
  
  def getEvents(seasonId:String) = {
    
    import CompetitionType._
    
    val now = LocalDate.now.toString
    
    val season = SeasonService.get(seasonId)
        
    def singletonEvents(c:Competition):js.Array[EventWrapper] = c match {
      case s:SingletonCompetition => js.Array(EventWrapper(s.event,c))
      case _ => js.Array()
    }
    
    def flatten[T](obs:Observable[js.Array[Observable[js.Array[T]]]]):Observable[js.Array[T]] = obs.flatMap(e => combineLatest(e.toSeq).map(a => a.flatten.toJSArray))//.concatAll()
    
    val comps = CompetitionService.firstClassCompetitions(seasonId)

   
    val fixtures:Observable[js.Array[EventWrapper]] = comps.flatMap(cs => cs.map(c => combineLatest(c.fixtures.map(_.obs).toSeq)).map(f => f.map(EventWrapper(_,c))))

    val singletons = comps.map(cs => cs.flatMap(singletonEvents _))
    
    val seasons = season.map(s => s.calendar.map(e => EventWrapper(e)))

    val  res = Observable.zip(Seq(
        fixtures,singletons,seasons))(lists =>  { 
          val ret = lists.flatMap(_.toSeq).toJSArray
          .groupBy(_.date)
          .toIterable
          .map(t => new DateWrapper(t._1, t._2))
          .toJSArray
          .sort((d1:DateWrapper,d2:DateWrapper) => d1.date compareTo d2.date)
          ret
        }      
    )  
    res

  }
  
}

@JSExportAll
class DateWrapper(val date:String, val events:js.Array[EventWrapper])

