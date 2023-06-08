package de.htwg.se.controler

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import de.htwg.se.model.{Field,PointFactory}

class ReceiverSpec extends AnyWordSpec {
    "The Receiver in Mastermind game" when {
        "empty" should {
            val field = new Field(2, 3, PointFactory.createPoint(" "))
            val receiver = new Receiver(field)
            receiver.add(PointFactory.createPoint("R"), 0, 0)
            "add a point" in {
                receiver.get(0, 0) should be(Some(PointFactory.createPoint("R")))
            }
        }
        "filled" should {
            val field = new Field(2, 3, PointFactory.createPoint("R"))
            val receiver = new Receiver(field)
            "remove a point" in {
                receiver.remove(0, 0) should be(Some(PointFactory.createPoint("R")))
            }
        }
    }
}


