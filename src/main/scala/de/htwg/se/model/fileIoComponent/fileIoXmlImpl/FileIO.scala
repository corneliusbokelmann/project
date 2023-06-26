package de.htwg.se.model.fileIOComponent.fileIoXmlImpl

import java.io.{File, PrintWriter}

import scala.util.Try
import scala.xml.{Elem, PrettyPrinter, XML, Node}

import de.htwg.se.model.modelcomponent._
import de.htwg.se.model.modelcomponent.modelImpl._
import de.htwg.se.model.fileIoComponent.FileIOInterface

class FileIO extends FileIOInterface {

  private def createDirectory(path: String): Unit = {
    val directory = new File(path)
    if (!directory.exists()) {
      directory.mkdir()
    }
  }

  override def save(field: FieldInterface, filePath: String): Boolean = {
    createDirectory("XML")
    val pw = new PrintWriter(new File(filePath))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(fieldToXml(field.asInstanceOf[Field]))  // Casting to concrete type
    Try(pw.write(xml)).isSuccess
  }

  override def load(filePath: String): FieldInterface = {
    val xml = loadFromFile(filePath)
    xmlToField(xml)
  }

  private def loadFromFile(filePath: String): Elem = {
    XML.loadFile(filePath)
  }

  private def fieldToXml(field: Field): Elem = {
    <field>
      <matrix>
        {matrixToXml(field.matrix)}
      </matrix>
      <feedbackField>
        {feedbackFieldToXml(field.feedbackField)}
      </feedbackField>
    </field>
  }

  private def feedbackFieldToXml(feedbackField: FeedbackField): Elem = {
    <feedbackField guessLength={feedbackField.guessLength.toString}>
      {matrixToXml(feedbackField.getFeedbackMatrix)}
    </feedbackField>
  }

  private def matrixToXml(matrix: Matrix[_]): Elem = {
    matrix match {
      case m: Matrix[Point] =>
        <matrix type="point">
          {matrix.rows.map(row => rowToXml(row.asInstanceOf[Vector[Point]]))}
        </matrix>
      case m: Matrix[FeedbackInterface] =>
        <matrix type="feedback">
          {matrix.rows.map(row => rowToXml(row.asInstanceOf[Vector[FeedbackInterface]]))}
        </matrix>
      case _ => <matrix/> // Should never reach this case
    }
  }

  private def rowToXml(row: Vector[_]): Seq[Elem] = {
    row.head match {
      case _: Point =>
        row.map {
          case point: Point => pointToXml(point)
          case _ => <point/> // Should never reach this case
        }
      case _: FeedbackInterface =>
        row.map {
          case feedback: FeedbackInterface => feedbackToXml(feedback)
          case _ => <feedback/> // Should never reach this case
        }
      case _ => Seq.empty // Should never reach this case
    }
  }

  private def pointToXml(point: Point): Elem = {
    <point symbol={point.symbol}/>
  }

  private def feedbackToXml(feedback: FeedbackInterface): Elem = {
    <feedback type={feedback.toString}/>
  }

  private def xmlToField(xml: Elem): FieldInterface = {
    val matrixXml = xml \ "matrix"
    val matrixType = (matrixXml \ "@type").text
    val matrix = xmlToMatrix(matrixXml.head, matrixType)

    val feedbackFieldXml = xml \ "feedbackField"
    val guessLength = (feedbackFieldXml \ "@guessLength").text.toInt
    val feedbackMatrix = xmlToMatrix(feedbackFieldXml \ "matrix", "feedback").asInstanceOf[Matrix[FeedbackInterface]]
    val feedbackField = FeedbackField(feedbackMatrix, guessLength)

    Field(matrix.asInstanceOf[Matrix[Point]], feedbackField)
    }

    private def xmlToMatrix(xml: Seq[Node], matrixType: String): Matrix[_] = {
    val rows = xml.collect { case elem: Elem => rowToXml(elem, matrixType) }
    matrixType match {
        case "point" =>
        Matrix[Point](rows.asInstanceOf[Vector[Vector[Point]]])
        case "feedback" =>
        Matrix[FeedbackInterface](rows.asInstanceOf[Vector[Vector[FeedbackInterface]]])
        case _ => throw new RuntimeException("Unknown matrix type in XML") // Should never reach this case
    }
    }



    private def rowToXml(rowXml: Node, matrixType: String): Seq[Elem] = {
    matrixType match {
        case "point" =>
        val pointsXml = rowXml \ "point"
        pointsXml.map(xmlToPoint).map(pointToXml).toSeq
        case "feedback" =>
        val feedbacksXml = rowXml \ "feedback"
        feedbacksXml.map(xmlToFeedback).map(feedbackToXml).toSeq
        case _ => throw new RuntimeException("Unknown matrix type in XML") // Should never reach this case
    }
    }






  private def xmlToPoint(xml: Node): Point = {
    val symbol = (xml \ "@symbol").text
    Point.valueFromSymbol(symbol).get
    }

  private def xmlToFeedback(xml: Node): FeedbackInterface = {
    val feedbackType = (xml \ "@type").text
    FeedbackInterface.valueOf(feedbackType)
    }


  private def xmlToFeedbackField(xml: Elem): FeedbackField = {
    val guessLength = (xml \ "@guessLength").text.toInt
    val matrixXml = xml \ "matrix"
    val feedbackMatrix = xmlToMatrix(matrixXml.head, "feedback").asInstanceOf[Matrix[FeedbackInterface]]
    FeedbackField(feedbackMatrix, guessLength)
  }
}
