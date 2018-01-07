package quizleague.web.site.user

import quizleague.web.service.user._
import quizleague.web.core._


object UserService extends UserGetService{
  
  def userForEmail(email:String) = {
    list().map(_.filter(_.email == email).headOption)
  }
  
}

