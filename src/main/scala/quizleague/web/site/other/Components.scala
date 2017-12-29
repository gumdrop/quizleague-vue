package quizleague.web.site.other

import quizleague.web.core.RouteComponent
import quizleague.web.site._

object LinksComponent extends RouteComponent with NoSideMenu{
  val template = """
  <v-container grid-list-lg>
    <v-layout>
    <ql-named-text name="links-content"></ql-named-text>
    </v-layout>
  </v-container>"""  
}

object RulesComponent extends RouteComponent with NoSideMenu{
  val template = """
  <v-container grid-list-lg>
    <v-layout>
    <ql-named-text name="rules-content"></ql-named-text>
    </v-layout>
  </v-container>"""  
}