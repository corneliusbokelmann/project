

package de.htwg.se.controler

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.model.{Field,PointFactory}
import scala.util.{Success,Try,Failure}

class RemoveCommandSpec extends AnyWordSpec {
    "RemoveCommand " when {
        "filled" should{
            val field = new Field(2, 3, PointFactory.createPoint("R"))
            val receiver = new Receiver(field)
            val removeCommand = new RemoveCommand(receiver, 0, 0)
            "execute and undo" in {
                removeCommand.execute() should be(Try(receiver.remove(0, 0)).map(_ => ()))
                removeCommand.undo() should be(Success(()))
                removeCommand.removedPoint should be(Some(PointFactory.createPoint("R")))
            }
        }
        "empty" should{
            val field = new Field(2, 3, PointFactory.createPoint(""))
            val receiver = new Receiver(field)
            val removeCommand = new RemoveCommand(receiver, 0, 0)
            "execute and undo" in {
                removeCommand.undo() should be(Try(receiver.add(PointFactory.createPoint("G"), 0, 0)))
            }
        }
    }
}


