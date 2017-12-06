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
import java.time.DateTimeUtils
import org.threeten.bp.format.DateTimeFormatter

@JSExportAll
object SiteApp{
  
 
  def main(args:Array[String]):Unit = {


   
  val demo = new Vue(
        literal(el="#app",
          data=literal(),
          methods=literal(),
          computed=literal(),
          filters=literal(date = ((date:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)))),
          router = Router(SiteModule())
      )
    )

  }
} 

  
