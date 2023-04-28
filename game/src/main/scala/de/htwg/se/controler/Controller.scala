package de.htwg.se.controler

import de.htwg.se.model._
import de.htwg.se.util._

case class Controller(var field: Field) extends  Observable:
    def put(point: Point, x: Int, y: Int): Unit =
        field = field.put(point, x, y)
        notifyObservers
    override def toString: String = field.toString