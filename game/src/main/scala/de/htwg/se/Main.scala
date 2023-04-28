package de.htwg.se

import model.*
import controler.Controller
import aview.TUI

@main def mastermind: Unit =
  println("Willkommen zu Mastermind!")
  val field = new Field(pointslength = 2, guesslegth = 3, Point.e)
  val controller = Controller(field)
  val tui = TUI(controller)
  tui.run
