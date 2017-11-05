package quizleague.web.store

import scalajs.js.Dynamic.literal
import firebase.firebase._

object Firestore {
    val config = literal(      
      apiKey = "AIzaSyDrTlMf77UN7iUVCLoyOwRTgJPgHMXRT1k",
      authDomain = "quizleague-d02fe.firebaseapp.com",
      databaseURL = "https://quizleague-d02fe.firebaseio.com",
      projectId = "quizleague-d02fe",
      storageBucket = "quizleague-d02fe.appspot.com",
      messagingSenderId = "127835213248")
  
   Firebase.initializeApp(config)
      
   val db = Firebase.firestore()
}