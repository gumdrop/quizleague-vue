package quizleague.web.site.results

import scala.scalajs.js


import quizleague.web.model.Result
import quizleague.web.util.rx._
import quizleague.web.util.Logging.log
import quizleague.web.core._
import rxscalajs.Observable
import com.felstar.scalajs.vue._

//@Component(
//  selector = "ql-results-simple",
//  template = s"""
//   <div  in-viewport (inViewport)="load($$event.value)">
//    &nbsp;
//    <div *ngIf="inView">
//      <table *ngIf="results | async as rs; else loading">
//        <ng-template ngFor let-result [ngForOf]="rs">
//          <tr *ngIf="result.fixture | async as fixture">
//            <td *ngIf="inlineDetails" class="inline-details" >{{fixture.date | date : "d MMM yyyy"}} : {{fixture.parentDescription}} {{fixture.description}}</td>
//            <td class="home" [ngClass]="nameClass(result.homeScore, result.awayScore)">{{(fixture.home | async)?.name}}</td>
//            <td class="score">{{result.homeScore}}</td><td> - </td><td class="score">{{result.awayScore}}</td>
//            <td class="away" [ngClass]="nameClass(result.awayScore, result.homeScore)">{{(fixture.away | async)?.name}}</td>
//            <td *ngIf="result.reports | async as reports">
//              <a *ngIf="!reports.isEmpty" md-icon-button routerLink="/results/{{result.id}}/reports">
//                <md-icon style="transform:scale(0.75)" class="md-12">description</md-icon>
//              </a>
//            </td> 
//          </tr>
//        </ng-template>
//      </table>   
//    </div> 
//  </div>
//    $loadingTemplate  
//  """,
//  styles = js.Array("""
//    .winner {
//      	color:darkred;
//        font-weight:600;
//    }
//  """,
//  """
//    .inline-details{
//      font-style: italic;
//      padding-right: .5em;
//      color: darkblue;
//    }
//""",
//"""
//    .home{
//      text-align:right;
//      padding-right:1em;
//    }
//    .away{
//      padding-left:1em;
//    }
//""",
//""".score{
//      font-weight:500;
//  }
//""")
//)
//class SimpleResultsComponent{  
//  
//  var inView:Boolean = false
//  
//  @Input
//  var loadIfHidden = false
//  
// 
//  @Input("results")
//  var results:Observable[js.Array[Result]] = _
//  
//  @Input
//  def list_= (list:js.Array[RefObservable[Result]]):Unit = results = zip(list)
//  
//  @Input
//  var inlineDetails = false
//  
//  def load(event:Boolean){
//    inView = event || inView || loadIfHidden
//  }
//  
//  def nameClass(score1:Int, score2:Int) = if(score1 > score2) "winner" else if(score1 == score2) "orange" else ""
//    
//}

@js.native
trait This extends VueComponent with VueRxComponent{
  val results:Observable[js.Array[Result]] = js.native
}

object SimpleResultsComponent extends Component with TableUtils{
  
  type facade = This
  
  val name = "ql-results-simple"
  val template = """
      <table v-if="rs">
          <tr v-for="result in rs" :key="result.id">
            <td v-if="inlineDetails" class="inline-details" >{{async(result.fixture).date}} : {{async(result.fixture).parentDescription}} {{async(result.fixture).description}}</td>
            <td class="home" :class="nameClass(result.homeScore, result.awayScore)">{{async(async(result.fixture).home).name}}</td>
            <td class="score">{{result.homeScore}}</td><td> - </td><td class="score">{{result.awayScore}}</td>
            <td class="away" :class="nameClass(result.awayScore, result.homeScore)">{{async(async(result.fixture).away).name}}</td>
            <td>
              <v-btn flat icon v-if="!async(result.reports).isEmpty" :to="['/results',result.id,'/reports']">
                <v-icon style="transform:scale(0.75)">description</v-icon>
              </a>
            </td> 
          </tr>
      </table>   
  """
  override val props = @@("results")
  override val subParams = Map("results" -> "rs")
  override val subscriptions = Map("rs" -> (c => c.results))
  override val methods = Map("nameClass" -> nameClass _ )
  
  
}