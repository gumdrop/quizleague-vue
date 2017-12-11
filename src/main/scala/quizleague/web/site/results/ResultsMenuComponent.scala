package quizleague.web.site.results

import scalajs.js
import quizleague.web.core.RouteComponent


object ResultsMenuComponent extends RouteComponent{
  val template = """
  <v-list dense >
    <v-list-tile >
      <v-btn to="/results/all" flat style="text-transform: none;">All Results</v-btn>
    </v-list-tile>
    <v-list-tile >
      <v-btn to="/fixtures/all" flat style="text-transform: none;">All Fixtures</v-btn>
    </v-list-tile>
   </v-list>
  """    
}