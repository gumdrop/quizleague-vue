package quizleague.web.site.results

//import angulate2.std._
//import angulate2.router.ActivatedRoute
//import quizleague.web.site.common.SideMenuService
//import quizleague.web.site.common.TitleService
//import quizleague.web.site.common.SectionComponent
//import quizleague.web.site.common.MenuComponent
//import quizleague.web.site.common.TitledComponent
//import quizleague.web.site.common.ComponentUtils._
//import quizleague.web.util.rx._
//import angulate2.core.animations
//import angulate2.core.animate
//import angulate2.ext.classModeScala
//import angulate2.common.Location
import quizleague.web.model._
import scalajs.js

//@Component(
//  template = s"""
//    <div *ngIf="reports | async as item; else loading">
//      <div *ngFor="let report of item.reports" fxLayout="column">
//        <md-card>
//          <md-card-subtitle>By {{(report.team | async).name}}</md-card-subtitle>
//          <md-card-content>
//            <ql-text [textId]="report.text.id"></ql-text>
//          </md-card-content>
//        </md-card>
//      </div>  
//    </div>
//    <div style="position:absolute;left:1em;bottom:1em;">
//      <button md-fab (click)="back()" mdTooltip="Back" mdTooltipPosition="above">
//          <md-icon class="md-24">arrow_back</md-icon>
//      </button>
//    </div>
//    $loadingTemplate  
//  """
//)
//@classModeScala
//class ReportComponent(
//    location:Location,
//    route:ActivatedRoute,
//    service:ResultService,
//    override val sideMenuService:SideMenuService,
//    override val titleService:TitleService) extends SectionComponent with MenuComponent with TitledComponent{
//  
//  val result = route.params.switchMap( (params,i) => service.get(params("id")))
//  val reports = result.switchMap((r,i) => r.reports.obs)
//  
//  setTitle(result.switchMap((r,i) => extract2[Result,Fixture,js.Array[Team],String](r, _.fixture, f => js.Array(f.home,f.away))((r,f,ts) => s"Reports for ${f.parentDescription} ${f.description} - ${ts(0).name} : ${ts(1).name}")))
//  
//  def back() = location.back()
//}
//
//@Component(
//  template = s"""
//    <div *ngIf="result | async as item; else loading">
//      <ql-section-title *ngIf="item.fixture | async as fixture"><span>Reports for {{fixture.date | date:"d MMM yyyy"}} {{fixture.parentDescription}} {{fixture.description}} - {{(fixture.home | async).name}} : {{(fixture.away | async).name}}</span></ql-section-title>
//    </div>
//    $loadingTemplate 
//  """    
//)
//class ReportTitleComponent(
//    route:ActivatedRoute,
//    service:ResultService
//){
//  val result = route.params.switchMap( (params,i) => service.get(params("id")))
//}