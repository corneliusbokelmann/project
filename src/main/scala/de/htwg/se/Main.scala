package de.htwg.se

import com.google.inject.Guice
import de.htwg.se.controler.controlercomponent.ControllerInterface
import de.htwg.se.aview.{TUI, GUI}

object Mastermind {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new MastermindModule())

    val controller = injector.getInstance(classOf[ControllerInterface])
    val tui = injector.getInstance(classOf[TUI])
    val gui = injector.getInstance(classOf[GUI])

    println("Welcome to Mastermind!")

    // Start the TUI
    tui.run()

    // Show the GUI
    gui.visible = true
  }
}


