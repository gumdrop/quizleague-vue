package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.user.UserService

object SubmitResultsComponent extends RouteComponent{
  val template ="""
    <v-container>
      <v-layout column v-if="users">
       <v-flex v-for="user in users">{{user.name}}</v-flex>
      </v-layout>
    </v-container>"""
  
  subscription("users")(c => UserService.list)
}