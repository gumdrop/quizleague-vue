package quizleague.web.maintain.component

object TemplateElements {
  val chbxRetired = """<v-checkbox v-model="item.retired" name="retired" label="Retired"></v-checkbox>"""
  
  val formButtons = """
            <v-btn
              fixed
              dark
              fab
              bottom
              left
              small
              color="pink"
      :disabled="!valid"
              v-on:click = "save"
            >
              <v-icon>save</v-icon>
            </v-btn>
            <v-btn
              fixed
              dark
              fab
              bottom
              right
              small
              color="pink"
              v-on:click = "cancel"
            >
              <v-icon>cancel</v-icon>
            </v-btn>
"""
  
  val addFAB = """

      <v-btn  fixed
              dark
              fab
              bottom
              left
              small
              color="pink"
              v-on:click="add">
          <v-icon>add</v-icon>
      </v-btn>
"""
  
  val backFAB = """      <v-btn  fixed
              dark
              fab
              bottom
              right
              small
              color="pink"
              v-on:click="$router.back()">
          <v-icon>back</v-icon>
      </v-btn>
"""
  
  def valRequired(name:String) = s""""[(v) => !!v || '$name is required']""""
}