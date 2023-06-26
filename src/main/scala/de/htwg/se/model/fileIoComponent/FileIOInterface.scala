package de.htwg.se.model.fileIoComponent

trait FileIOInterface {
  def save(data: String, filePath: String): Unit
  def load(filePath: String): Option[String]
}
