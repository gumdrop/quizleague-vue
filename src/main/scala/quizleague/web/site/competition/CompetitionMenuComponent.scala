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
        <ql-competition-menu></ql-competition-menu>
  """
  
  components(CompetitionMenuComponent)
}

object CompetitionMenuComponent extends Component{
  type facade = SeasonIdComponent
  val name = "ql-competition-menu"
  val template = """
    <v-list dense v-if="competitions">
     <v-list-group no-action :value="true">
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>mdi-trophy</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>Competitions</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>
       <v-list-tile v-for="competition in sort(competitions)" :key="competition.id" left>
          <v-btn :to="'/competition/' + competition.id +'/' + competition.typeName" flat left>{{competition.name}}</v-btn>
      </v-list-tile>
    </v-list-group>
    </v-list>
    """

  subscription("competitions")(c => CompetitionViewService.competitions())
  method("sort")((comps:js.Array[Competition]) => comps.sortBy(_.name))
}