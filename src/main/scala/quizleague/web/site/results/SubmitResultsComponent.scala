package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures.FixtureService
import scalajs.js
import js.JSConverters._
import quizleague.web.site._
import quizleague.web.model._

@js.native
trait SubmitResultsComponent extends com.felstar.scalajs.vue.VueRxComponent{
  val email:String
  var fixtures:js.Array[Fixture]
  val appData:ApplicationContext
  var hasResults:Boolean
}
object SubmitResultsComponent extends RouteComponent{
  
  type facade = SubmitResultsComponent
  
  val emailRegex = """(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"""
  
  val template ="""
    <v-container>
      <v-form>
      <v-layout column v-if="appData">
       <v-text-field v-model="email" label="email address"></v-text-field>
      <div v-for="fixture in fixtures">
        {{fixture.description}} - {{fixture.date | date("dd MMM yyyy")}}
        <v-text-field v-model="fixture.result.homeScore" :label="async(fixture.home).name" type="number" :disabled="hasResults"></v-text-field>
        <v-text-field v-model="fixture.result.awayScore" :label="async(fixture.away).name" type="number" :disabled="hasResults"></v-text-field>
      </div>
       
      </v-layout>
    </v-form>
    </v-container>"""
  
  def handleEmail(c:facade, e:js.Any) = {
    if(c.email.matches(emailRegex)) {
      FixtureService.fixturesForResultSubmission(c.email, c.appData.currentSeason.id).subscribe(handleFixtures(c) _)
    }
  }
  
  def handleFixtures(c:facade)(fixtures:js.Array[Fixture]) = {
    c.hasResults = fixtures.forall(_.result != null)
    c.fixtures = fixtures//if(c.hasResults) fixtures else fixtures.map(Fixture.addBlankResult(_))
  }
  
  
  subscription("appData")(c => ApplicationContextService.get)
  data("email","")
  data("hasResults",false)
  data("fixtures", js.Array())
  watch("email")(handleEmail _)
}