package quizleague.web.site.results

import scalajs.js
import quizleague.web.core.RouteComponent


object ResultsMenuComponent extends RouteComponent{
  val template = """
  <div fxLayout="column">
   <a fxFlexAlign="start" routerLink="/results/all"  md-menu-item routerLinkActive="active" >All Results</a>
   <a fxFlexAlign="start" routerLink="/results/fixtures"  md-menu-item routerLinkActive="active" >All Fixtures</a>
  </div>
  """    
}