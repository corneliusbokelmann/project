package de.htwg.se.aview

import de.htwg.se.controler._
import de.htwg.se.model._
import scala.io.StdIn.readLine
import de.htwg.se.util._

class TUI(controller: Controller) extends Observer:
    controller.add(this)
    def run = 
        println(controller.toString)
        getInputAndPrintLoop()

    override def update = ???

    def getInputAndPrintLoop(): Unit =
    println("Enter your move (<Stone><x><y>, eg. x02, q to quit):")
    val input = readLine
    input match
      case "q" =>
      case _ => {
        val chars = input.toCharArray
        val point = chars(0) match
            case 'W' => Point.w
            case 'B' => Point.b
            case 'G' => Point.g
            case 'R' => Point.r
            case _  => Point.e
        val x = chars(1).toString.toInt
        val y = chars(2).toString.toInt
        controller.put(point, x, y)
        println(controller.toString)
        getInputAndPrintLoop()
      }