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
import com.felstar.scalajs.vue.VueRxComponent

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
trait This extends VueComponent with VueRxComponent {
  def fixtures: Observable[js.Array[Fixture]] = js.native
}

object SimpleFixturesComponent extends Component {

  type facade = This

  val name = "ql-fixtures-simple"

  val template = """
   <div v-if="fixts">
    <div>
    <table >
      <ql-fixture-line v-for="fixture in fixts" :key="fixture.id" :fixture="fixture"></ql-fixture-line>
    </table>
    </div> 
    </div>

"""

  override val props = @@("fixtures", "list", "inlineDetails")
  override val subParams = Map("fixtures" -> "fixts")
  override val subscriptions = Map("fixts" -> (c => c.fixtures))


}

object FixtureLineComponent extends Component{
  val name = "ql-fixture-line"
  val template = """<tr>
        <td v-if="inlineDetails" class="inline-details" >{{fixture.date| date("d MMM yyyy")}} : {{fixture.parentDescription}} {{fixture.description}}</td>
        <td class="home">{{async(fixture.home).name}}</td>
        <td> - </td>
        <td class="away">{{async(fixture.away).name}}</td> 
      </tr>"""
  
  override val props = @@("fixture","inlineDetails")
}