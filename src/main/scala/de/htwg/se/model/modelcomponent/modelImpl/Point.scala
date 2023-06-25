package de.htwg.se.model.modelcomponent.modelImpl

enum Point(val symbol: String) {
  case WhitePoint extends Point("W")
  case BlackPoint extends Point("B")
  case GreenPoint extends Point("G")
  case RedPoint extends Point("R")
  case EmptyPoint extends Point(" ")
}