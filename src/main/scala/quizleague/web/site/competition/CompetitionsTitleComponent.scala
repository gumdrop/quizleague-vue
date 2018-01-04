package quizleague.web.site.competition

import quizleague.web.core.RouteComponent


//import angulate2.ext.classModeScala
//import angulate2.std.Component
//import quizleague.web.site.common.MenuComponent
//import quizleague.web.site.common.SideMenuService
//import quizleague.web.site.common.SectionComponent
//import angulate2.router.ActivatedRoute
//import rxjs.Observable
//import quizleague.web.model.Team
//import angulate2.core.OnInit
//import quizleague.web.site.common.ComponentUtils
//import ComponentUtils._

//@Component(
//  template = s"""
//  <ql-section-title>
//     <span *ngIf="itemObs | async as item; else loading">
//      {{item.name}} <ql-season-name [season]="season"></ql-season-name>
//    </span>
//    $loadingTemplate
//  </ql-section-title>
//  """    
//)
//@classModeScala
//class CompetitionTitleComponent(
//    route:ActivatedRoute,
//    service:CompetitionService,
//    viewService:CompetitionViewService){  
//  
//  val itemObs = route.params.switchMap((params,i) => service.get(params("id")))
//  val season = viewService.season
//  
//}

object CompetitionsTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="purple darken-3"
      dark
      clipped-left>
      <v-toolbar-title class="white--text" >
        Competitions
      </v-toolbar-title>
      &nbsp;<h3><ql-season-select :season="season"></ql-season-select></h3>
    </v-toolbar>"""
  
  data("season",CompetitionViewService.season)
}
