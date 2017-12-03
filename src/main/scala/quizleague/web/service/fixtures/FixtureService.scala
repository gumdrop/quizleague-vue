package quizleague.web.service.fixtures


import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixture => Model}
import quizleague.domain.{Fixture => Dom}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import scala.scalajs.js
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.names.FixtureNames
import quizleague.web.service.venue.VenueGetService
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.venue.VenuePutService
import quizleague.web.service.team.TeamPutService
import quizleague.web.service.DirtyListService
import quizleague.web.names.FixtureNames
import io.circe._,io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import io.circe.Json



trait FixtureGetService extends GetService[Fixture] with FixtureNames{
    override type U = Dom
    
  val venueService:VenueGetService
  val teamService:TeamGetService

  override protected def mapOutSparse(dom:Dom) = Model(dom.id,dom.description,dom.parentDescription,refObs(dom.venue, venueService),refObs(dom.home, teamService),refObs(dom.away, teamService),dom.date,dom.time,dom.duration)
  
  override protected def dec(json:js.Any) = decodeJson[U](json)

}

trait FixturePutService extends PutService[Fixture] with FixtureGetService with DirtyListService[Model]{
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.parentDescription, venueService.ref(model.venue), teamService.ref(model.home), teamService.ref(model.away), model.date, model.time, model.duration)
  override protected def make() = ???
  
  override val venueService:VenuePutService
  override val teamService:TeamPutService
  
  def instance(fx:Fixtures, home:Team, away:Team, venue:Venue) = {
    val dom = Dom(newId,fx.description, fx.parentDescription,venueService.getRef(venue),teamService.getRef(home),teamService.getRef(away),fx.date,fx.start,fx.duration)
    mapOutSparse(dom)
  }
  
  override def enc(item: Dom) = item.asJson

}

