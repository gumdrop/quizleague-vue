package quizleague.web.site
import org.scalajs.dom

import scalajs.js.annotation.JSExportAll
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import dom.ext.Ajax

import scalajs.js
import js.Dynamic.literal
import com.felstar.scalajs.vue._
import org.scalajs.dom.raw.HTMLElement

import js.annotation.JSName

@JSExportAll
object SiteApp{
  
 
  def main(args:Array[String]):Unit = {

   Vue.component("ql-app", 
       literal(
                data=() => literal(
                drawer=true,
                menu=true
               ),
               subscriptions = literal(appData = SiteModule.appData.inner),
        template="""
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
        {{appData?appData.leagueName:''}}
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>more_vert</v-icon>
      </v-btn>
      <div slot="extension">
      	<v-btn to="/home" flat style="text-transform: none;"><v-icon left>home</v-icon><span>Home</span></v-btn>
      	<v-btn to="/team" flat style="text-transform: none;"><v-icon left>people</v-icon><span>Teams</span></v-btn>
      	<v-btn to="/competition" flat style="text-transform: none;"><v-icon left>people</v-icon><span>Competitions</span></v-btn>
      	<v-btn to="/venue" flat style="text-transform: none;"><v-icon left>location_on</v-icon><span>Venue</span></v-btn>
      </div>
    </v-toolbar>
    <v-content>
		  <v-container fill-height fluid>
        <v-layout justify-left align-top column>
         <router-view name="title"  style="z-index:2"></router-view>
         <router-view></router-view>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>""",
  
       ))
   
  val demo = new Vue(
        literal(el="#app",
          data=literal(),
          methods=literal(),
          computed=literal(),
          filters=literal(),
          router = Router(SiteModule())
      )
    )

  }
} 

@JSExportAll
case class AppData(title:String)
  
