package quizleague.web.maintain.leaguetable

import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.service.leaguetable.LeagueTablePutService
import quizleague.web.maintain.team.TeamService

object LeagueTableModule {
  
}

object LeagueTableService extends LeagueTableGetService with LeagueTablePutService{
  override val teamService = TeamService
}