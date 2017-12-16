package quizleague.web.site.calendar

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import quizleague.web.core._
import quizleague.web.site.season.SeasonIdComponent
import com.felstar.scalajs.vue.VueRxComponent

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
  val template = """
    <v-toolbar      
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


@js.native
trait EventComponent extends VueRxComponent{
  
  val event:EventWrapper
}
  

@js.native
trait PanelComponent extends EventComponent{

  var panelVisible:Boolean

}


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
          <div><router-link :to="'/competition/' + event.competition.id + '/' + event.competition.typeName">{{event.fixtures.description}}</router-link></div>
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
  val template = """<div><b>{{event.event.description}}</b>  {{event.event.time}}  Venue : <router-link :to="'/venue/' + event.event.venue.id">{{async(event.event.venue).name}}</router-link></div>"""

}

object CompetitionEventComponent extends EventComponentConfig{
  
  val name = "ql-competition-event"
  val template = """<div><router-link :to="'/competition/' + event.competition.id+'/'+event.competition.typeName">{{event.competition.name}}</router-link>  {{event.event.time}}  Venue : <router-link :to="'/venue/' + event.event.venue.id">{{async(event.event.venue).name}}</router-link></div>"""

}

