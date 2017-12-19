package quizleague.web.maintain.season

import quizleague.web.service.season.SeasonGetService
import quizleague.web.service.season.SeasonPutService
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.core._
import com.felstar.scalajs.vue.RouteConfig
import quizleague.web.maintain.MaintainMenuComponent

object SeasonModule extends Module {
  override val routes = @@(     
//      RouteConfig(
//        path = "/maintain/season/:id/calendar",
//        //component = %%[CalendarComponent]
//      ),
      RouteConfig(
        path = "/maintain/season/:id",
        components = Map("default" -> SeasonComponent(), "sidenav" -> MaintainMenuComponent())
      ),
      RouteConfig(
        path = "/maintain/season",
        components = Map("default" -> SeasonListComponent(), "sidenav" -> MaintainMenuComponent())
      ))
}

object SeasonService extends SeasonGetService with SeasonPutService{
  override val competitionService = CompetitionService
  override val textService = TextService
  override val venueService = VenueService
}