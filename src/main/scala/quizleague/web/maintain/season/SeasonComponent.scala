package quizleague.web.maintain.season

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.core.RouteComponent
import quizleague.web.model._
import quizleague.web.maintain.component.TemplateElements._
import scalajs.js
import js.JSConverters._
import quizleague.web.maintain.competition.CompetitionService

@js.native
trait SeasonComponent extends ItemComponent[Season]{
  var selectedType:String
}

object SeasonComponent extends ItemComponentConfig[Season] with RouteComponent {

  override type facade = SeasonComponent
    
  val service = SeasonService
  val competitionService = CompetitionService
  
  def removeCompetition(c:facade, id:String) = c.item.competitions ---= id
  
  def addCompetition(c:facade,typeName:String) = {
      val comp:Competition = competitionService.instance(CompetitionType.withName(typeName))
      c.item.competitions +++= (comp.id,comp)
      editCompetition(c,comp)
    }
  
  def editCompetition(c:facade, comp: Competition) = {
    service.cache(c.item)
    c.$router.push(s"${c.item.id}/competition/${comp.id}/${comp.typeName}")
  }

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="Start Year"
          v-model="item.startYear"
          :rules=${valRequired("Start Year")}
          required
        ></v-text-field>
        <v-text-field
          label="End Year"
          v-model="item.endYear"
          :rules=${valRequired("End Year")}
          required
        ></v-text-field>

        <div><v-btn :to="'/maintain/text/' + item.text.id" flat><v-icon>description</v-icon>Text</v-btn></div>
        <v-layout column>
          <v-select @input="addCompetition(selectedType)" clearable append-icon="add" v-model="selectedType" label="Add Competition" :items="types"></v-select>
        <div>
          <v-chip close @input="removeCompetition(c.id)" v-for="c in item.competitions" :key="c.id">{{async(c).name}}</v-chip>
        </div>
        </v-layout>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""
     
  override val methods = super.methods ++ Map(
      "removeCompetition" -> ({removeCompetition _}:js.ThisFunction),
      "addCompetition" -> ({addCompetition _}:js.ThisFunction)
  )
  
  override val data = c => Map(
      "types" -> CompetitionType.values.map(_.toString()).toJSArray,
      "selectedType" -> null,
      "valid" -> true
  )

// override val subscriptions = super.subscriptions ++ Map(
////     "venues" -> {c:facade => venues()},
////     "users" -> {c:facade => users()}
//     )
}

//////////////////  <div>
////////////////    <h2>Season Detail</h2>
////////////////    <form #fm="ngForm" (submit)="save()">
//////////////      <div fxLayout="column">
////////////        <md-input-container>
//////////            <input mdInput placeholder="Start Year" type="number"
////////             required
////////             [(ngModel)]="item.startYear" name="startYear">
////////        </md-input-container>
//////////        <md-input-container>        
////////          <input mdInput placeholder="End Year" type="number"
//////             required
//////             [(ngModel)]="item.endYear" name="endYear">
//////        </md-input-container>
////////        <div fxLayout="row"><button (click)="editText(item?.text)" md-button type="button" >Edit Text...</button></div>
////////        <div fxLayout="row"><button (click)="calendar()" md-button type="button" >Calendar...</button></div>
////////        <div fxLayout="row">
//////          <md-select placeholder="Competitions" [(ngModel)]="selectedType" name="selectedType">  
////            <md-option *ngFor="let type of competitionTypes" [value]="type">{{type}}</md-option>
////          </md-select>
//////          <button md-icon-button (click)="addCompetition(selectedType)" type="button" [disabled]="selectedType==null"><md-icon>add</md-icon></button>
//////        </div>
////////        <md-chip-list selectable="true">
//////       <ng-template ngFor let-comp [ngForOf]="item?.competitions">
////          <md-chip *ngIf="comp | async as c"  [removable]="true" (remove)="removeCompetition(c)">
//            <span (click)="editCompetition(c)">{{c?.name}}</span>
//            <md-icon mdChipRemove>cancel</md-icon>
//          </md-chip>
////       </ng-template>
//////        </md-chip-list>
////////
////////      </div>
//////////     $formButtons
//////////    </form>
////////////  </div>