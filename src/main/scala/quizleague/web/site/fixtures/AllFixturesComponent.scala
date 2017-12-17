package quizleague.web.site.fixtures

//import rxjs.Observable.RichIObservable
//import scala.scalajs.js
//import angulate2.router.ActivatedRoute
//import angulate2.std._
//import quizleague.web.site.season.SeasonService
//import quizleague.web.site.global.ApplicationContextService
//import quizleague.web.site.common.SideMenuService
//import quizleague.web.site.common.SectionComponent
//import quizleague.web.site.common.MenuComponent
//import angulate2.ext.classModeScala
//import quizleague.web.site.common.TitledComponent
//import quizleague.web.site.common.TitleService
//import quizleague.web.site.results.ResultsViewService
//import quizleague.web.site.results.ResultsViewService
//import quizleague.web.model.Season
//import rxjs.Observable
//import quizleague.web.util.Logging._
//import quizleague.web.site.common.ComponentUtils
import quizleague.web.core.RouteComponent

import quizleague.web.core._
import quizleague.web.site.season.SeasonIdComponent
import quizleague.web.site.ApplicationContextService
import rxscalajs.Observable
import scalajs.js


//@Component(
//  template = s"""
//  <div fxLayout="column" fxLayoutGap="10px" style="margin-right:1em;">  
//    <md-card *ngFor="let item of items | async">
//      <md-card-title>{{item.parentDescription}} - {{item.date | date:"d MMMM yyyy"}} : {{item.description}}</md-card-title>
//      <md-card-content>
//        <ql-fixtures-simple [list]="item.fixtures"></ql-fixtures-simple>
//      </md-card-content>      
//    </md-card>
//  </div>
//  """    
//)
//@classModeScala
//class AllFixturesComponent(
//    route:ActivatedRoute,
//    seasonService:SeasonService,
//    viewService:ResultsViewService,
//    override val sideMenuService:SideMenuService,
//    override val titleService:TitleService) extends SectionComponent with MenuComponent with ComponentUtils with TitledComponent{
//  
//  setTitle("All Fixtures")
//  
//  val items = viewService.season.switchMap((s,i) => seasonService.getFixtures(s))
//  
//}

//object AllFixturesComponent extends RouteComponent

//@Component(
//  template = """
//  <ql-section-title>
//     <span>All Fixtures</span><ql-season-select [currentSeason]="viewService.season | async" (onchange)="viewService.seasonChanged($event)"></ql-season-select>
//  </ql-section-title>
//  """    
//)
//object AllFixturesTitleComponent extends RouteComponent
//  val viewService:ResultsViewService
//)

object AllFixturesPage extends RouteComponent{
  val template = """<div>
                      <ql-all-fixtures v-if="appData" :seasonId="appData.currentSeason.id"></ql-all-fixtures>
                    </div>"""
    override val subscriptions = Map("appData" -> (c => ApplicationContextService.get))
}


object AllFixturesComponent extends Component{
  val name = "ql-all-fixtures"
  type facade = SeasonIdComponent
  val template = """
    <v-container v-if="fixtures">
    <v-layout column>
    <v-card v-for="fixs in fixtures" :key="fixs.id" class="mb-3">
      <v-card-title primary-title><h3 class="headline mb-0">{{fixs.date | date('d MMM yyyy')}} {{fixs.description}}</h3></v-card-title>
      <v-card-text>
        <ql-fixtures-simple :fixtures="fixs.fixtures| combine"></ql-fixtures-simple>
      </v-card-text>
    </v-card>
    </v-layout>
    </v-container>"""
  override val subscriptions = Map("fixtures" -> (c => FixturesService.activeFixtures(c.seasonId)))
  override val props = @@("seasonId")
  
}

object AllFixturesTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="red darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" >
        All Fixtures
      </v-toolbar-title>
    </v-toolbar>"""
}
