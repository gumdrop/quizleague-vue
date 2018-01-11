package quizleague.web.site.venue

import quizleague.web.core.RouteComponent
import quizleague.web.core.GridSizeComponentConfig

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

object VenuesComponent extends RouteComponent with GridSizeComponentConfig{
   override val template="""
     <v-container v-bind="gridSize" fluid>
       <v-layout>
         <v-flex><ql-named-text name="venues-front-page"></ql-named-text></v-flex>
       </v-layout>
     </v-container>
       """

}
object VenuesTitleComponent extends RouteComponent{
   override val  template="""<v-toolbar      
      color="orange darken-3"
      dark
	    
      clipped-left>
      <v-toolbar-title class="white--text" >
        Venues
      </v-toolbar-title>
    </v-toolbar>"""
       

}