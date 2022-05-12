package controllers

import models.Conversion
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import repositories.RomansRepository

@Singleton
class RomansController  @Inject()(val controllerComponents: ControllerComponents, dataRepository: RomansRepository) extends BaseController {
  private val emptyConversion = Conversion(0, "")

  def convertToRoman(arabic: Int): Action[AnyContent] = Action {
    val conversion: Conversion = dataRepository.convertToRoman(arabic)
    if (conversion == emptyConversion)
      BadRequest(Json.toJson("Number cannot be converted into Roman numerals"))
    else
      Ok(Json.toJson(conversion))
  }

  def convertToArabic(roman: String): Action[AnyContent] = Action {
    val conversion: Conversion = dataRepository.convertToArabic(roman)
    if (conversion == emptyConversion)
      BadRequest(Json.toJson("Not a valid Roman numeral"))
    else
      Ok(Json.toJson(conversion))
  }
}
