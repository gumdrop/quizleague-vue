package quizleague.web.maintain.competition

import quizleague.web.maintain.component._
import quizleague.web.core._
import quizleague.web.model._
import scalajs.js
import quizleague.web.maintain.season.SeasonService
import rxscalajs.Observable
import scala.scalajs.js.UndefOr

@js.native
trait CompetitionComponent extends ItemComponent[Competition]{
  val season:Season
  
}

trait CompetitionComponentConfig extends ItemComponentConfig[Competition] with RouteComponent{
  
  override type facade <: CompetitionComponent
  
  val service = CompetitionService
  
  def fixtures(c:facade) = {
     service.cache(c.item)
     c.$router.push(s"fixtures")
   }
  
  def tables(c:facade) = {
     service.cache(c.item)
     c.$router.push(s"${c.item.id}/leaguetable")
   }

  
  override def subscriptions = super.subscriptions ++ Map("season" -> {c:facade => SeasonService.get(c.$route.params("seasonId").toString)})
  
  override def methods = super.methods ++ Map(
      "fixtures" -> ({fixtures _}:js.ThisFunction), 
      "tables" -> ({tables _}:js.ThisFunction) 
  )
}