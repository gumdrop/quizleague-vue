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
import js.JSConverters._
import quizleague.web.maintain.component.SelectWrapper
import quizleague.web.maintain.season.SeasonService
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.util.rx.RefObservable


@js.native
trait ApplicationContextComponent extends ItemComponent[ApplicationContext]{
  var user:RefObservable[User]
  var alias:String
}

object ApplicationContextComponent extends ItemComponentConfig[ApplicationContext] with RouteComponent {

  override type facade = ApplicationContextComponent
  
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
        <v-layout column>
          <v-layout row>
            <v-btn style="position:relative;top:12px" :disabled="!alias && !user" v-on:click="addAlias" icon><v-icon>add</v-icon></v-btn>
            <v-text-field v-model="alias" label="Alias"></v-text-field>
            <v-select clearable v-model="user" label="Select User" :items="users"></v-select>
          </v-layout>
        <div>
          <v-chip close @input="removeAlias(c)" v-for="c in item.emailAliases" :key="c.alias">{{c.alias}} :: {{async(c.user).name}}</v-chip>
        </div>
        </v-layout>
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

 def removeAlias(c:facade, alias:EmailAlias) = c.item.emailAliases -= alias 
 def addAlias(c:facade) = {
       c.item.emailAliases += EmailAlias(c.alias, c.user)
       c.alias = null
       c.user = null
     }
     
 override val subscriptions = Map(
     "item" -> {c:facade => ApplicationContextService.get},
     "users" -> {c:facade => users()},
     "textSets" -> {c:facade => textSets()},
     "seasons" -> {c:facade => seasons()},
     )
  
 override val methods = super.methods ++ Map(
     "removeAlias" ->  ({removeAlias _ }:js.ThisFunction),
     "addAlias" ->  ({addAlias _ }:js.ThisFunction)
     )
 override val data = (c:facade) => super.data(c) ++ Map("user" -> null, "alias" -> null)
}

class AliasSelectWrapper(val text:String, val value:EmailAlias)




