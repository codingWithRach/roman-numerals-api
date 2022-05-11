package models

import play.api.libs.json.{Json, OFormat}

case class Conversion( arabic: Int, roman: String)

object Conversion {
  implicit val format: OFormat[Conversion] = Json.format[Conversion]
}
