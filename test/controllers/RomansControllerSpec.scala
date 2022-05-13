package controllers

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import repositories.RomansRepository
import models.{Arabic, Conversion, Roman}
import org.mockito.Mockito.when
import play.api.libs.json.Json

class RomansControllerSpec  extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  val mockDataService: RomansRepository = mock[RomansRepository]
  val romanFor6: String = "VI"
  val conversionOf6: Conversion = Conversion(6, "VI")
  val emptyConversion: Option[Conversion] = Option(Conversion(0, ""))
  val noConversion: Option[Conversion] = None

  "RomansController GET convertToRoman" should {
    "return 200 OK for a request to convert a valid arabic number" in {
      when(mockDataService.convertToRoman(6)) thenReturn conversionOf6

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val conversionAsJson = controller.convertToRoman(6).apply(FakeRequest(GET, s"/toroman/6"))

      status(conversionAsJson) mustBe OK
      contentType(conversionAsJson) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert 0" in {
      when(mockDataService.convertToRoman(0)) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertToRoman(0).apply(FakeRequest(GET, s"/toroman/0"))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert a negative arabic number" in {
      when(mockDataService.convertToRoman(-42)) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertToRoman(-42).apply(FakeRequest(GET, s"/toroman/-42"))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  "RomansController POST convertBodyToRoman" should {
    "return 200 OK for a request to convert a valid arabic number" in {
      when(mockDataService.convertToRoman(6)) thenReturn conversionOf6

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val conversionAsJson = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Arabic(6))))

      status(conversionAsJson) mustBe OK
      contentType(conversionAsJson) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert 0" in {
      when(mockDataService.convertToRoman(0)) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Arabic(0))))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert a negative arabic number" in {
      when(mockDataService.convertToRoman(-42)) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Arabic(-42))))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
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

  it should {
    "return 400 BAD_REQUEST for a request to convert an empty string" in {
      when(mockDataService.convertToArabic("")) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertToArabic("").apply(FakeRequest(GET, s"/toarabic/"))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an invalid Roman numeral" in {
      when(mockDataService.convertToArabic("hello")) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertToArabic("hello").apply(FakeRequest(GET, s"/toarabic/hello"))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  "RomansController POST convertBodyToArabic" should {
    "return 200 OK for a request to convert valid Roman numerals" in {
      when(mockDataService.convertToArabic(romanFor6)) thenReturn conversionOf6

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val conversionAsJson = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Roman("VI"))))

      status(conversionAsJson) mustBe OK
      contentType(conversionAsJson) mustBe Some("application/json")

    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an empty string" in {
      when(mockDataService.convertToArabic("")) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Roman(""))))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an invalid Roman numeral" in {
      when(mockDataService.convertToArabic("hello")) thenReturn Conversion(0, "")

      val controller = new RomansController(stubControllerComponents(), mockDataService)
      val result = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Roman("hello"))))

      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }
}
