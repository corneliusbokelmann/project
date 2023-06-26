package de.htwg.se.controler.controlercomponent.controlerImpl

import scala.util.{Failure, Success, Try}
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.controler.controlercomponent.CommandInterface
import de.htwg.se.controler.controlercomponent.ReceiverInterface

case class RemoveCommand(receiver: ReceiverInterface, x: Int, y: Int) extends CommandInterface {
  var removedPoint: Option[Point] = None

  override def execute(): Try[Unit] = {
    receiver.remove(x, y) match {
      case Success(point) =>
        removedPoint = Some(point.asInstanceOf[Point])
        Success(())
      case Failure(ex) =>
        Failure(ex)
    }
  }

  override def undo(): Try[Unit] = removedPoint match {
    case Some(point) =>
      receiver.add(point, x, y) match {
        case Success(_) => Success(())
        case Failure(ex) => Failure(ex)
      }
    case None => Success(())
  }
}
