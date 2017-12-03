package quizleague.web.site.leaguetable

import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.site.team.TeamService
import quizleague.web.core._



object LeagueTableModule extends Module{
  override val components = @@(LeagueTableComponent)
}


object LeagueTableService extends LeagueTableGetService {
    override val teamService = TeamService

}

