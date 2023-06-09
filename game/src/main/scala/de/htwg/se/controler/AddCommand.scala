package de.htwg.se.controler

import scala.util.{Failure, Success, Try}
import de.htwg.se.model.Point

case class AddCommand(receiver: Receiver, point: Point, x: Int, y: Int) extends Command {
  private var removedPoint: Option[Point] = None

  override def execute(): Try[Unit] = {
    receiver.get(x, y) match {
      case None =>
        receiver.add(point, x, y)
        Try(())
      case Some(_) =>
        Failure(new IllegalStateException("Position already has a point."))
    }
  }


  override def undo(): Try[Unit] = removedPoint match {
    case Some(p) => Try(receiver.add(p, x, y))
    case None => Success(())
  }
}