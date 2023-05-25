package de.htwg.se.controler

import scala.util.{Failure, Success, Try}
import de.htwg.se.model.Point

case class RemoveCommand(receiver: Receiver, x: Int, y: Int) extends Command {
  private var removedPoint: Option[Point] = None

  override def execute(): Try[Unit] = {
    receiver.get(x, y) match {
      case Some(p) =>
        removedPoint = Some(p)
        receiver.remove(x, y)
        Try(())
      case None =>
        Failure(new IllegalStateException("No element at the specified position."))
    }
  }


  override def undo(): Try[Unit] = removedPoint match {
    case Some(p) => Try(receiver.add(p, x, y))
    case None => Success(())
  }
}
