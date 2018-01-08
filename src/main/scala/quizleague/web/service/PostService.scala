package quizleague.web.service

import scala.scalajs.js

import io.circe._, io.circe.parser._
import quizleague.web.names.ComponentNames
import quizleague.web.util.Logging._
import quizleague.web.model.Model

trait PostService {


  
  protected def command[R,V](pathParts:List[String],i:Option[V])(implicit decoder:Decoder[R],encoder:Encoder[V]):R = {
    
    val path = pathParts.mkString("/")
    
    
    
//    http.post(s"$uriRoot/$path", i.fold("")(encoder(_).noSpaces))
//    .map((r, i) => decode[R](r.asInstanceOf[js.Dynamic]._body.toString).merge.asInstanceOf[R])
//      .onError((x, t) => { log(s"error in POST for path $uriRoot/$path"); Observable.of(null).asInstanceOf[Observable[U]] })

    null.asInstanceOf[R]
  }
  
 }