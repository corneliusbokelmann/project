package de.htwg.se.controler.controlercomponent.controlerImpl

import scala.util.{Failure, Success, Try}
import de.htwg.se.model.modelcomponent.modelImpl.Point
import de.htwg.se.controler.controlercomponent.CommandInterface
import de.htwg.se.controler.controlercomponent.ReceiverInterface


case class RemoveCommand(receiver: ReceiverInterface, x: Int, y: Int) extends CommandInterface {
  var removedPoint: Option[Point] = None

  override def execute(): Try[Unit] = {
    receiver.get(x, y) match {
      case Some(p) =>
        removedPoint = Some(p)
        Try(receiver.remove(x, y)).map(_ => ())
      case None => Failure(new IllegalStateException("No element at the specified position."))
    }
  }

  override def undo(): Try[Unit] = removedPoint match {
    case Some(p) => Try(receiver.add(p, x, y))
    case None => Success(())
  }
}

/* case class RemoveCommand(receiver: Receiver, x: Int, y: Int) extends Command {
  private var removedPoint: Option[Point] = None

  override def execute(): Try[Unit] = {
    receiver.get(x, y) match {
      case Some(p) =>
        removedPoint = Some(p)
        Try(receiver.remove(x, y)).map(_ => ())
      case None => Failure(new IllegalStateException("No element at the specified position."))
    }
  }

  override def undo(): Try[Unit] = removedPoint match {
    case Some(p) => Try(receiver.add(p, x, y))
    case None => Success(())
  }
} */