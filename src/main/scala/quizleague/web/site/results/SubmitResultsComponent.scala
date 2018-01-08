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
  var email:String
  var fixtures:js.Array[Fixture]
  val appData:ApplicationContext
  var hasResults:Boolean
  val reportText:String
  var confirm:Boolean
}
object SubmitResultsComponent extends RouteComponent{
  
  type facade = SubmitResultsComponent
  
  val emailRegex = """^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"""
  
  val template ="""
    <v-container>
      <v-form>
      <v-layout column v-if="appData">
       <v-text-field v-model="email" label="Enter your mail address" prepend-icon="email"></v-text-field>
      <div v-if="hasResults">This result has already been submitted, but you can add a match report.</div>
      <p></p>
      <div v-for="fixture in fixtures" v-if="!hasResults">
          {{fixture.description}} - {{fixture.date | date("dd MMM yyyy")}}
          <v-text-field v-model.number="fixture.result.homeScore" :label="async(fixture.home).name" type="number" :disabled="hasResults"></v-text-field>
          <v-text-field v-model.number="fixture.result.awayScore" :label="async(fixture.away).name" type="number" :disabled="hasResults"></v-text-field>
      </div>
      <div v-else>
        <ql-fixtures-simple :fixtures="fixtures | wrap" :inlineDetails="true"></ql-fixtures-simple>
      </div>
      <div v-if="fixtures.length > 0">
        <v-text-field v-model="reportText" textarea auto-grow label="Match Report"></v-text-field>
        <div><v-btn v-on:click="preSubmit" flat color="primary">Submit&nbsp;<v-icon>send</v-icon></v-btn></div>
      </div>
     <v-dialog v-model="confirm" persistent lazy >
        <v-card>
          <v-card-title>Check Results</v-card-title>
          <v-card-text>
            <ql-fixtures-simple :fixtures="fixtures | wrap" :inlineDetails="true"></ql-fixtures-simple>
          </v-card-text>
          </v-card-actions><v-btn flat v-on:click="cancel"><v-icon>cancel</v-icon>Cancel</v-btn><v-btn flat color="primary" v-on:click="submit"><v-icon>check</v-icon>Ok</v-btn></v-card-actions>
        </v-card>
     </v-dialog>

      </v-layout>
    </v-form>
    </v-container>"""
  
  def handleEmail(c:facade, e:js.Any) = {
    if(c.email.toLowerCase.matches(emailRegex)) {
      FixtureService.fixturesForResultSubmission(c.email, c.appData.currentSeason.id).subscribe(handleFixtures(c) _)
    }
  }
  
  def handleFixtures(c:facade)(fixtures:js.Array[Fixture]) = {
    c.hasResults = fixtures.forall(_.result != null)
    c.fixtures = if(c.hasResults) fixtures else fixtures.map(Fixture.addBlankResult _)
  }
  
  def preSubmit(c:facade) {
    if(c.hasResults){
      submit(c)
    }
    else{
       c.confirm = true
    }

  }
  
  def cancel(c:facade) {
    c.confirm = false
  }
  
  def submit(c:facade){
    c.confirm = false
    FixtureService.submitResult(c.fixtures, c.reportText, c.email)
    c.email = ""
    c.fixtures = js.Array()
    c.hasResults = false
  }
  
  
  subscription("appData")(c => ApplicationContextService.get)
  method("submit")({submit _}:js.ThisFunction)
  method("preSubmit")({preSubmit _}:js.ThisFunction)
  method("cancel")({cancel _}:js.ThisFunction)
  data("email","")
  data("hasResults",false)
  data("fixtures", js.Array())
  data("reportText",null)
  data("confirm", false)
  watch("email")(handleEmail _)
}