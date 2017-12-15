package quizleague.web.site.calendar

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import quizleague.web.core._
import quizleague.web.site.season.SeasonIdComponent
import com.felstar.scalajs.vue.VueRxComponent

//import angulate2.ext.classModeScala
//import angulate2.router.ActivatedRoute
//import angulate2.std.{ @@@, Component, Data, Injectable }
//import quizleague.web.site.common.{ NoMenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
//import quizleague.web.util.Logging.log
//import angulate2.core.Input
//import quizleague.web.site.common.ComponentUtils
//import ComponentUtils._
//

object CalendarPage extends RouteComponent{
  
  val template = """<ql-calendar></ql-calendar>"""
  override val components = @@(CalendarComponent)
}


object CalendarComponent extends Component{
  val name = "ql-calendar" 
  val template = """
  <v-container grid-list-xl v-if="items" class="ql-calendar">
    <v-layout column>
      <v-card v-for="item in items" :key="item.date" class="mb-3">
         <v-card-title primary-title><h3 class="headline mb-0">{{item.date | date("EEEE d MMMM yyyy")}}</h3></v-card-title>
         <v-card-text>
          <div v-for="event in item.events">
              <ql-fixtures-event v-if="event.eventType === 'fixtures'" :event="event"></ql-fixtures-event>
              <ql-calendar-event v-if="event.eventType === 'calendar'" :event="event"></ql-calendar-event>
              <ql-competition-event v-if="event.eventType === 'competition'" :event="event"></ql-competition-event>
          </div>
         </v-card-text>
      </v-card>
    </v-layout>
  </v-container>"""
  override val subscriptions = Map("items" -> (c => CalendarViewService.events), "season" -> (c => CalendarViewService.season))
  override val components = @@(FixturesEventComponent,CalendarEventComponent,CompetitionEventComponent)
  
}

object CalendarTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="yellow darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" >
        Calendar
      </v-toolbar-title>
      &nbsp;<h3><ql-season-select :season="season"></ql-season-select></h3>
    </v-toolbar>"""
  
  override val data = c => Map("season" -> CalendarViewService.season)
}

//@Component(
//  template = s"""
//  <div *ngIf="itemObs | async as items ; else loading" fxLayout="column" fxLayoutGap="10px">
//  <md-card *ngFor="let item of items">
//     <md-card-title>{{item.date | date:"EEEE d MMMM yyyy"}}</md-card-title>
//     <md-card-content>
//      <div *ngFor="let event of item.events">
//        <div [ngSwitch]="event.eventType">
//          <ql-results-event *ngSwitchCase="'results'" [event]="event"></ql-results-event>
//          <ql-fixtures-event *ngSwitchCase="'fixtures'" [event]="event"></ql-fixtures-event>
//          <ql-calendar-event *ngSwitchCase="'calendar'" [event]="event"></ql-calendar-event>
//          <ql-competition-event *ngSwitchCase="'competition'" [event]="event"></ql-competition-event>
//        </div>
//      </div>
//     </md-card-content>
//  </md-card>
// 
//  </div>
//  $loadingTemplate
//""")
//@classModeScala
//class CalendarComponent(
//    service: CalendarViewService,
//    override val titleService: TitleService,
//    override val sideMenuService: SideMenuService) extends SectionComponent with NoMenuComponent with TitledComponent {
//
//  setTitle("Calendar")
//
//  val itemObs = service.season.switchMap((s, i) => service.getEvents(s))
//
//}
//
//@Component(
//  template = """
//  <ql-section-title>
//    Calendar <ql-season-select [currentSeason]="service.season | async" (onchange)="service.seasonChanged($event)"></ql-season-select>
//  </ql-section-title>
//""")
//class CalendarTitleComponent(
//  route: ActivatedRoute,
//  val service: CalendarViewService)
//  
//
@js.native
trait EventComponent extends VueRxComponent{
  
  val event:EventWrapper
}
  
  
object PanelComponent {
  val buttonStyle = "top:-12px;"
}


@js.native
trait PanelComponent extends EventComponent{

  var panelVisible:Boolean

}
//
//
//
//
//@Component(
//  selector = "ql-results-event",
//  template = """
//         <div fxLayout="column">
//            <div fxLayout="row">
//            <div><a routerLink="/competition/{{event.competition.id}}/{{event.competition.typeName}}">{{(event.results.fixtures | async)?.parentDescription}}</a> {{(event.results.fixtures | async)?.description}}</div>
//            <button class="fixButPos" md-icon-button (click)="togglePanel()">
//              <md-icon *ngIf="!panelVisible" class="md-24" mdTooltip="Show results">visibility</md-icon>
//              <md-icon *ngIf="panelVisible" class="md-24" mdTooltip="Hide results">visibility_off</md-icon>
//            </button>
//            </div>  
//          <div *ngIf="panelVisible"><ql-results-simple [list]="event.results.results"></ql-results-simple></div>
//          </div>
//
//""",
//  styles = @@@(buttonStyle),
//  inputs = @@@("event")
//)
//@classModeScala
//class ResultsEventComponent extends PanelComponent

trait EventComponentConfig extends Component{
   
   type facade = PanelComponent
   override val data = c => Map("panelVisible" -> false)
   override val props = @@("event")
   override val methods = Map("togglePanel" -> ({c:facade => c.panelVisible = !c.panelVisible}:js.ThisFunction))
}



object FixturesEventComponent extends EventComponentConfig{
  
  val name = "ql-fixtures-event"
   val template = s"""         
    <v-container grid-list-xl class="panel-component">
      <v-layout column>
        <v-layout row>
          <div><a :href="'/competition/' + event.competition.id + '/' + event.competition.typeName">{{event.fixtures.description}}</a></div>
            <v-btn icon v-on:click="togglePanel">
             <v-icon v-if="!panelVisible">visibility</v-icon>
             <v-icon v-if="panelVisible">visibility_off</v-icon>
            </v-btn>
          </div> 
        </v-layout> 
        <div v-if="panelVisible"><ql-fixtures-simple :fixtures="event.fixtures.fixtures | combine"></ql-fixtures-simple></div>
      </v-layout>
    </v-container>
"""

}

object CalendarEventComponent extends EventComponentConfig{
  
  val name = "ql-calendar-event"
  val template = """<div><b>{{event.event.description}}</b>  {{event.event.time}}  Venue : <a :href="'/venue/' + event.event.venue.id">{{async(event.event.venue).name}}</a></div>"""

}

object CompetitionEventComponent extends EventComponentConfig{
  
  val name = "ql-competition-event"
  val template = """<div><a :href="['/competition',event.competition.id,event.competition.typeName]">{{event.competition.name}}</a>  {{event.event.time}}  Venue : <a :href="'/venue/' + event.event.venue.id">{{async(event.event.venue).name}}</a></div>"""

}

//
//@Component(
//  selector = "ql-fixtures-event",
//  template = """
//         <div fxLayout="column">
//            <div fxLayout="row">
//            <div><a routerLink="/competition/{{event.competition.id}}/{{event.competition.typeName}}">{{event.fixtures.parentDescription}}</a> {{event.fixtures.description}}</div>
//            <button class="fixButPos" md-icon-button (click)="togglePanel()">
//              <md-icon *ngIf="!panelVisible" class="md-24" mdTooltip="Show fixtures">visibility</md-icon>
//              <md-icon *ngIf="panelVisible" class="md-24" mdTooltip="Hide fixtures">visibility_off</md-icon>
//            </button>
//            </div>  
//          <div *ngIf="panelVisible"><ql-fixtures-simple [list]="event.fixtures.fixtures"></ql-fixtures-simple></div>
//          </div>
//
//""",
//  styles = @@@(buttonStyle),
//  inputs = @@@("event")
//)
//@classModeScala
//class FixturesEventComponent extends PanelComponent
//
//@Component(
//  selector = "ql-calendar-event",
//  template = """
//        <div><b>{{event.event.description}}</b>  {{event.event.time}}  Venue : <a routerLink="/venue/{{event.event.venue.id}}">{{(event.event.venue | async)?.name}}</a></div>
//""",
//  inputs = @@@("event")
//)
//@classModeScala
//class CalendarEventComponent extends EventComponent
//
//@Component(
//  selector = "ql-competition-event",
//  template = """
//        <div><a routerLink="/competition/{{event.competition.id}}/{{event.competition.typeName}}">{{event.competition.name}}</a>  {{event.event.time}}  Venue : <a routerLink="/venue/{{event.event.venue.id}}">{{(event.event.venue | async)?.name}}</a></div>
//""",
//  inputs = @@@("event")
//)
//@classModeScala
//class CompetitionEventComponent extends EventComponent
//
//
//
