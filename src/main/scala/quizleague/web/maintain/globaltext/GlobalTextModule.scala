package quizleague.web.maintain.globaltext

import quizleague.web.service.globaltext.GlobalTextGetService
import quizleague.web.service.globaltext.GlobalTextPutService
import quizleague.web.maintain.text.TextService

object GlobalTextModule {
  
}

object GlobalTextService extends GlobalTextGetService with GlobalTextPutService{
  override val textService = TextService
}