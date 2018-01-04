package quizleague.web.site.results

import scala.scalajs.js


import quizleague.web.model.Result
import quizleague.web.util.rx._
import quizleague.web.util.Logging.log
import quizleague.web.core._
import rxscalajs.Observable
import com.felstar.scalajs.vue._


object SimpleResultsComponent extends Component{
  
  val name = "ql-results-simple"
  val template = """<ql-fixtures-simple :fixtures="results" :inlineDetails="inlineDetails"></ql-fixtures-simple>"""

  props("results","inlineDetails")

}  

