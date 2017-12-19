package quizleague.web.maintain.season

import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.core.RouteComponent
import quizleague.web.model._
import quizleague.web.maintain.component.TemplateElements._

object SeasonComponent extends ItemComponentConfig[Season] with RouteComponent {

  val service = SeasonService

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
        <div>Competitions
          <div>
           <v-chip close v-for="c in item.competitions" :key="c.id">{{async(c).name}}</v-chip>
          </div>
        </div>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

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