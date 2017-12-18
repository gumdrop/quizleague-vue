package quizleague.web.maintain.globaltext

import quizleague.web.service.globaltext.GlobalTextGetService
import quizleague.web.service.globaltext.GlobalTextPutService
import quizleague.web.maintain.text.TextService
import quizleague.web.core._
import quizleague.web.maintain.MaintainMenuComponent
import com.felstar.scalajs.vue.RouteConfig

object GlobalTextModule extends Module {
    override val routes = @@(
      RouteConfig(path = "/maintain/globaltext", components = Map("default" -> GlobalTextListComponent(), "sidenav" -> MaintainMenuComponent())),
      RouteConfig(path="/maintain/globaltext/:id", components = Map("default" -> GlobalTextComponent(), "sidenav" -> MaintainMenuComponent()))
       )

}

object GlobalTextService extends GlobalTextGetService with GlobalTextPutService{
  override val textService = TextService
}