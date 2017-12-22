package quizleague.web.maintain.fixtures



import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.text.TextService

import js.Dynamic.{ global => g }
import quizleague.web.util.Logging
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.team.TeamService
import quizleague.web.model.Team
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.util.TeamManager
import quizleague.web.util.rx._
import quizleague.web.maintain.competition.CompetitionComponentConfig
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.util.rx.RefObservable
import quizleague.web.util.rx.RefObservable
import quizleague.web.maintain.util.TeamManager
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.core._
import com.felstar.scalajs.vue.VueRxComponent



@js.native
trait FixturesComponent extends CompetitionComponent{
  var teamManager:TeamManager
  val teams:js.Array[SelectWrapper[Team]]
  var homeTeam:RefObservable[Team]
  var awayTeam:RefObservable[Team]
  val fxs:Fixtures
  var venue:RefObservable[Venue]
}

object FixturesComponent extends CompetitionComponentConfig{

  override type facade = FixturesComponent
  val fixtureService = FixtureService
  override val components = @@(FixtureComponent)
  
  val template = s"""
  <v-container v-if="item && season && fxs">
    <h2>Fixtures : {{item.name}}</h2>
    <v-form v-model="valid">
    <v-layout column>
      <v-layout column>
        <v-text-field label="Date" v-model="fxs.date" type="date" required></v-text-field>
        <v-text-field label="Time" v-model="fxs.start" type="time" required></v-text-field>
        <v-text-field label="Duration" v-model.number="fxs.duration" type="number" step=".1" required></v-text-field>
        <v-text-field label="Description" v-model="fxs.description" type="text" required></v-text-field>
        <v-text-field label="Parent Description" v-model="fxs.parentDescription" type="text"></v-text-field>
       </v-layout>
       <v-layout column>
        <h4>Fixture List</h4>
        <v-layout row>
          <v-select label="Home" v-model="homeTeam" :items="unusedTeams(awayTeam)" @input="setVenue(homeTeam)"></v-select>        
          <v-select label="Away" v-model="awayTeam" :items="unusedTeams(homeTeam)"></v-select>
          <v-select label="Venue" v-model="venue" :items="venues"></v-select>
          <v-btn style="top:5px;" icon v-on:click="addFixture()" :disabled="!(homeTeam && awayTeam && venue)"><v-icon >add</v-icon></v-btn>
         </v-layout>
         <v-layout column>
           <fixture :fixture="fixture" :fixtures="fxs" :teamManager="teamManager" v-for="fixture in fxs.fixtures" :key="fixture.id"></fixture>
         </v-layout>
        </v-layout>      
     </v-layout>
     $formButtons
    </v-form>
  </v-container>
  """
  
//  override def init(): Unit = {
//    super.init()
//    
//    route.params
//      .switchMap((params, i) => service.get(params("competitionId")))
//      .subscribe(comp = _)
//
//    VenueService.list.subscribe(venues = _)
//
//    Observable.zip(
//      loadItem() <v-layout row>
//        .switchMap((f, i) => zip(f.fixtures))
//        .switchMap((f, i) => f.flatMap(x => js.Array(x.home, x.away))),      "removeFixture" -> ({removeFixture _ }:js.ThisFunction),
//      teamService.list(),
//      (fixtureTeams: js.Array[Team], teams: js.Array[Team]) => {
//        teamManager = new TeamManager(teams)
//        fixtureTeams.foreach(teamManager.take(_))
//      }).subscribe(x => Unit)
//
//  }

//  override def save(): Unit = {
//    FixturesService.cache(item)
//    //item.fixtures.foreach({fixtureService.cache(_)})
//    location.back()
//  }

  def unusedTeams(c:facade, other: RefObservable[Team]) = teamManager(c).unusedTeams(other)

  def setVenue(c:facade, team: RefObservable[Team]) = {
    TeamService.get(team.id).subscribe(t => c.venue = t.venue)
  }

  def addFixture(c:facade) = {
    val f = fixtureService.instance(
      c.fxs,
      teamManager(c).take(c.homeTeam), 
      teamManager(c).take(c.awayTeam),
      c.venue)
      
      c.fxs.fixtures +++= (f.id, f)
      fixtureService.cache(f)

    c.homeTeam = null
    c.awayTeam = null
    c.venue = null
  }
  
  def teamManager(c:facade) = if(c.teamManager == null) {c.teamManager = new TeamManager(c.teams); c.teamManager} else c.teamManager

 
  def venues() = SelectUtils.model[Venue](VenueService)(_.name)
  def teams() = SelectUtils.model[Team](TeamService)(_.name)
  
  override def methods = super.methods ++ Map(
      "addFixture" -> ({addFixture _ }:js.ThisFunction),
      "setVenue" -> ({setVenue _ }:js.ThisFunction),
      "unusedTeams" -> ({unusedTeams _ }:js.ThisFunction),
  )
      
  
  
  override def subscriptions = super.subscriptions ++ Map(
      "fxs" -> ((c:facade) => FixturesService.get(c.$route.params("fixturesId"))),
      "venues" -> ((c:facade) => venues()),
      "teams" -> ((c:facade) => teams()),
      
  )
  
  override def data = c => super.data(c) ++ Map(
      "teamManager" -> null, 
      "venue" -> null, 
      "homeTeam" -> null, 
      "awayTeam" -> null)

}

@js.native
trait FixtureComponent extends VueRxComponent{
  val fixture:RefObservable[Fixture]
  val fx:Fixture
  val fixtures:Fixtures
  val teamManager:TeamManager
  
}

object FixtureComponent extends Component{
  
  type facade = FixtureComponent
  
  val name = "fixture"
  val template = """
    <v-layout column v-if="fx">
      <v-layout row>
        <v-btn style="top:-14px;" icon v-on:click="removeFixture(fx)" ><v-icon>cancel</v-icon></v-btn>
        <v-btn style="top:-14px;" icon v-if="fx.result" v-on:click="showResult = !showResult"><v-icon>check</v-icon></v-btn>
        <v-btn style="top:-14px;" icon v-if="!fx.result"><v-icon>add</v-icon></v-btn>
        <span >{{async(fx.home).name}} - {{async(fx.away).name}} @ {{async(fx.venue).name}}</span>
      </v-layout>
      <v-layout row v-if="showResult && fx.result">
        <h4>Result : </h4>
        <v-text-field label="Home Score" v-model="fx.result.homeScore"></v-text-field>
        <v-text-field label="Away Score" v-model="fx.result.awayScore"></v-text-field>
      </v-layout>
      <v-layout row v-if="showResult && fx.result.reports">
        <h4>Reports : </h4><v-btn flat v-on:click="editText(report.text.id)" v-for="report in async(fx.result.reports).reports" :key="report.text.id">{{async(report.team).shortName}}...</v-btn>
      </v-layout>
      <v-divider></v-divider>
    </v-layout>
"""
  def removeFixture(c:facade, fx:Fixture) = {
    c.fixtures.fixtures ---= fx.id
    c.teamManager.untake(fx.home)
    c.teamManager.untake(fx.away)
   
  }
  
  def editText(c:facade, textId:String) = {
    FixtureService.cache(c.fx)
    FixturesService.cache(c.fixtures)
    c.$router.push(s"/maintain/text/$textId")
  }
  
  override val data = c => Map("showResult" -> false)
  override val props = @@("fixture","fixtures","teamManager")
  override val subscriptions = Map("fx" -> (c => c.fixture.obs))
  override val methods = Map(
      "removeFixture" -> ({removeFixture _ }:js.ThisFunction),
      "editText" -> ({editText _ }:js.ThisFunction),
  )
}
    