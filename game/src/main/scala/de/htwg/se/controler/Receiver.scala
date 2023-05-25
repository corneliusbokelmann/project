package de.htwg.se.controler

import de.htwg.se.model.{Field, Point, PointFactory}
import scala.util.{Failure, Success, Try}

case class Receiver(var field: Field) {
  def add(point: Point, x: Int, y: Int): Unit = {
    field = field.put(point, x, y)
  }

  def remove(x: Int, y: Int): Option[Point] = {
    val point = field.matrix.cell(x, y)
    field = field.put(PointFactory.createPoint(" "), x, y)
    point
  }

  def get(x: Int, y: Int): Option[Point] = {
    field.matrix.cell(x, y) match {
      case Some(p) if p == PointFactory.createPoint(" ") => None
      case point => point
    }
  }
}
