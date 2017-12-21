package quizleague.web.maintain.fixtures


import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import js.JSConverters._
import TemplateElements._
import quizleague.web.maintain.text.TextService
import js.Dynamic.{ global => g }
import quizleague.web.util.Logging
import quizleague.web.util.rx._
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.names.FixturesNames
import quizleague.web.maintain.competition.CompetitionComponentConfig
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.maintain.component.ItemComponentConfig._

@js.native
trait FixturesListComponent extends CompetitionComponent{
  val fixtures:js.Array[Fixtures]
}

object FixturesListComponent extends CompetitionComponentConfig with FixturesNames{
  
  override type facade = FixturesListComponent
  
  val template = s"""
  <v-container v-if="item && fixtures">
    <v-layout column>
      <h2>Fixtures List for {{item.name}} </h2>
      <div v-for="fixture in fixtures" :key="fixture.id">
        <v-btn :to="item.typeName.toString + '/fixtures/' + fixture.id" flat left>{{fixture.date}}</v-btn>
      </div>
    </v-layout>
    $addFAB
    $backFAB
  </v-container>
  """    
  
  def add(c:facade):Unit = {
    val fixs = FixturesService.instance(c.item, c.fixtures)
    c.item.fixtures +++= (fixs.id, fixs)
    service.cache(c.item)
    c.$router.push(s"fixtures/${fixs.id}")
  }
  
  override val subscriptions = super.subscriptions ++ Map("fixtures" -> ((c:facade) => FixturesService.fixturesForCompetition(c.$route.params("id").toString).map(_.sortBy(_.date))))
  
  override val methods = super.methods + (("add", ({add _}):js.ThisFunction))

    
    


}
    