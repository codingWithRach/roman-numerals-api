package repositories

import models.Conversion

import javax.inject.Singleton

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
    if (!(roman matches "^[IVXLCDM]*$")) return emptyConversion
    var workingRoman: String = roman
    var arabic = 0
    val romanChars: Seq[String] = sortedArabics.map(arabic => romans(arabic))
    for (romanChar <- romanChars) {
      while (workingRoman.slice(0, romanChar.length) == romanChar) {
        arabic += romans.filter(_._2 == romanChar).keys.head
        workingRoman = workingRoman.substring( romanChar.length )
      }
    }
    Conversion(arabic, roman)
  }
}
