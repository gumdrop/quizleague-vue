package quizleague.web.site

import quizleague.web.core.Component


object SiteComponent extends Component {
     val name = "ql-app"

     val template="""
  <v-app
    toolbar 
  >
  <v-navigation-drawer
      clipped
      fixed
      app
	  v-model="drawer">
    <router-view name="sidenav"></router-view>
  </v-navigation-drawer>
    <v-toolbar      
      color="blue darken-3"
      dark
	    fixed 
      app 
      clipped-left>
      <v-toolbar-title class="white--text" >
        <v-toolbar-side-icon @click.stop="drawer = !drawer" v-show="menu"></v-toolbar-side-icon>
        <span v-if="appData">{{appData.leagueName}}</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
      <div slot="extension">
      	<v-btn to="/home" flat ><v-icon left>home</v-icon><span>Home</span></v-btn>
      	<v-btn to="/team" flat ><v-icon left>people</v-icon><span>Teams</span></v-btn>
      	<v-btn to="/competition" flat ><v-icon left>mdi-trophy</v-icon><span>Competitions</span></v-btn>
      	<v-btn to="/results" flat ><v-icon left>check</v-icon><span>Results</span></v-btn>
      	<v-btn to="/venue" flat ><v-icon left>location_on</v-icon><span>Venue</span></v-btn>
      	<v-btn to="/calendar" flat ><v-icon left>mdi-calendar</v-icon><span>Calendar</span></v-btn>
      </div>
    </v-toolbar>
    <v-content>
		  <v-container fill-height fluid>
        <v-layout justify-left align-top column>
         <router-view name="title"  style="z-index:2"></router-view>
         <p></p>
         <router-view></router-view>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>"""
  
  override val data = c => Map("drawer" -> true,"menu" -> true)
  override val subscriptions = Map("appData" -> (c => ApplicationContextService.get()))

}