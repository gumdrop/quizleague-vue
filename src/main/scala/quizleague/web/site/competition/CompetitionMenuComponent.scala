package quizleague.web.site.competition

import quizleague.web.site.ApplicationContextService
import quizleague.web.core._
import quizleague.web.site.season.SeasonIdComponent
import scalajs.js
import js.JSConverters._
import quizleague.web.model.Competition


//@Component(
//  template = s"""
//  <div fxLayout="column" *ngFor="let competition of competitions | async">
//    <a fxFlexAlign="start" routerLink="/competition/{{competition.id}}/{{competition.typeName}}"  md-menu-item routerLinkActive="active" >{{competition.name}}</a>
//  </div>
//  """    
//)
//class CompetitionMenuComponent(
//    service:CompetitionService,
//    viewService:CompetitionViewService,
//    seasonService:SeasonService){
//  
//  val competitions = viewService.season.switchMap((s,i) => seasonService.get(s.id)).map((s,i) => sort[Competition](s.competitions,(c1,c2) => c1.name compareTo c2.name)).concatAll()
//
//}


object CompetitionMenu extends RouteComponent{
  val template = """
      <div>
        <ql-competition-menu v-if="appData" :seasonId="appData.currentSeason.id"></ql-competition-menu>
      </div>"""
    override val subscriptions = Map("appData" -> (c => ApplicationContextService.get))
}

object CompetitionMenuComponent extends Component{
  type facade = SeasonIdComponent
  val name = "ql-competition-menu"
  val template = """
    <v-list dense v-if="competitions">
       <v-list-tile v-for="competition in sort(competitions)" :key="competition.id">
          <v-btn :to="'/competition/' + competition.id +'/' + competition.typeName" flat style="text-transform: none;">{{competition.name}}</v-btn>
      </v-list-tile>
    </v-list>
    """
  override val props = @@("seasonId")
  override val subParams = Map("seasonId" -> "competitions")
  override val subscriptions = Map("competitions" -> (c => CompetitionService.competitions(c.seasonId)))
  override val methods = Map("sort" -> ((comps:js.Array[Competition]) => comps.filter(_ != null).sortBy(_.name)))
}