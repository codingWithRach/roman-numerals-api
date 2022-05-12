package controllers

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import repositories.RomansRepository
import models.Conversion
import org.mockito.Mockito.when
import play.api.libs.json._

class RomansControllerSpec  extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  val mockDataService: RomansRepository = mock[RomansRepository]
  val arabicFor6: Int = 6
  val romanFor6: String = "VI"
  val conversionOf6: Conversion = Conversion(6, "VI")
  val emptyConversion: Option[Conversion] = Option(Conversion(0, ""))
  val noConversion: Option[Conversion] = None

  "RomansController GET convertToRoman" should {
    "return 200 OK for a request to convert a valid arabic number" in {
      when(mockDataService.convertToRoman(arabicFor6)) thenReturn conversionOf6

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val conversionAsJson = controller.convertToRoman(arabicFor6).apply(FakeRequest(GET, s"/toroman/$arabicFor6"))

      status(conversionAsJson) mustBe OK
      contentType(conversionAsJson) mustBe Some("application/json")

    }
  }

  "RomansController GET convertToArabic" should {
    "return 200 OK for a request to convert valid Roman numerals" in {
      when(mockDataService.convertToArabic(romanFor6)) thenReturn conversionOf6

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val conversionAsJson = controller.convertToArabic(romanFor6).apply(FakeRequest(GET, s"/toarabic/$romanFor6"))

      status(conversionAsJson) mustBe OK
      contentType(conversionAsJson) mustBe Some("application/json")

    }
  }
}
