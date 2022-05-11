package repositories

import models.Conversion
import javax.inject.Singleton

@Singleton
class RomansRepository {
  private val romans: Map[Int, String] = Map( 1 -> "I", 5 -> "V")


  def convertToRoman( arabic: Int ): Conversion = {
    Conversion(arabic, "I")
  }

  def convertToArabic( roman: String ): Conversion = {
    Conversion(1, roman)
  }
}
