package de.htwg.se.model

object PointFactory {
  def createPoint(color: String): Point = {
    color match {
      case "W" => WhitePoint
      case "B" => BlackPoint
      case "G" => GreenPoint
      case "R" => RedPoint
      case _   => EmptyPoint
    }
  }
}
