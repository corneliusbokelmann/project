package de.htwg.se.model.modelcomponent.modelImpl

enum Point(val symbol: String):
  case WhitePoint extends Point("W")
  case GreenPoint extends Point("G")
  case RedPoint extends Point("R")
  case BluePoint extends Point("U")
  case YellowPoint extends Point("Y")
  case OrangePoint extends Point("O")
  case PinkPoint extends Point("P")
  case PurplePoint extends Point("N")
  case BrownPoint extends Point("K")
  case EmptyPoint extends Point(" ")

object Point:
  private val valuesMap: Map[String, Point] = Map(
    "W" -> WhitePoint,
    "G" -> GreenPoint,
    "R" -> RedPoint,
    "U" -> BluePoint,
    "Y" -> YellowPoint,
    "O" -> OrangePoint,
    "P" -> PinkPoint,
    "N" -> PurplePoint,
    "K" -> BrownPoint,
    " " -> EmptyPoint
  )

  def valueFromSymbol(symbol: String): Option[Point] = valuesMap.get(symbol)
