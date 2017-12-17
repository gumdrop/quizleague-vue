package quizleague.web.maintain.team

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._

object TeamComponent extends ItemComponentConfig[Team] with RouteComponent {

  val service = TeamService

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="Name"
          v-model="item.name"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <v-text-field
          label="Short Name"
          v-model="item.shortName"
          :rules=${valRequired("Short Name")}
          required
        ></v-text-field>
        <div><v-btn :to="'/maintain/text/' + item.text.id" flat><v-icon>description</v-icon>Text</v-btn></div>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

}
