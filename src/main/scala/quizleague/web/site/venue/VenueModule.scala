package quizleague.web.site.venue



import scala.scalajs.js
import quizleague.web.model.Venue

import quizleague.web.names.ComponentNames


import quizleague.web.service.venue.VenueGetService
import quizleague.web.site._
import quizleague.web.site.text.TextModule
import quizleague.web.core.Module
import com.felstar.scalajs.vue.RouteConfig
import quizleague.web.core._

//@NgModule(
//  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, VenueRoutesModule, CommonAppModule, TextModule],
//  declarations = @@[VenueComponent, VenueTitleComponent, VenueMenuComponent, VenuesComponent, VenuesTitleComponent],
//  providers = @@[VenueService])
//class VenueModule
//
//@Routes(
//  root = false,
//  Route(
//    path = "venue",
//    children = @@@(
//      Route(path = ":id", children = @@@(
//        Route(path = "", children = @@@(
//          Route(path = "", component = %%[VenueComponent]),
//          Route(path = "", component = %%[VenueTitleComponent], outlet = "title"))))),
//      Route("", children = @@@(
//        Route(path = "", component = %%[VenuesComponent]),
//        Route(path = "", component = %%[VenuesTitleComponent], outlet = "title"))),
//      Route(path = "", component = %%[VenueMenuComponent], outlet = "sidemenu"))))
//class VenueRoutesModule

object VenueModule extends Module{

  override val components = @@(VenueComponent,VenueTitle)

  override val routes = @@(
      RouteConfig(path = "/venue", 
          components = Map("default" -> VenuesComponent(), "title" -> VenuesTitleComponent(),"sidenav" -> VenueMenuComponent())),
      RouteConfig(path = "/venue/:id", 
          components = Map("default" -> VenuePage(), "title" -> VenueTitleComponent(),"sidenav" -> VenueMenuComponent())))

      
   
}


object VenueService extends VenueGetService

