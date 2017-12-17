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
              right
              small
              color="pink"
              v-on:click="add">
          <v-icon>add</v-icon>
      </v-btn>
"""
  
  val backFAB = """    
    <div style="position:absolute;left:1em;bottom:2em;">
      <button md-fab (click)="back()">
          <md-icon class="md-24">arrow_back</md-icon>
      </button>
    </div>
"""
  
  def valRequired(name:String) = s""""[(v) => !!v || '$name is required']""""
}