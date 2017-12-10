package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.Component
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.core.IdComponent
import scalajs.js
import quizleague.web.site.ApplicationContextService

object TeamFixturesPage extends RouteComponent {
  val template = """<div>
                      <ql-all-team-fixtures v-if="appData" :id="$route.params.id" :seasonId="appData.currentSeason.id"></ql-all-team-fixtures>
                    </div>"""
  override val subscriptions = Map("appData" -> (c => ApplicationContextService.get))
  
  
}

@js.native
trait TeamFixturesComponent extends IdComponent{
  val seasonId:String
}
  
object TeamFixturesComponent extends Component {
  
  type facade = TeamFixturesComponent
  val name = "ql-all-team-fixtures"
  val template = """<ql-fixtures-simple :fixtures="fixtures(id,seasonId)" :inlineDetails="true"></ql-fixtures-simple>"""
  override val methods = Map("fixtures" -> ((teamId:String,seasonId:String) => FixtureService.teamFixtures(teamId, seasonId)))
  override val props = @@("id","seasonId")
  
}