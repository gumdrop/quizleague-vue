package quizleague.web.maintain.competition

import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.season.SeasonService



//@Component(
//  template = s"""
//  <div>
//    <h2>League Competition Detail</h2>
//    <form #fm="ngForm" (submit)="save()">
//      <div fxLayout="column">
//        <md-input-container>
//          <input mdInput placeholder="Name" type="text"
//             required
//             [(ngModel)]="item.name" name="name">
//        </md-input-container>
//        <md-input-container>
//          <input mdInput placeholder="Start Time" type="time"
//             required
//             [(ngModel)]="item.startTime" name="startTime">
//        </md-input-container>
//        <md-input-container>        
//          <input mdInput placeholder="Duration (hours)" type="number"
//             required step=".1"
//             [(ngModel)]="item.duration" name="duration">
//        </md-input-container>
//        <select placeholder="Subsidiary Competition" name="subsidiary" [(ngModel)]="item.subsidiary" [compareWith]="utils.compareWith" >
//          <option *ngFor="let subsidiary of competitions" [ngValue]="subsidiary" >
//            {{subsidiary.name}}
//          </option>
//        </select>
//       </div>
//      <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
//      <div fxLayout="row"><button (click)="fixtures(item)" md-button type="button" >Fixtures...</button></div>
//      <div fxLayout="row"><button (click)="results(item)" md-button type="button" >Results...</button></div>
//      <div fxLayout="row"><button (click)="tables(item)" md-button type="button" >Tables...</button></div>
//      $formButtons
//    </form>
//  </div>
//
//  """    
//)
object LeagueCompetitionComponent extends CompetitionComponentConfig{
  

  
  def filterSubs(c:Competition) = {
    c match {
      case s:SubsidiaryLeagueCompetition => true
      case _ => false
    }
  }
  
  def subsidiaries(c:facade) = combineAll(c.season.competitions 

}
    