import extension.FileOps
import java.io.{File, FileWriter, PrintWriter}
import adt.Error._
import adt.NonEmptyString
import scala.io.Source

class HelloSpec extends munit.FunSuite {

  private val filePath = getClass.getClassLoader.getResource("test_file.txt").getFile

  override def beforeEach(context: BeforeEach): Unit = {
    val file = new File(filePath)
    val fileWriter = new FileWriter(file)
    new PrintWriter(fileWriter).close
  }

  test("Should give a duplicate line error when trying to add a line that already exists") {
    val filePath = getClass.getClassLoader.getResource("test_file.txt").getFile
    val file = new File(filePath)
    file.addLineUnique(NonEmptyString("foo")) match 
      case Left(_) => assertFail("Should not fail here as the file is empty")
      case Right(r) => assertEquals(r, ())    

    file.addLineUnique(NonEmptyString("foo")) match 
      case Left(e) => assertEquals(e, DUPLICATE_LINE)
      case Right(_) => assertFail("Should not succeed as there is aduplicate line present")
  }

  test("Should give return a list of all the lines that were appended to the file") {
    val filePath = getClass.getClassLoader.getResource("test_file.txt").getFile
    val file = new File(filePath)
    val linesToAppend = Set("foo", "baz", "goo", "quux").map(NonEmptyString.apply)
    file.addLinesUnique(linesToAppend) match 
      case Right(set) => assertEquals(set, Set("foo", "baz", "goo", "quux").map(NonEmptyString.apply))
      case Left(_) => assertFail("should not fail here")

    file.addLinesUnique(linesToAppend) match
      case Right(set) => assertEquals(set, Set.empty[NonEmptyString])
      case Left(_) => assertFail("should not fail here")
    
    file.addLinesUnique(linesToAppend + NonEmptyString.apply("foooz")) match
      case Right(set) => assertEquals(set, Set(NonEmptyString("foooz")))
      case Left(_) => assertFail("should not fail here")
  }

}
