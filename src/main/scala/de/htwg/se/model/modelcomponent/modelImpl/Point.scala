package de.htwg.se.model.modelcomponent.modelImpl

enum Point(val symbol: String):
  case WhitePoint extends Point("W")
  case BlackPoint extends Point("B")
  case GreenPoint extends Point("G")
  case RedPoint extends Point("R")
  case EmptyPoint extends Point(" ")

object Point:
  private val valuesMap: Map[String, Point] = Map(
    "W" -> WhitePoint,
    "B" -> BlackPoint,
    "G" -> GreenPoint,
    "R" -> RedPoint,
    " " -> EmptyPoint
  )

  def valueFromSymbol(symbol: String): Option[Point] = valuesMap.get(symbol)
