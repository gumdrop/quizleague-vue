package quizleague.web.maintain.competition

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.season.SeasonService
import rxscalajs.Observable._
import js.JSConverters._
import quizleague.web.maintain.component.SelectUtils
import quizleague.web.core._
import rxscalajs.Observable
import quizleague.web.util.Logging._
import quizleague.web.maintain.season.SeasonService


object SubsidiaryCompetitionComponent extends CompetitionComponentConfig{

  val   template = s"""
  <v-container>
    <h2>Subsidiary Competition Detail {{season.startYear}}/{{season.endYear}}</h2>

    <v-form v-model="valid"  v-if="item && season">
      <v-layout column>
   
          <v-text-field  label="Name" type="text" v-model="item.name"
             required></v-text-field>

      <div><v-btn flat v-on:click="editText(item.text.id)"  type="button" ><v-icon>description</v-icon>Text...</v-btn></div>
      <div><v-btn flat v-on:click="fixtures(item)" ><v-icon>check</v-icon>Fixtures...</v-btn></div>
      </v-layout>
      $formButtons
    </v-form>
  </v-container>"""
 
}
    