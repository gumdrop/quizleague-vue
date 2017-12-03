package quizleague.web.site.fixtures

import scala.scalajs.js


import quizleague.web.model.Fixture
import quizleague.web.util.rx._

//import quizleague.web.site.common.ComponentUtils
//import ComponentUtils._
import quizleague.web.core._
import rxscalajs.Observable

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
object SimpleFixturesComponent extends Component{  
  
  val name = "ql-fixtures-simple"
  
  val template = """
   <div>
    &nbsp;
    <div>
    <table v-if="fixts)">
      <tr *ngFor="let fixture of fixts">
        <td v-if="inlineDetails" class="inline-details" >{{fixture.date"}} : {{fixture.parentDescription}} {{fixture.description}}</td>
        <td class="home">{{async(fixture.home).name}}</td>
        <td> - </td>
        <td class="away">{{async(fixture.away).name}}</td> 
      </tr>
    </table>
    </div> 
    </div>

"""
  
  override val props = @@("fixtures","list","inlineDetails")
  override val subParams = Map("fixtures" -> "fixts")
  override val subscriptions = Map("fixts" -> ((c:js.Dynamic) => c.fixtures.asInstanceOf[Observable[Any]]))
  
  
//  
//  var inView = false
//  
//  @Input
//  var loadIfHidden = false
//  
//  @Input
//  var fixtures:Observable[js.Array[Fixture]] = _
//  
//  @Input
//  def list_= (list:js.Array[RefObservable[Fixture]]) = fixtures = zip(list)
//  
//  @Input
//  var inlineDetails = false  
//  
//  def load(event:Boolean){
//    inView = event || inView || loadIfHidden
//  }
  
}