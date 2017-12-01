package quizleague.web.site.home

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.core.RouteComponent
import quizleague.web.site.SiteModule
import quizleague.web.core.Component

object HomeComponent extends RouteComponent{
  override val subscriptions=Map("appData" -> ((v:js.Dynamic) => SiteModule.appData))
  override val template="""
   <v-container grid-list-md>
     <v-layout row wrap>
      <v-flex xs5>
        <v-carousel light dark>
          <v-carousel-item src="">
            <ql-home-page-table></ql-home-page-table>
          </v-carousel-item>
          <v-carousel-item src="">Latest Results
          </v-carousel-item>
          <v-carousel-item src="">Next Fixtures</v-carousel-item>
        </v-carousel>
      </v-flex>
      <v-flex xs7>
        <ql-text id="016b153e-6ff4-4577-8c46-2e79dc5b66f3"></ql-text>
      </v-flex>
    </v-layout>
  </v-container>
"""
}

object HomeSidenavComponent extends RouteComponent{
   override val template="""
  <v-list dense>
    <v-list-tile>Home Menu1</v-list-tile>
    <v-list-tile>Home Menu2</v-list-tile>
  </v-list>"""
       
  

}

object HomePageLeagueTable extends Component{
  
  override val name = "ql-home-page-table"
  
  override val template =          """<v-card>
              <v-card-title primary-title><h4>League Table</h4></v-card-title>
              <v-card-text>League Table Text</v-card-text>
            </v-card>"""
}