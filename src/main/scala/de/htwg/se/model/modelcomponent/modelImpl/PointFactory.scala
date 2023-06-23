package de.htwg.se.model.modelcomponent
package modelImpl

import de.htwg.se.model.modelcomponent.modelImpl.{RedPoint, Point, EmptyPoint, GreenPoint, BlackPoint, WhitePoint}


object PointFactory extends PointFactoryInterface{
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
