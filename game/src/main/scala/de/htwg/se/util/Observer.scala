package de.htwg.se.util

trait Observer {
  def update(): Unit
}

trait Observable {
  private var subscribers: Vector[Observer] = Vector()

  def add(observer: Observer): Unit = {
    subscribers = subscribers :+ observer
  }

  def remove(observer: Observer): Unit = {
    subscribers = subscribers.filterNot(_ == observer)
  }

  def notifyObservers(): Unit = {
    subscribers.foreach(_.update())
  }
}
