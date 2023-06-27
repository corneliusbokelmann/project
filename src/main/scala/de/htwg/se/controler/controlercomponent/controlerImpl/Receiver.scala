package de.htwg.se.controler.controlercomponent.controlerImpl

import com.google.inject.Inject

import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.model.modelcomponent.FieldInterface
import scala.util.{Failure, Success, Try}

import de.htwg.se.controler.controlercomponent.ReceiverInterface

class Receiver @Inject() (var field: FieldInterface) extends ReceiverInterface {
  def add(point: Point, x: Int, y: Int): Try[Unit] = {
  field.matrix.cell(x, y) match {
    case Some(p) if p == Point.valueFromSymbol(" ") || p == Point.EmptyPoint =>
      field = field.put(Some(point), x, y)
      Success(())
    case Some(_) =>
      Failure(new IllegalStateException("Position already has a point."))
    case None =>
      Failure(new IllegalStateException("Position is out of bounds."))
  }
}


  def remove(x: Int, y: Int): Try[Point] = {
    if (isCellOccupied(x, y)) {
      val point = field.getCell(x, y).getOrElse(throw new IllegalStateException("Unexpected error: No point at the specified position."))
      field = field.put(Point.valueFromSymbol(" "), x, y)
      Success(point)
    } else {
      Failure(new IllegalStateException("No point at the specified position."))
    }
  }

  def get(x: Int, y: Int): Option[Point] = {
    field.matrix.cell(x, y) match {
      case Some(p) if p != Point.valueFromSymbol(" ") => Some(p)
      case _ => None
    }
  }

  private def isCellEmpty(x: Int, y: Int): Boolean = {
    field.matrix.cell(x, y) match {
      case Some(p) if p == Point.valueFromSymbol(" ") => true
      case _ => false
    }
  }

  private def isCellOccupied(x: Int, y: Int): Boolean = {
    !isCellEmpty(x, y)
  }
}
