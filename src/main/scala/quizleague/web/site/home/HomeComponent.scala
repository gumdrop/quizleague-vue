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

object HomeComponent extends RouteComponent{
  override val subscriptions = Map(
      "appData" -> (c => ApplicationContextService.get()),
  )
  override val template="""
   <v-container grid-list-md v-if="appData">
     <v-layout row wrap>
      <v-flex xs5>
        <v-carousel light dark>
          <v-carousel-item src="">
            <ql-home-page-table :seasonId="appData.currentSeason.id"></ql-home-page-table>
          </v-carousel-item>
          <v-carousel-item src="">Latest Results {{'2012-04-01' | date('dd MMM yyyy')}}
          </v-carousel-item>
          <v-carousel-item src=""><ql-next-fixtures :seasonId="appData.currentSeason.id"></ql-next-fixtures></v-carousel-item>
        </v-carousel>
      </v-flex>
      <v-flex xs7>
        <ql-text v-if="async(appData.currentSeason).id" :id="async(appData.currentSeason).text.id"></ql-text>
      </v-flex>
    </v-layout>
  </v-container>
"""
    override val components = @@(HomePageLeagueTable, NextFixturesComponent)
}

@js.native
trait NextFixturesComponent extends VueComponent with VueRxComponent{
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