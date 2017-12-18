package quizleague.web.maintain.globaltext

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._
import quizleague.web.maintain.component.SelectUtils
import quizleague.web.maintain.venue._
import quizleague.web.maintain.user.UserService

object GlobalTextComponent extends ItemComponentConfig[GlobalText] with RouteComponent {

  val service = GlobalTextService

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
      <v-layout row v-for="entry in item.text" :key="entry.name">
          <v-text-field
          label="Name"
          v-model="entry.name"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <div><v-btn :to="'/maintain/text/' + entry.text.id" flat><v-icon>description</v-icon>Text</v-btn></div>
      </v-layout>
     </v-layout>
     $addFAB
    </v-form>
  </v-container>"""

}
