package de.htwg.se.controler.controlercomponent.controlerImpl

import scala.util.{Failure, Success, Try}
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.controler.controlercomponent.CommandInterface
import de.htwg.se.controler.controlercomponent.ReceiverInterface


case class AddCommand(receiver: ReceiverInterface, point: Point, x: Int, y: Int) extends CommandInterface {
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