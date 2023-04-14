package de.htwg.se

// import model.Field
// import model.Point
import model.* 

@main def mastermind: Unit =
  println("Willkommen zu Mastermind!")
  var field = new Field(pointslength = 4, guesslegth = 3, Point.e)
  println(field.toString())
  field = field.put(Point.b,0,0)
  println(field.toString())