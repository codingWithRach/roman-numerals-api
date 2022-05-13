package controllers

import models.{Arabic, Conversion, Roman}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import repositories.RomansRepository

@Singleton
class RomansController  @Inject()(val controllerComponents: ControllerComponents, dataRepository: RomansRepository) extends BaseController {
  private val emptyConversion = Conversion(0, "")

  def convertToRoman(arabic: Int): Action[AnyContent] = Action {
    val conversion: Conversion = dataRepository.convertToRoman(arabic)
    val response = checkConversion(conversion, "Number cannot be converted into Roman numerals")
    if (conversion == emptyConversion)
      BadRequest(response)
    else {
      Ok(response)
    }
  }

  def convertBodyToRoman(): Action[AnyContent] = Action {
    implicit request => {
      val requestBody = request.body
      val arabicAsJson = requestBody.asJson
      val arabic: Option[Arabic] =
        arabicAsJson.flatMap(
          Json.fromJson[Arabic](_).asOpt
        )
      if (arabic.isEmpty) {
        BadRequest(Json.toJson("Request body not in required format"))
      } else {
        val conversion: Conversion = dataRepository.convertToRoman(arabic.get.arabic)
        val response = checkConversion(conversion, "Number cannot be converted into Roman numerals")
        if (conversion == emptyConversion)
          BadRequest(response)
        else {
          Ok(response)
        }
      }
    }
  }

  def convertToArabic(roman: String): Action[AnyContent] = Action {
    val conversion: Conversion = dataRepository.convertToArabic(roman)
    val response = checkConversion(conversion, "Not a valid Roman numeral")
    if (conversion == emptyConversion)
      BadRequest(response)
    else {
      Ok(response)
    }
  }

  def convertBodyToArabic(): Action[AnyContent] = Action {
    implicit request => {
      val requestBody = request.body
      val romanAsJson = requestBody.asJson
      val roman: Option[Roman] =
        romanAsJson.flatMap(
          Json.fromJson[Roman](_).asOpt
        )
      if (roman.isEmpty) {
        BadRequest(Json.toJson("Request body not in required format"))
      } else {
        val conversion: Conversion = dataRepository.convertToArabic(roman.get.roman)
        val response = checkConversion(conversion, "Not a valid Roman numeral")
        if (conversion == emptyConversion)
          BadRequest(response)
        else {
          Ok(response)
        }
      }
    }
  }

  def checkConversion(conversion: Conversion, message: String): JsValue = {
    if (conversion == emptyConversion) {
      Json.toJson(message)
    } else {
      Json.toJson(conversion)
    }
  }
}
