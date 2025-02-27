package example

import java.io.File

import scala.io.Source

object Main extends App {

  /*

    {
        id: ...,
        coordonnée : ...,
        vitesse : ...,
        accélération: ...,
        carburant: ...,
        temperature_moteur: ...,
        prochaine_étape: ...,

    }

    Pas besoin de ligne de header oklm

    Print les warning d'alerte pour réagir en cas de problème

    Système de fichier distribué pour la BDD (Cassandra peut etre)

    String vers un répertoire en entré de notre client/parser

    Liste d'objet en retour

   */

  def handleResponse(response : String) = response match {
    case response if response contains "WARNING" => println(response)
    case _ => println(response)
    //val responseJSON = JsonParser.parseJSON(response)
    //println(responseJSON.keys.toList(0).toString == "Type")
  }

  def getListOfFiles(dir: String):List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }
  }

  def files(path : String) = {
    val lines = Source.fromFile(path).getLines.toArray
    val res = example.CsvParser.parseCSV(lines)
    res.foreach(x => {
      val response = HttpRequest.post(x)
      handleResponse(response)
    })
  }

  getListOfFiles("ressources/").foreach(x => files(x.getAbsolutePath))
}
