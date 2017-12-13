package quizleague.web.site.competition


import quizleague.web.core._
import quizleague.web.core.IdComponent

////////@Component(
////////  template = s"""
//////////  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
////////    <ql-named-text [name]="textName"></ql-named-text>
////////    <ql-text [textId]="item.text.id"></ql-text>
////////    <v-card>
//////      <v-card-title>League Table</v-card-title>
//////      <v-card-text>
////        <ql-league-table *ngFor="let table of item.tables" [ref]="table"></ql-league-table>
////      </v-card-text>
//////    </v-card>
////////    <v-card>
//////      <v-card-title>Results</v-card-title>
//////      <v-card-subtitle>Latest results</v-card-subtitle>
//////      <v-card-text>
////        <div *ngFor="let results of latestResults | async">
//          <div>{{(results.fixtures | async)?.date | date:"d MMM yyyy"}}</div>
//          <ql-results-simple [list]="results.results" ></ql-results-simple>
//        </div>
////      </v-card-text>
//////      <v-card-actions>
////        <v-btn md-button to="results">Show All</a>
////      </v-card-actions>
//////    </v-card>
////////    <v-card>
//////      <v-card-title>Fixtures</v-card-title>
//////      <v-card-subtitle>Next fixtures</v-card-subtitle>
//////      <v-card-text>
////        <div *ngFor="let fixtures of nextFixtures | async">
//          <div>{{fixtures.date | date:"d MMM yyyy"}}</div>
//          <div>{{now}}</div>
//          <ql-fixtures-simple [list]="fixtures.fixtures" ></ql-fixtures-simple>
//        </div>
////      </v-card-text>
//////      <v-card-actions>
////        <v-btn md-button to="fixtures">Show All</a>
////      </v-card-actions>
//////    </v-card>
//  </div>
//  $loadingTemplate
//  """)
//@classModeScala
//class LeagueCompetitionComponent(
//  route: ActivatedRoute,
//  service: CompetitionService,
//  viewService: CompetitionViewService,
//  applicationContextService: ApplicationContextService,
//  titleService: TitleService,
//  sideMenuService: SideMenuService)
//    extends BaseCompetitionComponent(
//      route,
//      service,
//      viewService,
//      applicationContextService,
//      titleService,
//      sideMenuService
//    )
//    with TeamCompetitionComponent{
//  
//    override val textName:String = "league-comp"
//}

object LeagueCompetitionPage extends RouteComponent{
  val template = """<ql-league-competition :id="$route.params.id"></ql-league-competition>"""
  override val components = @@(LeagueCompetitionComponent)
}

object LeagueCompetitionComponent extends Component{
  type facade = IdComponent
  val name = "ql-league-competition"
  val template = """<div v-if="item" >
    <ql-named-text name="league-comp"></ql-named-text>
    <ql-text :id="item.text.id"></ql-text>
    <v-card>
      <v-card-title>League Table</v-card-title>
      <v-card-text>
        <ql-league-table v-for="table in item.tables" :key="table.id" :id="table.id"></ql-league-table>
      </v-card-text>
    </v-card>
    <v-card>
      <v-card-title>Results</v-card-title>
      <v-card-subtitle>Latest results</v-card-subtitle>
      <v-card-text>
        <div v-for="results in latestResults" :key="results.id">
          <div>{{results.date | date("d MMM yyyy")}}</div>
          <ql-results-simple :results="results.fixtures | combine" ></ql-results-simple>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn md-button to="results">Show All</v-btn>
      </v-card-actions>
    </v-card>
    <v-card>
      <v-card-title>Fixtures</v-card-title>
      <v-card-subtitle>Next fixtures</v-card-subtitle>
      <v-card-text>
        <div v-for="fixtures in nextFixtures" :key="fixtures.id">
          <div>{{fixtures.date | date("d MMM yyyy")}}</div>
          <ql-fixtures-simple :fixtures="fixtures.fixtures | combine" ></ql-fixtures-simple>
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn md-button to="fixtures">Show All</v-btn>
      </v-card-actions>
    </v-card>
  </div>"""
  
  override val props = @@("id")
  override val subParams = Map("id" -> "item", "id" -> "nextFixtures", "id" -> "latestResults")
 
  
  override val subscriptions = Map(
      "item" -> (c => CompetitionService.get(c.id)),
      "nextFixtures" -> (c => CompetitionViewService.nextFixtures(c.id,1)),
      "latestResults" -> (c => CompetitionViewService.latestResults(c.id,1))
      )
}
