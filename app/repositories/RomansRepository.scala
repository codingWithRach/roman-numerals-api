package repositories

import models.Conversion

import javax.inject.Singleton
import scala.collection.mutable

@Singleton
class RomansRepository {
  private val romans: Map[Int, String] = Map(
    1 -> "I",
    4 -> "IV",
    5 -> "V",
    9 -> "IX",
    10 -> "X",
    40 -> "XL",
    50 -> "L",
    90 -> "XC",
    100 -> "C",
    400 -> "CD",
    500 -> "D",
    900 -> "CM",
    1000 -> "M")
  private val sortedArabics: Seq[Int] = romans.keys.toSeq.sortWith( _ > _ )
  private val emptyConversion: Conversion = Conversion(0, "")
  val newRomans: mutable.Set[Conversion] = mutable.Set[Conversion]()

  def convertToRoman( arabic: Int ): Conversion = {
    if (arabic <= 0) return emptyConversion
    var workingNum: Int = arabic
    var roman = ""
    for ( arabicNum <- sortedArabics ) {
      while (workingNum >= arabicNum) {
        workingNum -= arabicNum
        roman += romans(arabicNum)
      }
    }
    Conversion(arabic, roman)
  }

  def convertToArabic( roman: String ): Conversion = {
    val uppercaseRoman = roman.toUpperCase
    if (!(uppercaseRoman matches "^[IVXLCDM]*$")) return emptyConversion
    var workingRoman: String = uppercaseRoman
    var arabic = 0
    val romanChars: Seq[String] = sortedArabics.map(arabic => romans(arabic))
    for (romanChar <- romanChars) {
      while (workingRoman.slice(0, romanChar.length) == romanChar) {
        arabic += romans.filter(_._2 == romanChar).keys.head
        workingRoman = workingRoman.substring( romanChar.length )
      }
    }
    Conversion(arabic, uppercaseRoman)
  }

  def addRoman( newRoman: Conversion): Option[Conversion] = {
    if( newRomans.exists( r => r.roman == newRoman.roman)) {
      None
    } else {
      newRomans.addOne(newRoman).collectFirst {
        case r if r.roman == newRoman.roman => r
      }
    }
  }
}
