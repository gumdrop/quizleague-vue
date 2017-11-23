package quizleague.web.site.text

import quizleague.web.service.text._
import quizleague.web.core._


object TextModule extends Module{
  
  
  override val components = @@(TextComponent)
  
   
   
}


object TextService extends TextGetService

//@Injectable
//@classModeScala
//class GlobalTextService(
//    override val http:Http, 
//    override val textService:TextService
//    ) extends GlobalTextGetService with ServiceRoot