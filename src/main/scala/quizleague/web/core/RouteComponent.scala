package quizleague.web.core

import scalajs.js

trait RouteComponent {
  
  def component:js.Dynamic
  
  def apply() = component
  
}

trait PageComponent{
  def apply():js.Any
}