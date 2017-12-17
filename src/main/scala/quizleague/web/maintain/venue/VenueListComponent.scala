package quizleague.web.maintain.venue

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._

object VenueListComponent extends ItemListComponentConfig[Venue] with RouteComponent with VenueNames{
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items">
        <v-btn :to="'venue/' + item.id" flat left>{{item.name}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = VenueService
}