package de.htwg.se.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.model.fileIoComponent.FileIOInterface
import de.htwg.se.model.modelcomponent.modelImpl.{Matrix, Point, Field, FeedbackField}
import de.htwg.se.model.modelcomponent.FeedbackInterface
import play.api.libs.json._

import java.nio.file.{Files, Paths}

class FileIO extends FileIOInterface {

  implicit val feedbackInterfaceFormat: Format[FeedbackInterface] = new Format[FeedbackInterface] {
    override def writes(feedback: FeedbackInterface): JsValue = JsString(feedback.toString)

    override def reads(json: JsValue): JsResult[FeedbackInterface] =
      json.validate[String].map {
        case "Nothing" => FeedbackInterface.Nothing
        case "ColorCorrect" => FeedbackInterface.ColorCorrect
        case "PositionCorrect" => FeedbackInterface.PositionCorrect
      }
  }

  implicit val pointFormat: Format[Point] = new Format[Point] {
    override def writes(point: Point): JsValue = Json.obj("symbol" -> point.symbol)

    override def reads(json: JsValue): JsResult[Point] = {
      val symbol = (json \ "symbol").as[String]
      JsSuccess(Point.valueFromSymbol(symbol).get)
    }
  }

  implicit val matrixFormat: Format[Matrix[Point]] = new Format[Matrix[Point]] {
    override def writes(matrix: Matrix[Point]): JsValue = {
      val rowsJson = matrix.rows.map(row => Json.toJson(row))
      Json.obj("rows" -> rowsJson)
    }

    override def reads(json: JsValue): JsResult[Matrix[Point]] = {
      val rowsJson = (json \ "rows").as[Seq[JsValue]]
      val rows = rowsJson.map(rowJson => Json.fromJson[Vector[Point]](rowJson))
      if (rows.forall(_.isSuccess)) {
        JsSuccess(Matrix(rows.flatMap(_.asOpt).toVector))
      } else {
        JsError("Invalid matrix format")
      }
    }
  }

  implicit val feedbackFieldFormat: Format[FeedbackField] = Json.format[FeedbackField]

  implicit val fieldFormat: Format[Field] = Json.format[Field]

  override def save(field: Field, filePath: String): Unit = {
    val jsonString = Json.toJson(field).toString()
    Files.write(Paths.get(filePath), jsonString.getBytes)
  }

  override def load(filePath: String): Option[Field] = {
    try {
      val jsonData = new String(Files.readAllBytes(Paths.get(filePath)))
      Json.fromJson[Field](Json.parse(jsonData)).asOpt
    } catch {
      case _: Exception => None
    }
  }
}
