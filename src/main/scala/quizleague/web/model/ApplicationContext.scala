package quizleague.web.model

import scalajs.js
import quizleague.web.util.rx.RefObservable


class ApplicationContext (
  val id:String,
  val leagueName:String,
  val textSet:RefObservable[GlobalText],
  val currentSeason:RefObservable[Season],
  val senderEmail:String,
  val emailAliases:js.Array[EmailAlias],
  val cloudStoreBucket:String
) extends js.Object

object ApplicationContext{
  def apply(  
     id:String,
     leagueName:String,
     textSet:RefObservable[GlobalText],
     currentSeason:RefObservable[Season],
     senderEmail:String,
     emailAliases:js.Array[EmailAlias],
     cloudStoreBucket:String) = new ApplicationContext(id, leagueName, textSet, currentSeason, senderEmail, emailAliases, cloudStoreBucket)
}

class EmailAlias(val alias:String, val user:RefObservable[User]) extends js.Object

object EmailAlias{
  def apply(alias:String, user:RefObservable[User]) = new EmailAlias(alias,user)
}