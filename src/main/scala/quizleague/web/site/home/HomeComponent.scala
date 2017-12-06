package quizleague.web.site.home



import quizleague.web.core.Component
import quizleague.web.core.RouteComponent
import quizleague.web.site.ApplicationContextService

object HomeComponent extends RouteComponent{
  override val subscriptions = Map("appData" -> (v => ApplicationContextService.get()))
  override val template="""
   <v-container grid-list-md v-if="appData">
     <v-layout row wrap>
      <v-flex xs5>
        <v-carousel light dark>
          <v-carousel-item src="">
            <ql-home-page-table></ql-home-page-table>
          </v-carousel-item>
          <v-carousel-item src="">Latest Results {{'2012-04-01' | date('dd MMM yyyy')}}
          </v-carousel-item>
          <v-carousel-item src="">Next Fixtures</v-carousel-item>
        </v-carousel>
      </v-flex>
      <v-flex xs7 v-if="async(appData.currentSeason).text">
        <ql-text :id="async(appData.currentSeason).text.id"></ql-text>
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
  
  override val template ="""<v-card>
              <v-card-title primary-title><h4>League Table</h4></v-card-title>
              <v-card-text>
              <ql-league-table id="1986e8d1-6058-44ba-9086-a375de8d6087"></ql-league-table>
              </v-card-text>
            </v-card>"""
}