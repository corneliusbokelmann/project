
package de.htwg.se.controler

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.model.{Field,PointFactory}
import scala.util.{Success,Try}

class AddCommandSpec extends AnyWordSpec {
    "AddCommand " should {
        val field = new Field(2, 3, PointFactory.createPoint(" "))
        val receiver = new Receiver(field)
        val addCommand = new AddCommand(receiver,PointFactory.createPoint("R") , 0, 0)
        "execute and undo" in {
            addCommand.execute() should be(Try(()))
            addCommand.undo() should be(Success(()))
        }
    }
}


