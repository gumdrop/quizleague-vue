package quizleague.web.site.home



import quizleague.web.core._
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.fixtures.FixturesService
import com.felstar.scalajs.vue.VueComponent
import quizleague.web.model.Season
import scalajs.js
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.site.season.SeasonService
import quizleague.web.site.leaguetable.LeagueTableService
import com.felstar.scalajs.vue.VuetifyComponent
import quizleague.web.site._



object HomeComponent extends RouteComponent with NoSideMenu{

  type facade = VueRxComponent with VuetifyComponent
  
  override val subscriptions = Map(
      "appData" -> (c => ApplicationContextService.get()),
  )
  override val template="""
   <v-container grid-list-xl v-if="appData">
     <v-layout v-bind="align">
      <v-flex xs12 mdAndUp5>
        <v-carousel light >
          <v-carousel-item src="">
            <ql-home-page-table :seasonId="appData.currentSeason.id"></ql-home-page-table>
          </v-carousel-item>
          <v-carousel-item src=""><ql-latest-results :seasonId="appData.currentSeason.id"></ql-latest-results>
          </v-carousel-item>
          <v-carousel-item src=""><ql-next-fixtures :seasonId="appData.currentSeason.id"></ql-next-fixtures></v-carousel-item>
        </v-carousel>
      </v-flex>
      <v-flex>
        <ql-named-text name="front-page"></ql-named-text>
        <ql-text v-if="async(appData.currentSeason).id" :id="async(appData.currentSeason).text.id"></ql-text>
      </v-flex>
    </v-layout>
  </v-container>
"""
    override val components = @@(HomePageLeagueTable, NextFixturesComponent,LatestResultsComponent)
    def align(c:facade) = js.Dictionary("column" -> c.$vuetify.breakpoint.smAndDown)
    override val computed = Map("align" -> ({align _}:js.ThisFunction))

}

@js.native
trait NextFixturesComponent extends VueRxComponent{
  val seasonId:String
}


object NextFixturesComponent extends Component{
  type facade = NextFixturesComponent
  
  val name = "ql-next-fixtures"
  val template = 
    
    """
   <v-card>
   <v-card-title primary-title><h3>Next Fixtures</h3></v-card-title>
   <v-card-text v-if="fixtures">
      <div v-for="f in fixtures" :key="f.id">
      <h4>{{f.description}} {{f.date | date("d MMM yyyy")}}</h4>
      <ql-fixtures-simple :fixtures="f.fixtures | combine"></ql-fixtures-simple>
      </div>
   </v-card-text>
   </v-card>

"""
  

  
  override val props = @@("seasonId")
  override val subParams = Map("seasonId"-> "fixtures")
  override val subscriptions = Map("fixtures" -> (c => FixturesService.nextFixtures(c.seasonId)))

}

object LatestResultsComponent extends Component{
  type facade = NextFixturesComponent
  
  val name = "ql-latest-results"
  val template = 
    
    """
   <v-card>
     <v-card-title primary-title><h3>Latest Results</h3></v-card-title>
     <v-card-text v-if="fixtures">
        <div v-for="f in fixtures" :key="f.id">
        <h4>{{f.description}} {{f.date | date("d MMM yyyy")}}</h4>
        <ql-results-simple :results="f.fixtures | combine"></ql-results-simple>
        </div>
     </v-card-text>
   </v-card>

"""
  
  override val props = @@("seasonId")
  override val subParams = Map("seasonId"-> "fixtures")
  override val subscriptions = Map("fixtures" -> (c => FixturesService.latestResults(c.seasonId)))
}

object HomeSidenavComponent extends RouteComponent{
   override val template="""
  <v-list dense>
    <v-list-tile>Home Menu1</v-list-tile>
    <v-list-tile>Home Menu2</v-list-tile>
  </v-list>"""
       
  

}

@js.native
trait HomePageLeagueTable extends VueComponent with VueRxComponent{
  val seasonId:String
}


object HomePageLeagueTable extends Component{
  
  type facade = HomePageLeagueTable
  
  override val name = "ql-home-page-table"
  
  override val template ="""<v-card v-if="tables">
              <v-card-title primary-title><h4>League Table</h4></v-card-title>
              <v-card-text>
              <ql-league-table v-for="table in tables"  :key="table.id" :id="table.id"></ql-league-table>
              </v-card-text>
            </v-card>"""
  
  override val props = @@("seasonId")
  override val subParams = Map("seasonId"-> "tables")
  override val subscriptions = Map("tables" -> (c => LeagueTableService.leagueTables(c.seasonId)))
}