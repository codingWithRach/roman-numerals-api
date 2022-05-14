package controllers

import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import repositories.RomansRepository
import models.{Arabic, Conversion, Roman}
import org.mockito.Mockito.when
import org.mockito.ArgumentMatchers.any
import play.api.libs.json.Json

import scala.collection.mutable

class RomansControllerSpec  extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  val mockDataService: RomansRepository = mock[RomansRepository]
  val romanFor6: String = "VI"
  val conversionOf6: Conversion = Conversion(6, "VI")
  val emptyConversion: Option[Conversion] = Option(Conversion(0, ""))
  val noConversion: Option[Conversion] = None
  val controller = new RomansController(stubControllerComponents(), mockDataService)

  "RomansController GET convertToRoman" should {
    "return 200 OK for a request to convert a valid arabic number" in {
      when(mockDataService.convertToRoman(6)) thenReturn conversionOf6
      val result = controller.convertToRoman(6).apply(FakeRequest(GET, s"/toroman/6"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert 0" in {
      when(mockDataService.convertToRoman(0)) thenReturn Conversion(0, "")
      val result = controller.convertToRoman(0).apply(FakeRequest(GET, s"/toroman/0"))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert a negative arabic number" in {
      when(mockDataService.convertToRoman(-42)) thenReturn Conversion(0, "")
      val result = controller.convertToRoman(-42).apply(FakeRequest(GET, s"/toroman/-42"))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  "RomansController POST convertBodyToRoman" should {
    "return 200 OK for a request to convert a valid arabic number" in {
      when(mockDataService.convertToRoman(6)) thenReturn conversionOf6
      val result = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Arabic(6))))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert 0" in {
      when(mockDataService.convertToRoman(0)) thenReturn Conversion(0, "")
      val result = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Arabic(0))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert a negative arabic number" in {
      when(mockDataService.convertToRoman(-42)) thenReturn Conversion(0, "")
      val result = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Arabic(-42))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Number cannot be converted into Roman numerals"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request with a body in an invalid format" in {
      val result = controller.convertBodyToRoman().apply(FakeRequest(POST, "/toroman").withJsonBody(Json.toJson(Roman("LXII"))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Request body not in required format"))
    }
  }

  "RomansController GET convertToArabic" should {
    "return 200 OK for a request to convert valid Roman numerals" in {
      when(mockDataService.convertToArabic(romanFor6)) thenReturn conversionOf6
      val result = controller.convertToArabic(romanFor6).apply(FakeRequest(GET, s"/toarabic/$romanFor6"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")

    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an empty string" in {
      when(mockDataService.convertToArabic("")) thenReturn Conversion(0, "")
      val result = controller.convertToArabic("").apply(FakeRequest(GET, s"/toarabic/"))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an invalid Roman numeral" in {
      when(mockDataService.convertToArabic("hello")) thenReturn Conversion(0, "")
      val result = controller.convertToArabic("hello").apply(FakeRequest(GET, s"/toarabic/hello"))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  "RomansController POST convertBodyToArabic" should {
    "return 200 OK for a request to convert valid Roman numerals" in {
      when(mockDataService.convertToArabic(romanFor6)) thenReturn conversionOf6
      val result = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Roman("VI"))))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an empty string" in {
      when(mockDataService.convertToArabic("")) thenReturn Conversion(0, "")
      val result = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Roman(""))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to convert an invalid Roman numeral" in {
      when(mockDataService.convertToArabic("hello")) thenReturn Conversion(0, "")
      val result = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Roman("hello"))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Not a valid Roman numeral"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request with a body in an invalid format" in {
      val result = controller.convertBodyToArabic().apply(FakeRequest(POST, "/toarabic").withJsonBody(Json.toJson(Arabic(42))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Request body not in required format"))
    }
  }

  "RomansController POST addRoman" should {
    "return 201 CREATED for a request to add a new Roman in valid format" in {
      val newRoman: Conversion = Conversion(roman="Caesar", arabic=150344)
      when(mockDataService.addRoman(any())) thenReturn Some(newRoman)
      val result = controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(newRoman)))
      status(result) mustBe CREATED
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to add a new Roman who already exists" in {
      val newRoman: Conversion = Conversion(roman="Caesar", arabic=150344)
      when(mockDataService.addRoman(any())) thenReturn None
      controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(newRoman)))
      val result = controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(newRoman)))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Roman already exists"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to add a new Roman in invalid format" in {
      val newRoman: Roman = Roman("Caesar")
      val result = controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(newRoman)))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Request body not in required format"))
    }
  }

  "RomansController GET getRomans" should {
    "return 200 OK for a request to retrieve Romans that have been added" in {
      val caesar: Conversion = Conversion(roman="Caesar", arabic=150344)
      val caligula: Conversion = Conversion(roman="Caligula", arabic=240141)
      when(mockDataService.getRomans) thenReturn mutable.Set(caesar, caligula)
      val result = controller.getRomans().apply(FakeRequest(GET, "/romans"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to retrieve Romans when none have been added" in {
      when(mockDataService.getRomans) thenReturn mutable.Set[Conversion]()
      val result = controller.getRomans().apply(FakeRequest(GET, "/romans"))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("No Romans defined"))
    }
  }

  "RomansController DELETE deleteRoman" should {
    "return 200 OK for a request to delete a Roman that has been added" in {
      val caesar: Conversion = Conversion(roman="Caesar", arabic=150344)
      when(mockDataService.deleteRoman(any())) thenReturn Some(caesar)
      controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(caesar)))
      val result = controller.deleteRoman(caesar.roman).apply(FakeRequest(DELETE, "/romans/Caesar"))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to delete a Roman that has not been added" in {
      when(mockDataService.deleteRoman(any())) thenReturn None
      val result = controller.deleteRoman("Caesar").apply(FakeRequest(DELETE, "/romans/Caesar"))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Roman cannot be found"))
    }
  }

  "RomansController DELETE deleteRomanBody" should {
    "return 200 OK for a request to delete a Roman that has been added" in {
      val caesar: Conversion = Conversion(roman="Caesar", arabic=150344)
      when(mockDataService.deleteRoman(any())) thenReturn Some(caesar)
      controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(caesar)))
      val result = controller.deleteRomanBody().apply(FakeRequest(DELETE, "/romans").withJsonBody(Json.toJson(Roman(caesar.roman))))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to delete a Roman that has not been added" in {
      when(mockDataService.deleteRoman(any())) thenReturn None
      val result = controller.deleteRomanBody().apply(FakeRequest(DELETE, "/romans").withJsonBody(Json.toJson(Roman("Caesar"))))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Roman cannot be found"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to delete a Roman in invalid format" in {
      val invalidRoman: Arabic = Arabic(150344)
      val result = controller.deleteRomanBody().apply(FakeRequest(DELETE, "/romans").withJsonBody(Json.toJson(invalidRoman)))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Request body not in required format"))
    }
  }

  "RomansController PUT updateRomanBody" should {
    "return 200 OK for a request to update a Roman that has been added" in {
      val incorrectCaesar: Conversion = Conversion(roman="Caesar", arabic=150341)
      val correctedCaesar: Conversion = Conversion(roman="Caesar", arabic=150344)
      when(mockDataService.updateRoman(any())) thenReturn Some(correctedCaesar)
      controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(incorrectCaesar)))
      val result = controller.updateRomanBody().apply(FakeRequest(PUT, "/romans").withJsonBody(Json.toJson(correctedCaesar)))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 200 OK for a request to update a Roman when sent in lowercase" in {
      val incorrectCaesar: Conversion = Conversion(roman="Caesar", arabic=150341)
      val correctedCaesar: Conversion = Conversion(roman="caesar", arabic=150344)
      when(mockDataService.updateRoman(any())) thenReturn Some(correctedCaesar)
      controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(incorrectCaesar)))
      val result = controller.updateRomanBody().apply(FakeRequest(PUT, "/romans").withJsonBody(Json.toJson(correctedCaesar)))
      status(result) mustBe OK
      contentType(result) mustBe Some("application/json")
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to update a Roman that has not been added" in {
      val caesar: Conversion = Conversion(roman="Caesar", arabic=150344)
      when(mockDataService.updateRoman(any())) thenReturn None
      val result = controller.updateRomanBody().apply(FakeRequest(PUT, "/romans").withJsonBody(Json.toJson(caesar)))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Roman cannot be found"))
    }
  }

  it should {
    "return 400 BAD_REQUEST for a request to update a Roman in invalid format" in {
      val incorrectCaesar: Conversion = Conversion(roman="Caesar", arabic=150341)
      val correctedCaesar: Roman = Roman("Caesar")
      controller.addRoman().apply(FakeRequest(POST, "/romans").withJsonBody(Json.toJson(incorrectCaesar)))
      val result = controller.updateRomanBody().apply(FakeRequest(PUT, "/romans").withJsonBody(Json.toJson(correctedCaesar)))
      status(result) mustBe BAD_REQUEST
      contentType(result) mustBe Some("application/json")
      assert(contentAsString(result).contains ("Request body not in required format"))
    }
  }
}
