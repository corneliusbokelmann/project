
package de.htwg.se.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class FieldSpec extends AnyWordSpec {
    "A Mastermind Field" when {
        "filled with W" should {
            val field1 = new Field(pointslength = 4, guesslegth = 5,Point.w)
            val field2 = new Field(pointslength = 5, guesslegth = 5,Point.w)
            val field3 = new Field(pointslength = 1, guesslegth = 1,Point.w)
            val field4 = new Field(pointslength = 1, guesslegth = 3,Point.w)
            val eol = sys.props("line.separator")
            "have a bar as String of form '+---+---+---+---+ +---+---+---+---+'" in {
                field1.bar() should be("+---+---+---+---+ +---+---+---+---+" + eol)
            }
            "have a scalabe bar" in {
                field1.bar(1) should be("+---+ +---+" + eol)
                field2.bar(2) should be("+---+---+ +---+---+" + eol)
            }
            "have cells as String of form '| W | W | W | W | W | | W | W | W | W | W |'" in {
                field2.cells(0) should be("| W | W | W | W | W | | W | W | W | W | W |" + eol)
            }
            "have a mesh in the form " +
                "+---+ +---+" +
                "| W | | W |" +
                "+---+ +---+" in {
                    field3.mesh(1) should be("+---+ +---+" + eol + "| W | | W |" + eol + "+---+ +---+" + eol)
                    field4.mesh(1) should be("+---+ +---+" + eol + "| W | | W |" + eol + "+---+ +---+" + eol + "| W | | W |" + eol + "+---+ +---+" + eol + "| W | | W |" + eol + "+---+ +---+" + eol)
                }
        }
        "filled with B" should {
            val field1 = new Field(pointslength = 4, guesslegth = 5,Point.b)
            val field2 = new Field(pointslength = 5, guesslegth = 5,Point.b)
            val field3 = new Field(pointslength = 1, guesslegth = 1,Point.b)
            val field4 = new Field(pointslength = 1, guesslegth = 3,Point.b)
            val eol = sys.props("line.separator")
            "have a bar as String of form '+---+---+---+---+ +---+---+---+---+'" in {
                field1.bar() should be("+---+---+---+---+ +---+---+---+---+" + eol)
            }
            "have a scalabe bar" in {
                field1.bar(1) should be("+---+ +---+" + eol)
                field2.bar(2) should be("+---+---+ +---+---+" + eol)
            }
            "have cells as String of form '| B | B | B | B | B | | B | B | B | B | B |'" in {
                field2.cells(0) should be("| B | B | B | B | B | | B | B | B | B | B |" + eol)
            }
            "have a mesh in the form " +
                "+---+ +---+" +
                "| B | | B |" +
                "+---+ +---+" in {
                    field3.mesh(1) should be("+---+ +---+" + eol + "| B | | B |" + eol + "+---+ +---+" + eol)
                    field4.mesh(1) should be("+---+ +---+" + eol + "| B | | B |" + eol + "+---+ +---+" + eol + "| B | | B |" + eol + "+---+ +---+" + eol + "| B | | B |" + eol + "+---+ +---+" + eol)
                }
        }
        "filled with empty" should {
            var field = new Field(pointslength = 4, guesslegth = 3,Point.e)
            val eol = sys.props("line.separator")
            "be empty initially" in {
                field.toString should be(
                    "+---+---+---+---+ +---+---+---+---+" + eol +
                    "|   |   |   |   | |   |   |   |   |" + eol +
                    "+---+---+---+---+ +---+---+---+---+" + eol +
                    "|   |   |   |   | |   |   |   |   |" + eol +
                    "+---+---+---+---+ +---+---+---+---+" + eol +
                    "|   |   |   |   | |   |   |   |   |" + eol +
                    "+---+---+---+---+ +---+---+---+---+" + eol)
            }
            "have the first line filled after the first move" in {
                field.put(Point.b,0,0).put(Point.w,0,1).put(Point.w,0,2).put(Point.b,0,3).toString should be(
                    "+---+---+---+---+ +---+---+---+---+" + eol +
                    "| B | W | W | B | |   |   |   |   |" + eol +
                    "+---+---+---+---+ +---+---+---+---+" + eol +
                    "|   |   |   |   | |   |   |   |   |" + eol +
                    "+---+---+---+---+ +---+---+---+---+" + eol +
                    "|   |   |   |   | |   |   |   |   |" + eol +
                    "+---+---+---+---+ +---+---+---+---+" + eol)
            }
        }
    }
}
