package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.Component
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.core.IdComponent
import scalajs.js
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonIdComponent
import quizleague.web.core.IdComponent

object TeamResultsPage extends RouteComponent {
  val template = """<div>
                      <ql-all-team-results v-if="appData" :id="$route.params.id" :seasonId="appData.currentSeason.id"></ql-all-team-results>
                    </div>"""
  override val subscriptions = Map("appData" -> (c => ApplicationContextService.get))
  
  
}

object TeamResultsComponent extends Component {
  
  type facade = SeasonIdComponent with IdComponent
  val name = "ql-all-team-results"
  val template = """<ql-results-simple :results="fixtures(id,seasonId)" :inlineDetails="true"></ql-results-simple>"""
  override val methods = Map("fixtures" -> ((teamId:String,seasonId:String) => FixtureService.teamResults(teamId, seasonId)))
  override val props = @@("id","seasonId")
  
}