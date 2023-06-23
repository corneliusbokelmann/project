package de.htwg.se.controler.controlercomponent
package controlerImpl

import de.htwg.se.model.modelcomponent.{FieldInterface, Point, PointFactoryInterface}
import scala.util.{Failure, Success, Try}

case class Receiver(var field: FieldInterface) {
  private val pointFactory = new PointFactoryInterface

  def add(point: Point, x: Int, y: Int): Unit = {
    field = field.put(point, x, y)
  }

  def remove(x: Int, y: Int): Option[Point] = {
    val point = field.cell(x, y)
    field = field.put(pointFactory.createPoint(" "), x, y)
    point
  }

  def get(x: Int, y: Int): Option[Point] = {
    field.cell(x, y) match {
      case Some(p) if p == pointFactory.createPoint(" ") => None
      case point => point
    }
  }
}