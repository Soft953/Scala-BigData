import org.scalatest.FunSuite
import example.CsvParser

class CsvParserTest extends FunSuite{

  test("CsvParserTest.splitString simple example") {

    val string1 = "car2789;48,23 89,56;67.8;2.3;60.6;90.0;A1-Paris"

    val e = CsvParser.splitString(Array(string1), ';')

    assert(e.flatten.length === 7)
  }


  test("CsvParserTest.splitString wrong delimiter") {

    val string1 = "car2789;48,23 89,56;67.8;2.3;60.6;90.0;A1-Paris"

    val e = CsvParser.splitString(Array(string1), '%')

    assert(e.flatten.length === 1)
  }

  test("CsvParserTest.splitString empty string") {

    val e = CsvParser.splitString(Array(""), ';')

    assert(e.flatten.length === 1)
  }

  test("CsvParserTest.createCarData simple example") {

    val array1 = Array("car2789", "48,23 89,56", "67.8" , "2.3" , "60.6", "90.0", "A1-Paris")

    val carData1 = CsvParser.createCarData(array1);

    assert(carData1.toString === "car2789;48,23 89,56;67.8;2.3;60.6;90.0;A1-Paris")
  }

  test("CsvParserTest.parseCSV simple example") {

    val header = "id;location;speed;acceleration;fuel;engineTemp;nextStep"
    val string1 = "car2789;48,23 89,56;67.8;2.3;60.6;90.0;A1-Paris"

    val e = CsvParser.parseCSV(Array(header, string1), ';')

    assert(e.length === 1)
    assert(e(0).toString === "car2789;48,23 89,56;67.8;2.3;60.6;90.0;A1-Paris")

  }
}
