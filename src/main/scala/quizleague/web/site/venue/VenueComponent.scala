package quizleague.web.site.venue

import quizleague.web.core.RouteComponent
import com.felstar.scalajs.vue.Component
import scalajs.js
import scala.scalajs.js.JSConverters._
import js.Dynamic.literal
import quizleague.web.service.venue._
import com.felstar.scalajs.vue.Vue
import quizleague.web.core._
import rxscalajs.subjects.ReplaySubject
import quizleague.web.model.Venue
import rxscalajs.Observable
import scala.scalajs.js.annotation.ScalaJSDefined
import rxscalajs.Observable
import rxscalajs.facade.ObservableFacade
import scala.scalajs.js.ThisFunction0
import quizleague.web.core.Component

//@Component(
//    template = s"""
// <div *ngIf="itemObs | async as item; else loading">
//     <md-card>
//      <md-card-content>
//         <div fxLayout="row" fxLayoutAlign="space-between start">
//           <div fxLayout="column">
//             <div [innerText]="item.address" ></div>
//             <div class="map" fxHide.xs="true">
//               <iframe [src]="embeddedUrl(item)" width="400" height="300" frameborder="0" style="border: 0"></iframe>
//             </div>
//             <div fxHide="true" fxShow.xs="true"><a [href]="linkUrl(item)" target="_blank">map</a></div>
//             <div>email : <a href="mailto:{{item.email}}">{{item.email}}</a></div>
//             <div>website : <a href="{{item.website}}" target="_blank">{{item.website}}</a></div>
//             <div>phone : {{item.phone}}</div>
//           </div>
//           <div fxHide="true" fxShow.gt-sm="true">
//              <img md-card-img-sm src="{{item.imageURL}}">
//           </div>
//         </div>
//      </md-card-content>
//    </md-card>
//  </div>
//  $loadingTemplate
//""",
//    styles = @@@("""
//       .map{
//          margin-top:.5em;
//          margin-bottom:.5em;
//        }
// """)
//)
//@classModeScala
//class VenueComponent(
//    route:ActivatedRoute,
//    service:VenueService,
//    sanitiser:DomSanitizer,
//    override val titleService:TitleService,
//    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{
//
//  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
//
//  itemObs.subscribe(v => setTitle(v.name))
//
//  def embeddedUrl(venue:Venue) = sanitiser.bypassSecurityTrustResourceUrl(makeParts(venue).join(""))
//
//  def linkUrl(venue:Venue) = sanitiser.bypassSecurityTrustResourceUrl(makeParts(venue).take(2).join(""))
//
//  private def makeParts(venue:Venue) = {
//    js.Array("https://maps.google.com/maps?&q=",js.URIUtils.encodeURIComponent(s"${venue.name} ${venue.address}".replaceAll("\\s", "+")), "&output=embed")
//  }
//}
//
//@Component(
//    template = s"""
//  <ql-section-title>
//     <span *ngIf="itemObs | async as item; else loading">
//      {{item.name}}
//    </span>
//    $loadingTemplate
//  </ql-section-title>
//"""
//)
//class VenueTitleComponent(
//        route:ActivatedRoute,
//    service:VenueService
//    ){
//
//  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
//}
//
//@Component(
//  template = s"""
//  <div fxLayout="column" *ngFor="let item of items | async">
//    <a fxFlexAlign="start" routerLink="/venue/{{item.id}}"  md-menu-item routerLinkActive="active" >{{item.name}}</a>
//  </div>
//  """
//)
//class VenueMenuComponent(service:VenueService){
//
//  val items = service.list()
//
//}

object VenuePage extends RouteComponent {
  override val template = """<ql-venue :id="$route.params.id"></ql-venue>"""
}

object VenueComponent extends Component {

  override val name = "ql-venue"
  override val template = """
          <div v-if="venue">
           <v-card>
              <v-card-text>
                 <div>
                   <div >
                     <div v-html="lineBreaks(venue.address)"></div>
                      <div>email : <a :href="'mailto:' + venue.email">{{venue.email}}</a></div>
                     <div>website : <a :href="venue.website" target="_blank">{{venue.website}}</a></div>
                     <div>phone : {{venue.phone}}</div>
                   </div>
                   <div v-if="venue.imageURL" >
                      <img :src="venue.imageURL">
                   </div>
                 </div>
              </v-card-text>
            </v-card>
          </div>"""
  override val props = @@("id")
  override val subParams = Map("id"->"venue")
  override val subscriptions = Map("venue" -> ((v: js.Dynamic) => VenueService.get(v.id.toString)))
  override val methods = Map("lineBreaks" -> ((s: String) => s.replaceAll("\\n", "<br>")))

}

object VenueTitleComponent extends RouteComponent {
  override val template = """<ql-venue-title :id="$route.params.id"></ql-venue-title>"""
}

object VenueTitle extends Component {
  
  override val name = "ql-venue-title"
  override val template = """
    <v-toolbar      
      color="orange darken-3"
      dark
      clipped-left
      v-if="venue">
      <v-toolbar-title class="white--text" >
        {{venue.name}}
      </v-toolbar-title>
    </v-toolbar>"""
  
   override val props = @@("id")
   override val subParams = Map("id"->"venue")
   override val subscriptions = Map("venue" -> ((v: js.Dynamic) => VenueService.get(v.id.toString)))

}

object VenueMenuComponent extends Component {

  override val name = ""  
  
  override val template = """<v-list dense>
                    <v-list-tile v-for="venue in venues " :key="venue.id">
                    <v-btn v-bind:to="'/venue/' + venue.id" flat style="text-transform: none;">{{venue.name}}</v-btn>
                    </v-list-tile>
                   </v-list>"""
    override val subscriptions = Map("venues" -> ((v: js.Dynamic) => VenueService.list))
}