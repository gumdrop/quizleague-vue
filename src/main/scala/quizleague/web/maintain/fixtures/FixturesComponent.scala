package quizleague.web.maintain.fixtures



import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.text.TextService

import js.Dynamic.{ global => g }
import quizleague.web.util.Logging
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.team.TeamService
import quizleague.web.model.Team
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.util.TeamManager
import quizleague.web.util.rx._
import quizleague.web.maintain.competition.CompetitionComponentConfig
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.util.rx.RefObservable
import quizleague.web.util.rx.RefObservable
import quizleague.web.maintain.util.TeamManager
import quizleague.web.maintain.component.ItemComponentConfig._

//@Component(
//  template = s"""
//  <div>
//    <h2>Fixtures</h2>
//    <form #fm="ngForm" (submit)="save()">
//    <div fxLayout="column">
//      <div fxLayout="column">
//        <md-input-container>
//          <input  required mdInput placeholder="Date" name="date" [(ngModel)]="item.date" type="date">
//        </md-input-container>
//        <md-input-container>
//          <input mdInput placeholder="Description" name="description" [(ngModel)]="item.description" type="text">
//        </md-input-container>
//        <md-input-container>
//          <input required mdInput placeholder="Parent Description" name="parentDescription" [(ngModel)]="item.parentDescription" type="text">
//        </md-input-container>
//        <md-input-container>
//          <input required mdInput placeholder="Start Time" name="time" [(ngModel)]="item.start" type="time">
//        </md-input-container>
//        <md-input-container>
//          <input required mdInput placeholder="Duration" name="duration" [(ngModel)]="item.duration" type="number" step=".1">
//        </md-input-container>
//       </div>
//       <div fxLayout="column">
//        <h4>Fixture List</h4>
//        <div fxLayout="row">          
//          <md-select placeholder="Home" [(ngModel)]="homeTeam" name="homeTeam" (change)="setVenue(homeTeam)">  
//            <md-option *ngFor="let team of unusedTeams(awayTeam)" [value]="team">{{team.name}}</md-option>
//          </md-select>
//          <md-select placeholder="Away" [(ngModel)]="awayTeam" name="awayTeam">  
//            <md-option *ngFor="let team of unusedTeams(homeTeam)" [value]="team">{{team.name}}</md-option>
//          </md-select>
//          <md-select placeholder="Venue" [(ngModel)]="venue" name="venue">  
//            <md-option *ngFor="let venue of venues" [value]="venue">{{venue.name}}</md-option>
//          </md-select>
//          <button md-icon-button type="button" (click)="addFixture()" [disabled]="!(homeTeam && awayTeam && venue)"><md-icon >add</md-icon></button>
//         </div>
//         <div fxLayout="column">
//          <div *ngFor="let f of item.fixtures" fxLayout="row">
//            <button  class="pos-fix" *ngIf="f | async as fixture" md-icon-button type="button" (click)="removeFixture(fixture)" ><md-icon>cancel</md-icon></button>
//            <span *ngIf="f | async as fixture">{{(fixture.home | async)?.name}} - {{(fixture.away | async)?.name}} @ {{(fixture.venue | async)?.name}}</span>
//          </div>
//         </div>
//        </div>      
//     </div>
//     $formButtons
//    </form>
//  </div>
//  """,
//  styles = @@@("""
//  .pos-fix{
//    position:relative;
//    top:-11px;
//  }
//"""))

@js.native
trait FixturesComponent extends CompetitionComponent{
  val teamManager:TeamManager
  val teams:js.Array[SelectWrapper[Team]]
  val homeTeam:RefObservable[Team]
  val awayTeam:RefObservable[Team]
  val fxs:Fixtures
  val venue:RefObservable[Venue]
}

object FixturesComponent extends CompetitionComponentConfig{

  override type facade = FixturesComponent
  val fixtureService = FixtureService
  
  
  override def init(): Unit = {
    super.init()
    
    route.params
      .switchMap((params, i) => service.get(params("competitionId")))
      .subscribe(comp = _)

    VenueService.list.subscribe(venues = _)

    Observable.zip(
      loadItem()
        .switchMap((f, i) => zip(f.fixtures))
        .switchMap((f, i) => f.flatMap(x => js.Array(x.home, x.away))),
      teamService.list(),
      (fixtureTeams: js.Array[Team], teams: js.Array[Team]) => {
        teamManager = new TeamManager(teams)
        fixtureTeams.foreach(teamManager.take(_))
      }).subscribe(x => Unit)

  }

//  override def save(): Unit = {
//    FixturesService.cache(item)
//    //item.fixtures.foreach({fixtureService.cache(_)})
//    location.back()
//  }

  def unusedTeams(c:facade, other: RefObservable[Team]) = teamManager(c).unusedTeams(other)

  def setVenue(c:facade, team: RefObservable[Team]) = {
    TeamService.get(team.id).subscribe(c.venue = _.venue)
  }

  def addFixture(c:facade) = {
    val f = fixtureService.instance(
      c.fxs,
      teamManager(c).take(homeTeam),
      teamManager(c).take(awayTeam),
      venue)
      
      item.fixtures +++= (f.id, f)
      fixtureService.cache(f)

    homeTeam = null
    awayTeam = null
    venue = null
  }
  
  def teamManager(c:facade) = if(c.teamManager == null) {c.teamManager = new TeamManager(c.teams); c.teamManager} else c.teamManager

  def removeFixture(c:facade, fx: Fixture) = {
    c.fxs.fixtures ---= fx.id
    teamManager(c).untake(fx.home)
    teamManager(c).untake(fx.away)
  }
  
  def venues() = SelectUtils.model[Venue](VenueService)(_.name)
  def teams() = SelectUtils.model[Team](TeamService)(_.name)
  
  def subscriptions = super.subscriptions ++ Map(
      "fxs" -> ((c:facade) => FixturesService.get(c.$route.params("fixturesId"))),
      "venues" -> ((c:facade) => venues()),
      "teams" -> ((c:facade) => teams()),
      
  )
  
  def data = c => super.data(c) ++ Map("teamManager" -> null, "venue" -> null, "homeTeam" -> null, "awayTeam" -> null)

}
    