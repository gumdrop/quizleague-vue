package quizleague.web.site.team

import quizleague.web.core.RouteComponent

//@Component(
//  template = """<ql-named-text name="venues-front-page"></ql-named-text>"""    
//)
//@classModeScala
//class VenuesComponent(
//    override val titleService:TitleService,
//    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{
//  
//    setTitle("Venues")
//}
//
//@Component(
//  template = """<ql-section-title><span>Venues</span></ql-section-title>"""    
//)
//class VenuesTitleComponent {
//  
//}

object TeamsComponent extends RouteComponent{
    val template="""<ql-named-text name="teams-header"></ql-named-text>"""
       

}
object TeamsTitleComponent extends RouteComponent{
   val  template="""<v-toolbar      
      color="amber darken-3"
      dark
	    
      clipped-left>
      <v-toolbar-title class="white--text" >
        Teams
      </v-toolbar-title>
    </v-toolbar>"""
       


}