package quizleague.web.site.fixtures

import scala.scalajs.js

import quizleague.web.model.Fixture
import quizleague.web.util.rx._

//import quizleague.web.site.common.ComponentUtils
//import ComponentUtils._
import quizleague.web.core._
import rxscalajs.Observable
import quizleague.web.core.IdComponent
import com.felstar.scalajs.vue.VueComponent
import com.felstar.scalajs.vue._
import quizleague.web.site.results.TableUtils
import quizleague.web.site.results.ReportsService

//@Component(
//  selector = "ql-fixtures-simple",
//  template = s"""
//   <div  in-viewport (inViewport)="load($$event.value)">
//    &nbsp;
//    <div *ngIf="inView">
//    <table *ngIf="fixtures | async as fs else loading">
//      <tr *ngFor="let fixture of fs">
//        <td *ngIf="inlineDetails" class="inline-details" >{{fixture.date | date : "d MMM yyyy"}} : {{fixture.parentDescription}} {{fixture.description}}</td>
//        <td class="home">{{(fixture.home | async)?.name}}</td>
//        <td> - </td>
//        <td class="away">{{(fixture.away | async)?.name}}</td>
//      </tr>
//    </table>
//    </div>
//    </div>
//    $loadingTemplate
//  """,
//    styles = js.Array(
//  """
//    .inline-details{
//      font-style: italic;
//      padding-right: .5em;
//      color: darkblue;
//    }
//""",
//""".home{
//      text-align:right;
//      padding-right:1em;
//    }
//    .away{
//      padding-left:1em;
//    }""")
//)

@js.native
trait SimpleFixturesComponent extends VueRxComponent {
  def fixtures: Observable[js.Array[Fixture]] = js.native
}

object SimpleFixturesComponent extends Component {

  type facade = SimpleFixturesComponent

  val name = "ql-fixtures-simple"

  val template = """
   <div v-if="fixts" class="ql-fixtures-simple">
    <div>
    <table>
      <ql-fixture-line v-for="fixture in fixts" :key="fixture.id" :fixture="fixture" :inlineDetails="inlineDetails"></ql-fixture-line>
    </table>
    </div> 
    </div>

"""

  override val props = @@("fixtures", "list", "inlineDetails")
  override val subParams = List("fixtures" -> "fixts")
  override val subscriptions = Map("fixts" -> (c => c.fixtures))
  override val components = @@(FixtureLineComponent)


}


object FixtureLineComponent extends Component with TableUtils{
  type facade = VueRxComponent with VuetifyComponent
  val name = "ql-fixture-line"
  val template = """
      <tr>
        <td v-if="inlineDetails" class="inline-details" ><span v-if="!short">{{fixture.date| date("d MMM yyyy")}}</span><span v-else>{{fixture.date| date("d-MM-yy")}}</span> : {{fixture.parentDescription}} {{fixture.description}}</td>
        <td v-if="!fixture.result" class="home"><ql-team-name :team="fixture.home" :short="short"></ql-team-name></td><td v-else class="home" :class="nameClass(fixture.result.homeScore, fixture.result.awayScore)"><ql-team-name :short="short" :team="fixture.home"></ql-team-name></td>
        <td v-if="!fixture.result"></td><td v-else class="score">{{fixture.result.homeScore}}</td>
        <td> - </td>
        <td v-if="!fixture.result"></td><td v-else class="score">{{fixture.result.awayScore}}</td>
        <td v-if="!fixture.result" class="away"><ql-team-name :team="fixture.away" :short="short"></ql-team-name></td><td v-else class="away" :class="nameClass(fixture.result.awayScore, fixture.result.homeScore)"><ql-team-name :short="short" :team="fixture.away"></ql-team-name></td> 
        <td v-if="!fixture.result"></td>
        <td v-else>
          <v-btn icon @click.stop="showReports=true" v-if="fixture.result.reports">
            <v-icon style="transform:scale(0.75)">description</v-icon>
          </v-btn>
          <v-dialog v-model="showReports" max-width="500" lazy v-if="fixture.result.reports">

          <v-card>
            <v-card-title>Reports ::&nbsp;<ql-team-name :short="short" :team="fixture.home"></ql-team-name>&nbsp;{{fixture.result.homeScore}} - {{fixture.result.awayScore}}&nbsp;<ql-team-name :short="short" :team="fixture.away"></ql-team-name></v-card-title>
            <ql-reports :id="fixture.result.reports.id"></ql-reports>
          </v-card>
         </v-dialog>
        </td> 
      </tr>"""
  override val components = @@(ReportsComponent)
  override val data = c => Map("showReports" -> false, "short" -> c.$vuetify.breakpoint.smAndDown)
  override val props = @@("fixture","inlineDetails")
  override val methods = Map("nameClass" -> nameClass _ )
}

object ReportsComponent extends Component{
  type facade = IdComponent
  val name = "ql-reports"
  val template = """
    <v-container grid-list-sm v-if="reports">
      <v-layout column>
      <v-flex v-for="report in reports.reports" :key="report.id">
        <v-card >
        <v-card-title><h5>{{async(report.team).name}}</h5></v-card-title>
        <v-card-text v-if="report.text">
          <ql-text :id="report.text.id"></ql-text>
        </v-card-text>
      </v-card> 
      </v-flex>
    </v-layout>
    </v-container>"""
  override val props = @@("id")
  override val subParams = List("id" -> "reports")
  override val subscriptions = Map("reports" -> (c => ReportsService.get(c.id)))
}
