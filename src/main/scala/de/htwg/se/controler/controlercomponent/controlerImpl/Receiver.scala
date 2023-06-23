package de.htwg.se.controler.controlercomponent.controlerImpl

import de.htwg.se.model.modelcomponent.{FieldInterface, Point, PointFactoryInterface}
import scala.util.{Failure, Success, Try}

case class Receiver(var field: FieldInterface) {
  def add(point: Point, x: Int, y: Int): Unit = {
    field = field.put(point, x, y)
  }

  def remove(x: Int, y: Int): Option[Point] = {
    val point = field.matrix.cell(x, y)
    field = field.put(PointFactoryInterface.createPoint(" "), x, y)
    point
  }

  def get(x: Int, y: Int): Option[Point] = {
    field.matrix.cell(x, y) match {
      case Some(p) if p == PointFactory.createPoint(" ") => None
      case point => point
    }
  }
}