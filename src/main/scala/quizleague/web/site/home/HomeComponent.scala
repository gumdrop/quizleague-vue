package quizleague.web.site.home

import com.felstar.scalajs.vue._
import scalajs.js.Dynamic.literal
import scalajs.js
import quizleague.web.core.RouteComponent
import quizleague.web.core.PageComponent
import quizleague.web.site.SiteModule

object HomeComponent extends RouteComponent{
  override val component = Component(subscriptions=()=>literal(appData = SiteModule.appData.inner)
      ,template="""
   <v-container grid-list-md>
     <v-layout row wrap>
      <v-flex xs5>
        <v-carousel light dark>
          <v-carousel-item src="">
            <ql-home-page-table></ql-home-page-table>
          </v-carousel-item>
          <v-carousel-item src="">
          </v-carousel-item>
          <v-carousel-item src="">Next Fixtures</v-carousel-item>
        </v-carousel>
      </v-flex>
      <v-flex xs7>
        <ql-text id="016b153e-6ff4-4577-8c46-2e79dc5b66f3"></ql-text>
      </v-flex>
    </v-layout>
  </v-container>
""")
}

object HomeSidenavComponent extends RouteComponent{
   override val component = Component(
       template="""
  <v-list dense>
    <v-list-tile>Home Menu1</v-list-tile>
    <v-list-tile>Home Menu2</v-list-tile>
  </v-list>"""
       
   )

}

object HomePageLeagueTable extends PageComponent{
  override def apply() = {
      Vue.component("ql-home-page-table", 
       literal(template=
         """<v-card>
              <v-card-title primary-title><h4>League Table</h4></v-card-title>
              <v-card-text>League Table Text</v-card-text>
            </v-card>"""))

  }
}