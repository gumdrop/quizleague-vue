package quizleague.web.maintain.team

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.maintain.component.ItemListComponentConfig
import quizleague.web.model._
import quizleague.web.names._

object TeamListComponent extends ItemListComponentConfig[Team] with RouteComponent with TeamNames{
  val template = s"""
  <v-container>
    <v-layout column>
      <div v-for="item in items">
        <v-btn :to="'team/' + item.id" flat left>{{item.name}}</v-btn>
      </div>
      $addFAB
    </v-layout>
  </v-container>
"""
val service = TeamService
}