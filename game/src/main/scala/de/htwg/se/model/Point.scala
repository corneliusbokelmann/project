package de.htwg.se.model

sealed trait Point

case object WhitePoint extends Point {
  override def toString: String = "W"
}

case object BlackPoint extends Point {
  override def toString: String = "B"
}

case object GreenPoint extends Point {
  override def toString: String = "G"
}

case object RedPoint extends Point {
  override def toString: String = "R"
}

case object EmptyPoint extends Point {
  override def toString: String = " "
}