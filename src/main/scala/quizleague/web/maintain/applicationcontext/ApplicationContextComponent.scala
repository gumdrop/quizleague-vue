package quizleague.web.maintain.applicationcontext

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._
import quizleague.web.maintain.component.SelectUtils
import quizleague.web.maintain.venue._
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain.globaltext.GlobalTextService
import rxscalajs.Observable
import scalajs.js
import quizleague.web.maintain.component.SelectWrapper
import quizleague.web.maintain.season.SeasonService

object ApplicationContextComponent extends ItemComponentConfig[ApplicationContext] with RouteComponent {

  val service = ApplicationContextService
  def users() = SelectUtils.model[User](UserService)(_.name)
  def textSets() = SelectUtils.model[GlobalText](GlobalTextService)(_.name)
  def seasons() = SelectUtils.model[Season](SeasonService)(s => s"${s.startYear}/${s.endYear}")

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="League Name"
          v-model="item.leagueName"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <v-select
          label="Global Text"
          :items="textSets"
          v-model="item.textSet"
          :rules=${valRequired("Name")}
          required
          >
        </v-select>
        <v-select
          label="Current Season"
          :items="seasons"
          v-model="item.currentSeason"
          :rules=${valRequired("Name")}
          required
          >
        </v-select>
        <v-text-field
          label="Sender Email"
          v-model="item.senderEmail"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <v-text-field
          label="Cloud Store Bucket"
          v-model="item.cloudStoreBucket"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <v-select
          label="Email Aliases"
          v-model="item.emailAliases"
          item-text="name"
          return-object
          multiple
          chips
          >
        </v-select>
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

 override val subscriptions = Map(
     "item" -> {c:facade => ApplicationContextService.get},
     "users" -> {c:facade => users()},
     "textSets" -> {c:facade => textSets()},
     "seasons" -> {c:facade => seasons()},
     )
}


