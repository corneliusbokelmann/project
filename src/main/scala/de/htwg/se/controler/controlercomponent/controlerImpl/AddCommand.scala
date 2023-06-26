package de.htwg.se.controler.controlercomponent.controlerImpl

import scala.util.{Failure, Success, Try}
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.controler.controlercomponent.CommandInterface
import de.htwg.se.controler.controlercomponent.ReceiverInterface

case class AddCommand(receiver: ReceiverInterface, point: Point, x: Int, y: Int) extends CommandInterface {
  override def execute(): Try[Unit] = {
    receiver.get(x, y) match {
      case Some(existingPoint) if existingPoint == Point.EmptyPoint =>
        receiver.remove(x, y) flatMap { _ => receiver.add(point, x, y) }
      case Some(_) =>
        Failure(new Exception("Cannot overwrite non-empty point"))
      case None =>
        receiver.add(point, x, y)
    }
  }

  override def undo(): Try[Unit] = {
    receiver.remove(x, y) flatMap { _ => receiver.add(Point.EmptyPoint, x, y) }
  }
}