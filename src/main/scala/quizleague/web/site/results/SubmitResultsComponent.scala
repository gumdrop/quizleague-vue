package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures.FixtureService
import scalajs.js
import quizleague.web.site._
import quizleague.web.model._

@js.native
trait SubmitResultsComponent extends com.felstar.scalajs.vue.VueRxComponent{
  val email:String
  var fixtures:js.Array[Fixture]
  val appData:ApplicationContext
}
object SubmitResultsComponent extends RouteComponent{
  
  type facade = SubmitResultsComponent
  
  val template ="""
    <v-container>
      <v-form>
      <v-layout column v-if="appData">
       <v-text-field v-model="email" label="email address"></v-text-field>
      <div v-for="fixture in fixtures">
        {{fixture.description}}
        <v-text-field v-model="fixture.result.homeScore" :label="async(fixture.home).name" type="number" :disabled="fixture.result"></v-text-field>
        <v-text-field v-model="fixture.result.awayScore" :label="async(fixture.away).name" type="number" :disabled="fixture.result"></v-text-field>
      </div>
       
      </v-layout>
    </v-form>
    </v-container>"""
  
  subscription("appData")(c => ApplicationContextService.get)
  data("email","")
  data("fixtures", js.Array())
  watch("email")((c:facade,x:js.Any) => 
    FixtureService.fixturesForResultSubmission(c.email, c.appData.currentSeason.id).subscribe(c.fixtures = _))
}