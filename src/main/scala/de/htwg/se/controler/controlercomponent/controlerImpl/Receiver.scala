package de.htwg.se.controler.controlercomponent
package controlerImpl

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.FieldInterface
import scala.util.{Failure, Success, Try}

import de.htwg.se.controler.controlercomponent.ReceiverInterface

class Receiver(var field: FieldInterface) extends ReceiverInterface {
  def add(point: Point, x: Int, y: Int): Unit = {
    field = field.put(point, x, y)
  }

  def remove(x: Int, y: Int): Option[Point] = {
    val point = field.getCell(x, y)
    field = field.put(Point.valueOf(" "), x, y)
    point
  }

  def get(x: Int, y: Int): Option[Point] = {
    field.matrix.cell(x, y) match {
      case Some(p) if p == Point.valueOf(" ") => None
      case point => point
    }
  }
}
