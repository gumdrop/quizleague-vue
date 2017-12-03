package quizleague.web.service.results


import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{ Result => Model }
import quizleague.domain.{ Result => Dom }
import quizleague.domain.{ Report => DomReport }
import quizleague.domain.Ref
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import quizleague.web.service._
import org.threeten.bp.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.names.ResultsNames
import quizleague.web.names.ResultNames
import quizleague.web.service.user.UserGetService
import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.text.TextGetService
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.user.UserPutService
import quizleague.web.service.fixtures.FixturePutService
import quizleague.web.service.text.TextPutService
import quizleague.web.service.team.TeamPutService
import quizleague.web.service.DirtyListService
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._


trait ResultGetService extends GetService[Model] with ResultNames {
  override type U = Dom
  
  val userService:UserGetService
  val fixtureService:FixtureGetService
  val teamService:TeamGetService
  val reportsService:ReportsGetService

  override protected def mapOutSparse(dom: Dom) = Model(dom.id,refObs(dom.fixture, fixtureService),dom.homeScore, dom.awayScore, userService.refObs(dom.submitter), dom.note, refObs(dom.reports, reportsService))

  override protected def dec(json:js.Any) = decodeJson[U](json)

}

trait ResultPutService extends PutService[Model] with ResultGetService with DirtyListService[Model] {
  
  override val userService:UserPutService
  override val fixtureService:FixturePutService
  override val teamService:TeamPutService
  override val reportsService:ReportsPutService
  
  override protected def mapIn(model: Model) = Dom(
      model.id, 
      fixtureService.ref(model.fixture),
      model.homeScore, 
      model.awayScore, 
      Option(userService.ref(model.submitter)),
      model.note,
      reportsService.ref(model.reports)
  )

  override protected def make() = ???
  
  override def enc(item: Dom) = item.asJson

}
