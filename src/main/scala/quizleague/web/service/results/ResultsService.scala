package quizleague.web.service.results


import quizleague.web.model._
import quizleague.web.model.{ Results => Model }
import quizleague.domain.{ Results => Dom }
import quizleague.domain.Ref
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import quizleague.web.service._
import org.threeten.bp.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.names.ResultsNames
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.service.fixtures.FixturesPutService
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._


trait ResultsGetService extends GetService[Results] with ResultsNames {
  override type U = Dom

  val resultService:ResultGetService
  val fixturesService:FixturesGetService

  override protected def mapOutSparse(dom: Dom) = Model(dom.id,refObs(dom.fixtures, fixturesService),refObsList(dom.results, resultService))

  override protected def dec(json:js.Any) = decodeJson[U](json)

}

trait ResultsPutService extends PutService[Results] with ResultsGetService with DirtyListService[Model]{
  
  override val resultService:ResultPutService
  override val fixturesService:FixturesPutService

  override protected def mapIn(model: Model) = Dom(model.id, fixturesService.ref(model.fixtures), resultService.ref(model.results))

  override def save(model:Model) = {resultService.saveAllDirty;super.save(model)}
  
  override protected def make() = ???
  
  //def instance(comp:Competition) = ???

  override def enc(item: Dom) = item.asJson

}