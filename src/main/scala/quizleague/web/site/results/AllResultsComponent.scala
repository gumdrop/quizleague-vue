package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.core.Component
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonIdComponent


//import quizleague.web.site.common.SideMenuService
//import quizleague.web.model.Team
//
//import quizleague.web.site.season.SeasonService
//import quizleague.web.site.global.ApplicationContextService
//import quizleague.web.model.Results
//import quizleague.web.model.Season
//import scalajs.js
//import quizleague.web.site.common.TitledComponent
//import quizleague.web.site.common.TitleService
//import quizleague.web.util.Logging._
//import quizleague.web.site.common.ComponentUtils
//import ComponentUtils._


//@Component(
//  template = s"""
//    <div *ngIf="items | async as its; else loading" fxLayout="column" fxLayoutGap="5px">  
//    <md-card *ngFor="let item of items | async">
//      <md-card-title *ngIf="item.fixtures | async as fixtures">{{fixtures.parentDescription}} - {{fixtures.date | date:"d MMMM yyyy"}} : {{fixtures.description}}</md-card-title>
//      <md-card-content>
//        <ql-results-simple [loadIfHidden]="false" [list]="item.results"></ql-results-simple>
//      </md-card-content>
//      </md-card>
//    </div>
//    $loadingTemplate
//  """    
//)
//@classModeScala
//class AllResultsComponent(
//    route:ActivatedRoute,
//    seasonService:SeasonService,
//    viewService:ResultsViewService,
//    override val sideMenuService:SideMenuService,
//    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent with ComponentUtils{
//  
//  setTitle("All Results")
//  
//  val items = viewService.season.switchMap((s,i) => seasonService.getResults(s))
//}
//
//@Component(
//  template = """
//  <ql-section-title>
//     <span>All Results</span><ql-season-select [currentSeason]="viewService.season | async" (onchange)="viewService.seasonChanged($event)"></ql-season-select>
//  </ql-section-title>
//  """    
//)
//class AllResultsTitleComponent(
//  val viewService:ResultsViewService
//)


object AllResultsPage extends RouteComponent{
  val template = """<div>
                      <ql-all-results></ql-all-results>
                    </div>"""
  
  override val components = @@(AllResultsComponent)
}


object AllResultsComponent extends Component{
  val name = "ql-all-results"
  type facade = SeasonIdComponent
  val template = """
    <v-container v-if="fixtures">
    <v-layout column>
    <v-card v-for="fixs in fixtures" :key="fixs.id" class="mb-3">
      <v-card-title primary-title><h3 class="headline mb-0">{{fixs.date | date('d MMM yyyy')}} {{fixs.description}}</h3></v-card-title>
      <v-card-text>
        <ql-results-simple :results="fixs.fixtures | combine"></ql-results-simple>
      </v-card-text>
    </v-card>
    </v-layout>
    </v-container>"""
  override val subscriptions = Map("fixtures" -> (c => ResultsViewService.results))
  
}

object AllResultsTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="red darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" >
        All Results
       </v-toolbar-title>
      &nbsp;
      <ql-season-select :season="season"></ql-season-select>
    </v-toolbar>"""
  
  override val data = c => Map("season" -> ResultsViewService.season)
}