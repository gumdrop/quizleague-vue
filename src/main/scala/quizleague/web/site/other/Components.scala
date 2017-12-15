package quizleague.web.site.other

import quizleague.web.core.RouteComponent

object LinksComponent extends RouteComponent{
  val template = """<ql-named-text name="links-content"></ql-named-text>"""  
}

object RulesComponent extends RouteComponent{
  val template = """<ql-named-text name="rules-content"></ql-named-text>"""  
}